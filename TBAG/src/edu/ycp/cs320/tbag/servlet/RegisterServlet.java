package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;
import edu.ycp.cs320.tbag.model.User;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DerbyDatabase db = new DerbyDatabase();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String newUsername = req.getParameter("newUsername");
        String newPassword = req.getParameter("newPassword");

        if (newUsername != null && newPassword != null) {
            User existingUser = db.findUser(newUsername);
            if (existingUser != null) {
                // Username already exists, redirect with error 2
                resp.sendRedirect("login?error=3");
            } else {
                // Username does not exist, proceed with registration
                db.insertUser(newUsername, newPassword);
                resp.sendRedirect("login?error=2"); // Optionally, redirect to login with success message
            }
        } else {
            // Invalid parameters, redirect with error 3
            resp.sendRedirect("login?error=3");
        }
    }
}
