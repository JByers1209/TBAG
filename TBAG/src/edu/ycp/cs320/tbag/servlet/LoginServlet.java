package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve username and password from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if username is "admin" and password is "password"
        boolean authenticationSucceeded = "admin".equals(username) && "password".equals(password);

        if (authenticationSucceeded == true) {
            // Authentication successful
            // Redirect to a success page or perform other actions
            response.sendRedirect("index.jsp");
        } else {
            // Authentication failed
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>Authentication failed. Please try again.</h2>");
            out.println("</body></html>");
        }
    }
}
