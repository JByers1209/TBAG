package edu.ycp.cs320.tbag.controller;

import edu.ycp.cs320.tbag.model.Game;

public class GameEngine {

	Game game = new Game();
	//Processes the users commands and controls the game
    public String processUserInput(String userInput) {
    	return game.processUserInput(userInput);
    }
}
