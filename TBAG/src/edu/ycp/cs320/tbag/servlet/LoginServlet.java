package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.tbag.controller.GameEngine;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GameEngine gameEngine; // Instantiate GameEngine

    @Override
    public void init() throws ServletException {
        super.init();
        gameEngine = new GameEngine(); // Initialize GameEngine
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	  String username = req.getParameter("username");
          String password = req.getParameter("password");
          
          boolean isAuthenticated = authenticateUser(username, password);

          if (isAuthenticated) {
              // Forward to the game page upon successful login
              req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
          } else {
              // Redirect back to the login page with an error message
              resp.sendRedirect("/login.jsp?error=1");
          }   
	}
 // Placeholder method for authentication (replace with actual authentication logic)
    private boolean authenticateUser(String username, String password) {
        // This is a placeholder implementation, you should replace it with your actual authentication logic
        return username.equals("admin") && password.equals("password");
    }
}