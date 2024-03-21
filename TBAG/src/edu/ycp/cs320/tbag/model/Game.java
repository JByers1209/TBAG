package edu.ycp.cs320.tbag.model;

// model class for GuessingGame
// only the controller should be allowed to call the set methods
// the JSP will call the "get" and "is" methods implicitly
// when the JSP specifies game.min, that gets converted to
//    a call to model.getMin()
// when the JSP specifies if(game.done), that gets converted to
//    a call to model.isDone()
public class Game {
	private String input, result;
	
	public Game(String input, String result) {
		this.input = input;
		this.result = result;
	}
	
	public void setInput(String input) {
		this.input = input;
		}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return result;
	}
	
	public String getInput() {
		return input;
	}

	
}
