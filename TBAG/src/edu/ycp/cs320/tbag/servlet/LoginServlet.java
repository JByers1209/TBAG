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
   
	}
}