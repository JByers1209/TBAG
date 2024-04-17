package edu.ycp.cs320.tbag.model;

public class RoomConnection {

	private int roomId;
	private String move1;
	private String dest1;
	private String move2;
	private String dest2;
	private String move3;
	private String dest3;
	private String move4;
	private String dest4;
	
	

	public RoomConnection(){
	}
	
	public int getRoomID() {
		return roomId;
	}
	
	public void setRoomID(int roomId) {
		this.roomId = roomId;
	}
	
	//Move 1
		public void setMove1(String move1) {
			this.move1 = move1;
		}
		
		public String getMove1() {
			return move1;
		}

	//Move 2
		public void setMove2(String move2) {
			this.move2 = move2;
		}
		
		public String getMove2() {
			return move2;
		}
	
	//Move 3
		public void setMove3(String move3) {
			this.move3 = move3;
		}
			
		public String getMove3() {
			return move3;
		}
	
	//Move 4
		public void setMove4(String move4) {
			this.move4 = move4;
		}
		
		public String getMove4() {
			return move4;
		}	
}

