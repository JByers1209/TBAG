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
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:derby:C:/Users/josmb/git/tbag.db";

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    // Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve username from the request parameter
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
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=1");
        }
    }

    private boolean authenticate(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isAuthenticated = false;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(DB_URL);

            // Prepare statement
            stmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
            stmt.setString(1, username);

            // Execute query
            rs = stmt.executeQuery();

            // If any row is returned, it means the username exists in the database
            if (rs.next()) {
                // Retrieve the password stored in the database
                String storedPassword = rs.getString("password");
                // Compare the stored password with the password provided by the user
                isAuthenticated = storedPassword.equals(password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle database connection or query errors
            e.printStackTrace(); // Consider logging the error instead
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Consider logging the error instead
            }
        }
        return isAuthenticated;
    }
}
