package edu.ycp.cs320.tbag.model;

public class RoomConnection {

	private int connectionID;
	private int roomId;
	private String move;
	private int destId;
	
	public RoomConnection(){
	}
	
	public int getConnectionID() {
		return connectionID;
	}
	
	public void setConnectionID(int connectionID) {
		this.connectionID = connectionID;
	}
	
	public int getRoomID() {
		return roomId;
	}
	
	public void setRoomID(int roomId) {
		this.roomId = roomId;
	}
	

	public void setMove(String move) {
		this.move = move;
	}
		
	public String getMove() {
		return move;
	}


	public void setDestId(int destId) {
		this.destId = destId;
	}
		
	public int getDestId() {
		return destId;
	}

}

