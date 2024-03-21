package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.tbag.controller.GameEngine;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    // Retrieve user input from the form
	    String userInput = req.getParameter("userInput");

	    // Process the user input
	    String result = ""; // Process the input and get the result

	    // Set the result as an attribute
	    req.setAttribute("result", result);

	    // Forward to view to render the result HTML document
	    req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}


}
