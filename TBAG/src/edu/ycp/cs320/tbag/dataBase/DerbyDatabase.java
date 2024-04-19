package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.User;


public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	private void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		room.setRoomID(resultSet.getInt(index++));
		room.setName(resultSet.getString(index++));
		room.setLongDescription(resultSet.getString(index++));
		room.setShortDescription(resultSet.getString(index++));
		room.setVisited(resultSet.getString(index++));
		room.setNeedsKey(resultSet.getString(index++));
		room.setKeyName(resultSet.getString(index++));
	}
	
	private void loadRoomConnection(RoomConnection roomConnection, ResultSet resultSet, int index) throws SQLException {
		roomConnection.setRoomID(resultSet.getInt(index++));
		roomConnection.setMove1(resultSet.getString(index++));
		roomConnection.setDest1(resultSet.getString(index++));
		roomConnection.setMove2(resultSet.getString(index++));
		roomConnection.setDest2(resultSet.getString(index++));
		roomConnection.setMove3(resultSet.getString(index++));
		roomConnection.setDest3(resultSet.getString(index++));
		roomConnection.setMove4(resultSet.getString(index++));
		roomConnection.setDest4(resultSet.getString(index++));
	}
	
	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setUserID(resultSet.getInt(index++));
		user.setUsername(resultSet.getString(index++), null);
		user.setPassword(resultSet.getString(index++));	
	}
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				
				try {
					stmt1 = conn.prepareStatement(
						"create table rooms (" +
						"	room_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	name varchar(90)," +
						"	longDescription varchar(150)," +
						"	shortDescription varchar(150)," +
						"	hasVisited varchar(40)," +
						"	needsKey varchar(40)," +
						"	keyName varchar(40)" +
						")"
					);
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
						    "create table roomConnections (" +
						    "	connection_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	room_id integer constraint room_id references rooms, " +
						    "    move1 varchar(40)," +
						    "    dest1 varchar(40)," +
						    "    move2 varchar(40)," +
						    "    dest2 varchar(40)," +
						    "    move3 varchar(40)," +
						    "    dest3 varchar(40)," +
						    "    move4 varchar(40)," +
						    "    dest4 varchar(40)" +
						    ")"
						);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table users (" +
							"	user_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	user varchar(90)," +
							"	username varchar(150)," +
							"	password varchar(150)," +
							")"
						);
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Room> roomList;
				List<RoomConnection> connectionList;
				List<User> userList;
				
				try {
					roomList = InitialData.getRooms();
					connectionList = InitialData.getConnections();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom = null;
				PreparedStatement insertConnection   = null;
				PreparedStatement insertUser   = null;


				try {
					// populate rooms table (do authors first, since author_id is foreign key in books table)
					insertRoom = conn.prepareStatement("insert into rooms (name, longDescription, shortDescription, hasVisited, needsKey, keyName) values (?, ?, ?, ?, ?, ?)");
					for (Room room : roomList) {
//						insertRoom.setInt(1, room.getRoomId());		// auto-generated primary key, don't insert this
						insertRoom.setString(1, room.getName());
						insertRoom.setString(2, room.getLongDescription());
						insertRoom.setString(3, room.getShortDescription());
						insertRoom.setString(4, room.getVisited());
						insertRoom.setString(5, room.getNeedsKey());
						insertRoom.setString(6, room.getKeyName());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
					
					// populate connections table
					insertConnection = conn.prepareStatement("insert into roomConnections (move1, dest1, move2, dest2, move3, dest3, move4, dest4) values (?, ?, ?, ?, ?, ?, ?, ?)");
					for (RoomConnection roomConnection : connectionList) {
//						insertBook.setInt(1, roomConnections.getBookId());		// auto-generated primary key, don't insert this
						insertConnection.setString(1, roomConnection.getMove1());
						insertConnection.setString(2, roomConnection.getDest1());
						insertConnection.setString(3, roomConnection.getMove2());
						insertConnection.setString(4, roomConnection.getDest2());
						insertConnection.setString(5, roomConnection.getMove3());
						insertConnection.setString(6, roomConnection.getDest3());
						insertConnection.setString(7, roomConnection.getMove4());
						insertConnection.setString(8, roomConnection.getDest4());
						insertConnection.addBatch();
					}
	
					insertConnection.executeBatch();
					
					insertUser = conn.prepareStatement("insert into users (username, password) values (?, ?)");
					for (User user : userList) {
//						insertRoom.setInt(1, room.getRoomId());		// auto-generated primary key, don't insert this
						insertUser.setString(1, user.getUsername());
						insertUser.setString(2, user.getPassword());
						
					}
					insertRoom.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertRoom);
					DBUtil.closeQuietly(insertConnection);
					DBUtil.closeQuietly(insertUser);

				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}

	@Override
	public Actor findActorByID(int actorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room findCurrentLocationByActorID(int actorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findConnectionByRoomIDandDirection(String roomId, String move) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateCurrentRoomByRoomAndActorID(int newRoomId, int actorId) {
		// TODO Auto-generated method stub
		
	}
}