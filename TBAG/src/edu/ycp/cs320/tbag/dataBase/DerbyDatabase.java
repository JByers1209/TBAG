package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;


import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.NPC;
import edu.ycp.cs320.tbag.model.Player;



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

	
	
	
	
	
	
	
	
	


	private void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
		
				
				try {
					stmt1 = conn.prepareStatement(
						"create table actors (" +
						"	actor_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	room_id integer," +
						"   name varchar(40)," +
						"	level integer," +
						"   xp integer," +
						"   current_health integer," + 
						"   max_health integer" + 
						")"
					);	
					stmt1.executeUpdate();
					
				
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
				}
			}
		});
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Actor> actorList;
				
				try {
					actorList = InitialData.getActors();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertActor = null;


				try {
					// populate actors table
					insertActor = conn.prepareStatement("insert into actors (room_id, name, level, xp, current_health, max_health) values (?, ?, ?, ? ,?, ?)");
					for (Actor actor : actorList) {
//						insertAuthor.setInt(1, actor.getActorID());
						insertActor.setInt(1, actor.getRoomID());
						insertActor.setString(2, actor.getName());
						insertActor.setInt(3, actor.getLevel());
						insertActor.setInt(4, actor.getXP());
						insertActor.setInt(5, actor.getCurrentHealth());
						insertActor.setInt(6, actor.getMaxHealth());
						insertActor.addBatch();
					}
					insertActor.executeBatch();
					
	
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertActor);
				}
			}
		});
	}//end loadInitialData()
	
	
	private void loadActor(Actor actor, ResultSet resultSet, int index) throws SQLException {
		actor.setActorID(resultSet.getInt(index++));
		actor.setRoomID(resultSet.getInt(index++));
		actor.setName(resultSet.getString(index++));
		actor.setLevel(resultSet.getInt(index++));
		actor.setXP(resultSet.getInt(index++));
		actor.setCurrentHealth(resultSet.getInt(index++));
		actor.setMaxHealth(resultSet.getInt(index++));
	}
	
	private static int getActorIDbyRoomID(Connection conn, int roomID) throws SQLException {
		int actorID = -1;
		PreparedStatement stmt;
		ResultSet resultSet;
		
		// get actor id query
		 stmt = conn.prepareStatement(
				"select actors.actor_id "
				+ "from actors "
				+ "where actors.room_id = ?"
		);
					
		
		// substitute the roomID entered by the user for the placeholder in the query
		stmt.setInt(1, roomID);
		// execute the query
		resultSet = stmt.executeQuery();

		// get the precise schema of the tuples returned as the result of the query
		ResultSetMetaData resultSchema = stmt.getMetaData();

		// iterate through the returned tuples, printing each one
		// count # of rows returned
		int rowsReturned = 0;
		
		while (resultSet.next()) {
			for (int i = 1; i <= resultSchema.getColumnCount(); i++) {
				Object obj = resultSet.getObject(i);
				actorID = Integer.parseInt(obj.toString());
				if (i > 1) {
					System.out.print(",");
				}
				System.out.print("Retrieved actor id: "+ actorID);
			}
			System.out.println();
			
			// count # of rows returned
			rowsReturned++;
		}
		
		// indicate if the query returned nothing
		if (rowsReturned == 0) {
			System.out.println("No Actor ID");
		}
		
		
		return actorID;
	}
	
	
	//IDatabase Methods
	@Override
	public Actor findActorByID(int actorID) {
		return executeTransaction(new Transaction<Actor>() {
		@Override
		public Actor execute(Connection conn) throws SQLException {
			Actor actor;
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			if(actorID == 1) {
				actor = new Player();
			}else {
				actor = new NPC();
			}
			
			try {
				stmt = conn.prepareStatement("select * "+ 
			                                 "from actors " +
					                         "where actors.actor_id = ?");
				
				stmt.setInt(1, actorID);
				
				resultSet = stmt.executeQuery();
				
				Boolean found = false;
				while (resultSet.next()) {
					found = true;
					loadActor(actor, resultSet, 1);
				}
				
				// check if the ID was found
				if (!found) {
					System.out.println("<" + actorID + "> was not found in the actors table");
				}
				
			}finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
			}
			
		
			return actor;
		}
		
	});
		
	}//end findActorByID
	
	@Override
	public Actor findActorByRoomID(int roomID) {
		return executeTransaction(new Transaction<Actor>() {
		@Override
		public Actor execute(Connection conn) throws SQLException {
			Actor actor;
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			int actorID = getActorIDbyRoomID(conn, roomID);
			
			if(actorID == 1) {
				actor = new Player();
			}else {
				actor = new NPC();
			}
			
			try {
				stmt = conn.prepareStatement("select * "+ 
			                                 "from actors " +
					                         "where actors.room_id = ?");
				
				stmt.setInt(1, roomID);	
				resultSet = stmt.executeQuery();
				Boolean found = false;
				while (resultSet.next()) {
					found = true;
					loadActor(actor, resultSet, 1);
				}
				
				// check if the ID was found
				if (!found) {
					System.out.println("<" + roomID + "> was not found in the actors table");
					return null;
				}
				
			}finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
			}
			
		
			return actor;
		}
		
	});
		
	}//end findActorByRoomID
	
	@Override
	public void updateActor(int actorID, Actor actor) {
		executeTransaction(new Transaction<Object>() {
			@Override
			public Object execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					
					boolean actorInDatabase;
				
					if (findActorByID(actorID) == null) {
						actorInDatabase = false;
					}else {
						actorInDatabase = true;
					}
					
					if(actorInDatabase == false) {
						System.out.println("No actor in database with id of " + actorID);
						return null;
						
					}
					
					
					// update actor in database
					stmt = conn.prepareStatement(
							"update actors "
						  + "set room_id = ?, "
						  + "name = ?, "
						  + "level = ?, "					
						  + "xp = ?, "
						  + "current_health = ?, "	
						  + "max_health = ? "
						  + "where actor_id = ? "
						  
					);
					
					
					stmt.setInt(1, actor.getRoomID());
					stmt.setString(2, actor.getName());
					stmt.setInt(3, actor.getLevel());
					stmt.setInt(4, actor.getXP());
					stmt.setInt(5, actor.getCurrentHealth());
					stmt.setInt(6, actor.getMaxHealth());
					stmt.setInt(7, actorID);
				
					stmt.executeUpdate();
					
					return null;
				
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});//end transaction
		
	}//end updateActor

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}//end main

	
	
}//end DerbyDatabase
