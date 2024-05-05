package edu.ycp.cs320.tbag.model;

public class SaveGame {
	
	private int userID;
	private String saveName;
	private int saveID;
	
	public SaveGame() {
		
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public int getSaveID() {
		return saveID;
	}

	public void setSaveID(int saveID) {
		this.saveID = saveID;
	}
	
	
}
