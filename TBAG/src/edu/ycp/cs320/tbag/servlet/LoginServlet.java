package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.tbag.controller.GameEngine;
import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DerbyDatabase db = new DerbyDatabase();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("test2");
        // Forward GET requests to the login page
        req.getRequestDispatcher("_view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        // Check if username and password are not null
        if (username != null && password != null) {
            // Authenticate user and get user_id
            int userId = authenticateUser(username, password);
            
            if (userId > 0) {
                HttpSession session = req.getSession();
             
                session.setAttribute("username", username);
                session.setAttribute("user_id", userId);
                System.out.println(username);
                System.out.println(userId);
                resp.sendRedirect("index");
            } else {
                resp.sendRedirect("login?error=1");
            }

        } else {
            // If username or password is null, show error message
            resp.sendRedirect("login?error=2");
        }
    }

    private int authenticateUser(String username, String password) {
        String dbUrl = "jdbc:derby:tbag.db";
        
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT user_id FROM users WHERE username=? AND password=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("user_id");
                    } else {
                        return 0; // Return 0 if authentication fails
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Return 0 in case of any database error
        }
    }

}