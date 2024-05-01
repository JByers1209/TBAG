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
            // Authenticate user
            boolean isAuthenticated = authenticateUser(username, password);
            
            if (isAuthenticated) {
                // If authentication is successful, create a session
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                
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

    private boolean authenticateUser(String username, String password) {
        // Database connection parameters
        String dbUrl = "jdbc:derby:C:/Users/josmb/git/tbag.db"; // Update with your database URL
        
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            // Prepare the SQL statement
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Check if the query returned any rows
                    return resultSet.next(); // Return true if the query returned at least one row
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
            return false; // Return false in case of any database error
        }
    }
}