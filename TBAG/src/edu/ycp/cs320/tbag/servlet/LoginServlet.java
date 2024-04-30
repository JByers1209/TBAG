package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve username and password 
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Authenticate user 
        boolean isAuthenticated = authenticate(username, password);

        if (isAuthenticated) {
            // Create a session and store user information
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Redirect user to the home page or some other protected resource
            resp.sendRedirect(req.getContextPath() + "/game.jsp");
        } else {
            // Invalid credentials, redirect back to login page with an error message
            resp.sendRedirect(req.getContextPath() + "/login?error=1");
        }
    }

    private boolean authenticate(String username, String password) {
        // Establish database connection
        String url = "jdbc:derby:Documents/Spring_2024/CS320_spring2024/tbag.db";
     
        String dbUsername = null;
		String dbPassword = null;
		try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
        ) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPass = rs.getString("password");
                // Compare passwords using equals() method
                if (storedPass.equals(password)) {
                    return true;
                }
            
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query errors
        }

        // Return false if authentication fails
        return false;
    }
}
