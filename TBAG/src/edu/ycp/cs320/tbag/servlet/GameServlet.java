package edu.ycp.cs320.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.tbag.controller.GameEngine;
import edu.ycp.cs320.tbag.dataBase.DerbyDatabase;

public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GameEngine gameEngine; // Instantiate GameEngine
    DerbyDatabase db = new DerbyDatabase();
    @Override
    public void init() throws ServletException {
        super.init();
        gameEngine = new GameEngine(); // Initialize GameEngine
    }
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        
        if (session != null && session.getAttribute("user_id") != null) {
            int userId = (int) session.getAttribute("user_id");
            String username = (String) session.getAttribute("username");
            
            // Pass the user ID and username to the GameEngine
            gameEngine.setUserId(userId);
            gameEngine.setUsername(username);
            System.out.println(userId + username);
            // If the user is logged in, redirect to the game page
            req.getRequestDispatcher("_view/game.jsp").forward(req, resp);
        } else {
            // If the user is not logged in, redirect to the login page
        	req.getRequestDispatcher("_view/login.jsp").forward(req, resp);
        }
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

}
