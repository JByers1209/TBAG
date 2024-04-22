package edu.ycp.cs320.tbag.model;

public class User {

	private int userId;
	private String username;
	private String password;
	
	

	public User(){
	}
	
	public int getUserID() {
		return userId;
	}
	
	public void setUserID(int userId) {
		this.userId = userId;
	}
	
	//username
		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getUsername() {
			return username;
		}

	// password
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getPassword() {
			return password;
		}

		
}