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

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
            
            if (userId != -1) {
                // If authentication is successful, create a session
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                session.setAttribute("user_id", userId);
                
                // Redirect to a success page or perform other actions
                resp.sendRedirect("index");
            } else {
                // If authentication fails, show error message
                resp.sendRedirect("login?error=1");
            }
        } else {
            // If username or password is null, show error message
            resp.sendRedirect("login?error=2");
        }
    }


    private int authenticateUser(String username, String password) {
        // Database connection parameters
        String dbUrl = "jdbc:derby:C:/Users/josmb/git/tbag.db"; // Update with your database URL
        
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            // Prepare the SQL statement
            String sql = "SELECT user_id FROM users WHERE username=? AND password=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Check if the query returned any rows
                    if (resultSet.next()) {
                        // If the user is authenticated, return the user_id
                        return resultSet.getInt("user_id");
                    } else {
                        // If authentication fails, return -1
                        return -1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
            return -1; // Return -1 in case of any database error
        }
    }

}