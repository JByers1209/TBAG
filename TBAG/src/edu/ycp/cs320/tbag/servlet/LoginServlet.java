package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve username and password from the request parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Check if username and password are valid (example check)
        if (isValidLogin(username, password)) {
            // If valid, redirect to a success page
            resp.sendRedirect(req.getContextPath() + "/success.jsp");
        } else {
            // If not valid, redirect back to the login page with an error message
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=1");
        }
    }

    // Example method to check if username and password are valid (replace with your logic)
    private boolean isValidLogin(String username, String password) {
        // Example validation logic (replace with actual validation logic)
        // This is just a simple example
        return "admin".equals(username) && "password".equals(password);
    }
}
