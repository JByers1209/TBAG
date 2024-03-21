package edu.ycp.cs320.tbag.controller;

import edu.ycp.cs320.tbag.model.Game;

public class GameEngine {
    private Game game;

    public GameEngine() {
        this.game = new Game();
    }

    public String processUserInput(String userInput) {
        return game.processUserInput(userInput);
    }
}
