package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // Extract user input from request
        String userInput = req.getParameter("userInput");

        // Process user input using GameEngine
        String gameResponse = gameEngine.processUserInput(userInput);

        
        // Set response content type
        resp.setContentType("text/plain");

        // Send game response back to client
        resp.getWriter().write(gameResponse);
    }
}