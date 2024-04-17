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
       
        
        

        // Check if username and password are valid
        if (isValidLogin(username, password)) {
            // If valid, redirect to the index page
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            // If not valid, forward back to the login page with an error message
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }


    // Example method to check if username and password are valid (replace with your logic)
    private boolean isValidLogin(String username, String password) {
        // Example validation logic (replace with actual validation logic)
        // This is just a simple example
        return "admin".equals(username) && "password".equals(password);
    }
}
