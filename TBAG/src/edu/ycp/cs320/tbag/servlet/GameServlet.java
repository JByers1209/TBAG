package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.tbag.controller.GameEngine;

public class GameServlet extends HttpServlet {
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
   
        	 
        String userInput = req.getParameter("userInput");

        // Process user input using GameEngine
        String gameResponse = gameEngine.processUserInput(userInput);

        // Set response content type
        resp.setContentType("text/plain");

        // Send game response back to client
        resp.getWriter().write(gameResponse);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 HttpSession session = req.getSession(false);
		 
    	 if (session != null && session.getAttribute("username") != null) {
    		 resp.sendRedirect("/_view/login.jsp");
         } else {
             // User is not logged in, redirect t
        // Extract user input from request'
        	 
		String function = req.getParameter("function");
    
    if ("Resume".equals(function)) {
        req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
    }
  
    else {
    	req.getRequestDispatcher("/_view/pause.jsp").forward(req, resp);
    	}
    }
  }
}

