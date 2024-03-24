package edu.ycp.cs320.tbag.model;

public class Game {
	
	String response;
	
	Room start = new Room("Start", "You are in the starter room!");
	Room up = new Room("Above", "The above room");
	Room left = new Room("Left", "The left room");
	Room right = new Room("Right", "The right room");
	Room down = new Room("Below", "The below room");
	
	Player player = new Player(start);

}

