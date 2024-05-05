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
import edu.ycp.cs320.tbag.model.Consumable;
import edu.ycp.cs320.tbag.model.Item;
import edu.ycp.cs320.tbag.model.KeyItem;
import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;
import edu.ycp.cs320.tbag.model.SaveData;
import edu.ycp.cs320.tbag.model.SaveGame;
import edu.ycp.cs320.tbag.model.User;
import edu.ycp.cs320.tbag.model.Weapon;
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
	private int actorCount, itemCount, roomCount;
	
	
	public int getActorCount() {
		return actorCount;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public int getRoomCount() {
		return roomCount;
	}
	
	public void addSaveGame(SaveGame saveGame) {
	    executeTransaction(new Transaction<Void>() {
        @Override
        public Void execute(Connection conn) throws SQLException {
            PreparedStatement stmt = null;
            ResultSet resultSet = null;

            try {
                stmt = conn.prepareStatement("insert into save_games (user_id, save_name, save_id) values (?, ?, ?)");
                stmt.setInt(1, saveGame.getUserID());
                stmt.setString(2, saveGame.getSaveName());
                stmt.setInt(3, saveGame.getSaveID());
                stmt.executeUpdate();
             
            } finally {
                // Closing resources in a finally block
                DBUtil.closeQuietly(resultSet);
                DBUtil.closeQuietly(stmt);
            }
            return null;
        }
    });
		
	}//end addSaveGame

	
	public List<RoomConnection> findConnectionsByRoomID(int roomID) {
	    return executeTransaction(new Transaction<List<RoomConnection>>() {
	        @Override
	        public List<RoomConnection> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement(
	                        "SELECT * FROM RoomConnections WHERE room_id = ?"
	                );
	                stmt.setInt(1, roomID);

	                List<RoomConnection> result = new ArrayList<>();

	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                    RoomConnection connection = new RoomConnection();
	                    
	                    // Load common attributes
	                    loadRoomConnection(connection, resultSet, 1);
	                    result.add(connection);
	                }

	                if (result.isEmpty()) {
	                    System.out.println("<" + roomID + "> was not found in the connections table");
	                    return new ArrayList<>(); // Return an empty list
	                }


	                return result;
	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	public Room findRoomByRoomID(int room_id) {
	    return executeTransaction(new Transaction<Room>() {
	        @Override
	        public Room execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;
	            Room result = null;

	            try {
	                // Construct the SQL query to retrieve the room based on room_id
	                stmt = conn.prepareStatement(
	                        "SELECT * FROM rooms WHERE room_id = ?"
	                );
	                stmt.setInt(1, room_id);

	                resultSet = stmt.executeQuery();

	                // Check if the result set contains any rows
	                if (resultSet.next()) {
	                    // If a room is found, create a Room object and populate its attributes
	                    result = new Room();
	                    result.setRoomID(resultSet.getInt(1));
	                    result.setName(resultSet.getString(2));
	                    result.setLongDescription(resultSet.getString(3));
	                    result.setShortDescription(resultSet.getString(4));
	                    result.setVisited(resultSet.getString(5));
	                    result.setNeedsKey(resultSet.getString(6));
	                    result.setKeyName(resultSet.getString(7));
	                    
	                    // Log the retrieved room's attributes for debugging
	                    System.out.println("Retrieved room attributes:");
	                    System.out.println("Room ID: " + result.getRoomID());
	                    System.out.println("Name: " + result.getName());
	                    System.out.println("Has Visited: " + result.getVisited());
	                    System.out.println("Needs Key: " + result.getNeedsKey());
	                    // Log other attributes similarly
	                } else {
	                    // No room found
	                    System.out.println("Room with room_id " + room_id + " not found.");
	                }
	            } finally {
	                // Close resources
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }

	            return result;
	        }
	    });
	}
	
	
	public void updateRoomByRoom(Room room) {
	    executeTransaction(new Transaction<Void>() {
	        @Override
	        public Void execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;

	            try {
	                // Construct the SQL query to update the room in the rooms table
	                stmt = conn.prepareStatement(
	                        "UPDATE rooms " +
	                        "SET name = ?, longDescription = ?, shortDescription = ?, " +
	                        "hasVisited = ?, needsKey = ?, keyName = ? " +
	                        "WHERE room_id = ?"
	                );

	                // Set parameters for the update query based on the Room object
	                stmt.setString(1, room.getName());
	                stmt.setString(2, room.getLongDescription());
	                stmt.setString(3, room.getShortDescription());
	                stmt.setString(4, room.getVisited());
	                stmt.setString(5, room.getNeedsKey());
	                stmt.setString(6, room.getKeyName());
	                stmt.setInt(7, room.getRoomID());

	                // Execute the update query
	                int rowsUpdated = stmt.executeUpdate();

	                if (rowsUpdated > 0) {
	                    System.out.println("Room with room_id " + room.getRoomID() + " updated successfully.");
	                } else {
	                    System.out.println("Room with room_id " + room.getRoomID() + " not found or not updated.");
	                }
	            } finally {
	                // Close resources
	                DBUtil.closeQuietly(stmt);
	            }

	            return null;
	        }
	    });
	}
	
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
	public Item findItemByID(int itemID) {
		return executeTransaction(new Transaction<Item>() {
		@Override
		public Item execute(Connection conn) throws SQLException {
	
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			Item item = null;
			
			try {
				stmt = conn.prepareStatement("select * "+ 
			                                 "from items " +
					                         "where items.item_id = ?");
				
				stmt.setInt(1, itemID);
				
				resultSet = stmt.executeQuery();
				
				Boolean found = false;
				while (resultSet.next()) {
					found = true;
                    int itemType = resultSet.getInt("type");
                    switch (itemType) {
                        case 1:
                            item = new Weapon();
                            break;
                        case 2:
                            item = new Consumable();
                            break;
                        case 3:
                            item = new KeyItem();
                            break;
                        default:
                            // If item type is not recognized, return null
                            return null;
                    }
					loadItems(item, resultSet, 1);
					
				}
				
				// check if the ID was found
				if (!found) {
					System.out.println("<" + itemID + "> was not found in the actors table");
		
				}
				
			}finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
			}
			
		
			return item;
		}
		
	});
		
	}//end findItemByID
	
	@Override
	public void updateActor(Actor actor) {
		executeTransaction(new Transaction<Object>() {
			@Override
			public Object execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					
					boolean actorInDatabase;
				
					if (findActorByID(actor.getActorID()) == null) {
						actorInDatabase = false;
					}else {
						actorInDatabase = true;
					}
					
					if(actorInDatabase == false) {
						System.out.println("No actor in database with id of " + actor.getActorID());
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
					stmt.setInt(7, actor.getActorID());
				
					stmt.executeUpdate();
					
					return null;
				
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});//end transaction
		
	}//end updateActor
	
	@Override
	public void saveActor(int saveID, Actor actor) {
		executeTransaction(new Transaction<Object>() {
			@Override
			public Object execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
			
				try {
					
					boolean actorInDatabase;
			
				
					if (findActorByID(actor.getActorID()) == null) {
						actorInDatabase = false;
					}else {
						actorInDatabase = true;
					}
					if(actorInDatabase == false) {
						System.out.println("No actor in database with id of " + actor.getActorID());
						return null;
						
					}
					
					
					// save actor in database 
					//    format for actors in save id_slot1 = actorID, id_slot2 = roomID
					stmt = conn.prepareStatement(
							"insert into saves (save_id, save_type, id_slot1, id_slot2, id_slot3, actor_level, actor_xp, actor_current_health, actor_max_health, room_has_visited, room_needs_key, log) "
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						  
					);
					
					stmt.setInt(1, saveID);
					stmt.setString(2, "actor");
					stmt.setInt(3, actor.getActorID());
					stmt.setInt(4, actor.getRoomID());
					stmt.setInt(5, -1);
					stmt.setInt(6, actor.getLevel());
					stmt.setInt(7, actor.getXP());
					stmt.setInt(8, actor.getCurrentHealth());
					stmt.setInt(9, actor.getMaxHealth());
					stmt.setString(10, "null");
					stmt.setString(11, "null");
					stmt.setString(12, "null");
					
					
				
					stmt.executeUpdate();
					
					return null;
				
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});//end transaction
		
	}//end saveActor
	

	@Override
	public void saveRoom(int saveID, Room room) {
		executeTransaction(new Transaction<Object>() {
			@Override
			public Object execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
			
				try {
					
					boolean roomInDatabase;
			
				
					if (findRoomByRoomID(room.getRoomID()) == null) {
						roomInDatabase = false;
					}else {
						roomInDatabase = true;
					}
					if(roomInDatabase == false) {
						System.out.println("No room in database with id of " + room.getRoomID());
						return null;
						
					}
					
					
					// save room in database 
					//    format for room in save id_slot1 = roomID
					stmt = conn.prepareStatement(
							"insert into saves (save_id, save_type, id_slot1, id_slot2, id_slot3, actor_level, actor_xp, actor_current_health, actor_max_health, room_has_visited, room_needs_key, log) "
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						  
					);
					
					stmt.setInt(1, saveID);
					stmt.setString(2, "room");
					stmt.setInt(3, room.getRoomID());
					stmt.setInt(4, -1);
					stmt.setInt(5, -1);
					stmt.setInt(6, -1);
					stmt.setInt(7, -1);
					stmt.setInt(8, -1);
					stmt.setInt(9, -1);
					stmt.setString(10, room.getVisited());
					stmt.setString(11, room.getNeedsKey());
					stmt.setString(12, "null");
					
					
				
					stmt.executeUpdate();
					
					return null;
				
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});//end transaction
		

	}//end saveRoom

	
	@Override
	public void saveItem(int saveID, Item item) {
		executeTransaction(new Transaction<Object>() {
			@Override
			public Object execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
			
				try {
					
		
				
				
					
					
					// save item in database 
					//    format for item in save id_slot1 = itemID, id_slot2 = roomID, id_slot3 = ownerID
					stmt = conn.prepareStatement(
							"insert into saves (save_id, save_type, id_slot1, id_slot2, id_slot3, actor_level, actor_xp, actor_current_health, actor_max_health, room_has_visited, room_needs_key, log) "
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						  
					);
					
					stmt.setInt(1, saveID);
					stmt.setString(2, "item");
					stmt.setInt(3, item.getItemID());
					stmt.setInt(4, item.getRoomID());
					stmt.setInt(5, item.getOwnerID());
					stmt.setInt(6, -1);
					stmt.setInt(7, -1);
					stmt.setInt(8, -1);
					stmt.setInt(9, -1);
					stmt.setString(10, "null");
					stmt.setString(11, "null");
					stmt.setString(12, "null");
					
					
				
					stmt.executeUpdate();
					
					return null;
				
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});//end transaction
		
	}//end save item

	
	
	@Override
	public void saveLog(int saveID, String log) {
		executeTransaction(new Transaction<Object>() {
			@Override
			public Object execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
			
				try {
					
		
				
				
					// save log in database 
					stmt = conn.prepareStatement(
							"insert into saves (save_id, save_type, id_slot1, id_slot2, id_slot3, actor_level, actor_xp, actor_current_health, actor_max_health, room_has_visited, room_needs_key, log) "
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						  
					);
					
					stmt.setInt(1, saveID);
					stmt.setString(2, "log");
					stmt.setInt(3, -1);
					stmt.setInt(4, -1);
					stmt.setInt(5, -1);
					stmt.setInt(6, -1);
					stmt.setInt(7, -1);
					stmt.setInt(8, -1);
					stmt.setInt(9, -1);
					stmt.setString(10, "null");
					stmt.setString(11, "null");
					stmt.setString(12, log);
					
					
				
					stmt.executeUpdate();
					
					return null;
				
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});//end transaction
		
	}//end save log
	
	@Override
	public List<SaveData> getSaveData(int saveID){
	    return executeTransaction(new Transaction<List<SaveData>>() {
	        @Override
	        public List<SaveData> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement("SELECT * FROM saves WHERE save_id = ?");
	                stmt.setInt(1, saveID);

	                List<SaveData> result = new ArrayList<>();
	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                    SaveData saveData = new SaveData();
	                    // Assuming loadUser method populates user data from ResultSet
	                    loadSaveData(saveData, resultSet, 1); // Assuming this method exists
	                    result.add(saveData);
	                }

	                return result;
	            } finally {
	                // Closing resources in a finally block
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
		
	
	}//end getSaveData

	
	@Override
	public List<SaveGame> getSaveGames(int userID){
	    return executeTransaction(new Transaction<List<SaveGame>>() {
	        @Override
	        public List<SaveGame> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement("SELECT * FROM save_games WHERE user_id = ?");
	                stmt.setInt(1, userID);

	                List<SaveGame> result = new ArrayList<>();
	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                	SaveGame saveGame = new SaveGame();
	                    // Assuming loadUser method populates user data from ResultSet
	                    loadSaveGame(saveGame, resultSet, 1); // Assuming this method exists
	                    result.add(saveGame);
	                }

	                return result;
	            } finally {
	                // Closing resources in a finally block
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
		
	
	}//end getSaveGame

	
	@Override
	public SaveGame getSaveGameByName(String saveName){
	    return executeTransaction(new Transaction<SaveGame>() {
	        @Override
	        public SaveGame execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement("SELECT * FROM save_games WHERE save_name = ?");
	                stmt.setString(1, saveName);

	             
	                resultSet = stmt.executeQuery();
	                SaveGame saveGame = new SaveGame();
	                while (resultSet.next()) {
	                    loadSaveGame(saveGame, resultSet, 1);
	                }

	                return saveGame;
	            } finally {
	                // Closing resources in a finally block
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
		
	
	}//end getSaveGame
	
	
	public int getNextSaveID(){
	    return executeTransaction(new Transaction<Integer>() {
	        @Override
	        public Integer execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;
	            int largestSaveID = 0;

	            try {
	                stmt = conn.prepareStatement("SELECT * FROM save_games");

	                List<SaveGame> result = new ArrayList<>();
	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                	SaveGame saveGame = new SaveGame();
	                    // Assuming loadUser method populates user data from ResultSet
	                    loadSaveGame(saveGame, resultSet, 1); // Assuming this method exists
	                    result.add(saveGame);
	                }
	                
	                for(SaveGame saveGame: result) {
	                	
	                	if(saveGame.getSaveID() > largestSaveID) {
	                		largestSaveID = saveGame.getSaveID();
	                	}
	                }
	                

	                return (largestSaveID + 1);
	            } finally {
	                // Closing resources in a finally block
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
		
	
	}//end getNextSaveID
	

	
	public List<Item> findItemsByRoomID(int roomID) {
	    return executeTransaction(new Transaction<List<Item>>() {
	        @Override
	        public List<Item> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement(
	                        "SELECT * FROM items WHERE room_id = ?"
	                );
	                stmt.setInt(1, roomID);

	                List<Item> result = new ArrayList<>();

	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                    Item item = null;
	                    int itemType = resultSet.getInt("type");
	                    switch (itemType) {
	                        case 1:
	                            item = new Weapon();
	                            break;
	                        case 2:
	                            item = new Consumable();
	                            break;
	                        case 3:
	                            item = new KeyItem();
	                            break;
	                        default:
	                            // If item type is not recognized, return null
	                            return null;
	                    }
	                    // Load common attributes
	                    loadItems(item, resultSet, 1);
	                    result.add(item);
	                }

	                if (result.isEmpty()) {
	                    // Return null if no items were found
	                    return null;
	                }

	                return result;
	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}


	public List<Item> findItemsByOwnerID(int ownerID) {
	    return executeTransaction(new Transaction<List<Item>>() {
	        @Override
	        public List<Item> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement(
	                        "SELECT * FROM items WHERE owner_id = ?"
	                );
	                stmt.setInt(1, ownerID);

	                List<Item> result = new ArrayList<>();

	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                    Item item = null;
	                    int itemType = resultSet.getInt("type");
	                    switch (itemType) {
	                        case 1:
	                            item = new Weapon();
	                            break;
	                        case 2:
	                            item = new Consumable();
	                            break;
	                        case 3:
	                            item = new KeyItem();
	                            break;
	                        default:
	                            // Handle unknown item types or create a generic Item object
	                            break;
	                    }
	                    // Load common attributes
	                    loadItems(item, resultSet, 1);
	                    result.add(item);
	                }

	                return result;
	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	public List<Item> findItemsByNameAndRoomID(String name, int roomID) {
	    return executeTransaction(new Transaction<List<Item>>() {
	        @Override
	        public List<Item> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement(
	                        "SELECT * FROM items WHERE name = ? AND room_id = ?"
	                );
	                stmt.setString(1, name);
	                stmt.setInt(2, roomID);

	                List<Item> result = new ArrayList<>();

	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                    Item item = null;
	                    int itemType = resultSet.getInt("type");
	                    switch (itemType) {
	                        case 1:
	                            item = new Weapon();
	                            break;
	                        case 2:
	                            item = new Consumable();
	                            break;
	                        case 3:
	                            item = new KeyItem();
	                            break;
	                        default:
	                            // Handle unknown item types or create a generic Item object
	                            break;
	                    }
	                    // Load common attributes
	                    loadItems(item, resultSet, 1);
	                    result.add(item);
	                }

	                return result;
	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	public List<Item> findItemsByName(String name) {
	    return executeTransaction(new Transaction<List<Item>>() {
	        @Override
	        public List<Item> execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;

	            try {
	                stmt = conn.prepareStatement(
	                        "SELECT * FROM items WHERE name = ?"
	                );
	                stmt.setString(1, name);

	                List<Item> result = new ArrayList<>();

	                resultSet = stmt.executeQuery();

	                while (resultSet.next()) {
	                    Item item = null;
	                    int itemType = resultSet.getInt("type");
	                    switch (itemType) {
	                        case 1:
	                            item = new Weapon();
	                            break;
	                        case 2:
	                            item = new Consumable();
	                            break;
	                        case 3:
	                            item = new KeyItem();
	                            break;
	                        default:
	                            // Handle unknown item types or create a generic Item object
	                            break;
	                    }
	                    // Load common attributes
	                    loadItems(item, resultSet, 1);
	                    result.add(item);
	                }

	                return result;
	            } finally {
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	        }
	    });
	}
	
	@Override
	public void updateItem(int itemID, int roomID, int ownerID) {
	    executeTransaction(new Transaction<Void>() {
	        @Override
	        public Void execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;

	            try {
	                // Construct the SQL query to update the item in the items table
	                stmt = conn.prepareStatement(
	                        "UPDATE items " +
	                        "SET room_id = ?, owner_id = ? " +
	                        "WHERE item_id = ?"
	                );

	                // Set parameters for the update query based on the item ID, room ID, and owner ID
	                stmt.setInt(1, roomID);
	                stmt.setInt(2, ownerID);
	                stmt.setInt(3, itemID);

	                // Execute the update query
	                int rowsUpdated = stmt.executeUpdate();

	                if (rowsUpdated > 0) {
	                    System.out.println("Item with item_id " + itemID + " updated successfully.");
	                } else {
	                    System.out.println("Item with item_id " + itemID + " not found or not updated.");
	                }
	            } finally {
	                // Close resources
	                DBUtil.closeQuietly(stmt);
	            }

	            return null;
	        }
	    });
	}
	
	public User findUser(String username) {
	    return executeTransaction(new Transaction<User>() {
	        @Override
	        public User execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;
	            User result = null;

	            try {
	                // Construct the SQL query to retrieve the room based on room_id
	            	stmt = conn.prepareStatement(
	            			"SELECT * FROM users WHERE username=?"
	                );

	                stmt.setString(1, username);

	                resultSet = stmt.executeQuery();

	                // Check if the result set contains any rows
	                if (resultSet.next()) {
	                    // If a room is found, create a Room object and populate its attributes
	                    result = new User();
	                    result.setUserID(resultSet.getInt(1));
	                    result.setUsername(resultSet.getString(2));
	                    result.setPassword(resultSet.getString(3));
	                
	                } else {
	                    // No room found
	                    System.out.println("User with u " + username + " not found.");
	                }
	            } finally {
	                // Close resources
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }

	            return result;
	        }
	    });
	}

	
	public void insertUser(String username, String password) {
	    executeTransaction(new Transaction<Void>() {
	        @Override
	        public Void execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;

	            try {
	                // Construct the SQL query to insert a new user
	                stmt = conn.prepareStatement(
	                        "INSERT INTO users (username, password) VALUES (?, ?)"
	                );

	                stmt.setString(1, username);
	                stmt.setString(2, password);

	                // Execute the insert query
	                stmt.executeUpdate();

	                System.out.println("User " + username + " inserted successfully.");
	            } finally {
	                // Close resources
	                DBUtil.closeQuietly(stmt);
	            }

	            return null;
	        }
	    });
	}

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
		Connection conn = DriverManager.getConnection("jdbc:derby:tbag.db;create=true");
		
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
		roomConnection.setConnectionID(resultSet.getInt(index++));
		roomConnection.setRoomID(resultSet.getInt(index++));
		roomConnection.setMove(resultSet.getString(index++));
		roomConnection.setDestId(resultSet.getInt(index++));
	}
	
	private void loadActor(Actor actor, ResultSet resultSet, int index) throws SQLException {
		actor.setActorID(resultSet.getInt(index++));
		actor.setRoomID(resultSet.getInt(index++));
		actor.setName(resultSet.getString(index++));
		actor.setLevel(resultSet.getInt(index++));
		actor.setXP(resultSet.getInt(index++));
		actor.setCurrentHealth(resultSet.getInt(index++));
		actor.setMaxHealth(resultSet.getInt(index++));
	}
	
	private void loadItems(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setItemID(resultSet.getInt(index++));
		item.setType(resultSet.getInt(index++));
		item.setName(resultSet.getString(index++));
		item.setDescription(resultSet.getString(index++));
		item.setThrowable(resultSet.getString(index++));
		item.setDamage(resultSet.getInt(index++));
		item.setEffect(resultSet.getString(index++));
		item.setRoomID(resultSet.getInt(index++));
		item.setOwnerID(resultSet.getInt(index++));
		}
	
	private void loadUsers(User user, ResultSet resultSet, int index) throws SQLException {
	    user.setUserID(resultSet.getInt(index++));
	    user.setUsername(resultSet.getString(index++));
	    user.setPassword(resultSet.getString(index++));
	}

	private void loadSaveData(SaveData saveData, ResultSet resultSet, int index) throws SQLException {
		saveData.setSaveID(resultSet.getInt(index++));
		saveData.setSaveType(resultSet.getString(index++));
		saveData.setIdSlot1(resultSet.getInt(index++));
		saveData.setIdSlot2(resultSet.getInt(index++));
		saveData.setIdSlot3(resultSet.getInt(index++));
		saveData.setLevel(resultSet.getInt(index++));
		saveData.setXp(resultSet.getInt(index++));
		saveData.setCurrentHealth(resultSet.getInt(index++));
		saveData.setMaxHealth(resultSet.getInt(index++));
		saveData.setHasVisited(resultSet.getString(index++));
		saveData.setNeedsKey(resultSet.getString(index++));
		saveData.setLog(resultSet.getString(index++));
	}
	
	private void loadSaveGame(SaveGame saveGame, ResultSet resultSet, int index) throws SQLException {
		saveGame.setUserID(resultSet.getInt(index++));
		saveGame.setSaveName(resultSet.getString(index++));
		saveGame.setSaveID(resultSet.getInt(index++));
		
	}	
	
	
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {

				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt7 = null;
				
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
							"	 room_id integer constraint room_id references rooms, " +
						    "    move varchar(40)," +
						    "    destId integer" +
						    ")"
						);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
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
					stmt3.executeUpdate();
					
					stmt4 = conn.prepareStatement(
							"create table items (" +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	type integer," +
							"   name varchar(40)," +
							"	description varchar(100)," +
							"   throwable varchar(40)," +
							"   damage integer," + 
							"   effect varchar(40)," + 
							"   room_id integer," + 
							"   owner_id integer" + 
							")"
						);
						stmt4.executeUpdate();
						
					stmt5 = conn.prepareStatement(
							"create table users (" +
							"	user_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"   username varchar(40)," +
							"	password varchar(40)" +
							")"
						);
						stmt5.executeUpdate();
						
					stmt6 = conn.prepareStatement(
								"create table saves (" +
								"	save_id integer,"+									
								"   save_type varchar(40)," +
								"	id_slot1 integer," +
								"	id_slot2 integer," +
								"	id_slot3 integer," +
								"	actor_level integer," +
								"	actor_xp integer," +
								"	actor_current_health integer," +
								"	actor_max_health integer," +
								"	room_has_visited varchar(40)," +
								"	room_needs_key varchar(40)," +
								"   log varchar(4000)" + 
								")"
							);
						stmt6.executeUpdate();	
							
						stmt7 = conn.prepareStatement("create table save_games (" +
								"	user_id integer,"+									
								"   save_name varchar(40)," +
								"	save_id integer" +
								")");
						stmt7.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);
				}
			}
		});
	}
	
	public void reCreateTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {

				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				
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
							"	 room_id integer constraint room_id references rooms, " +
						    "    move varchar(40)," +
						    "    destId integer" +
						    ")"
						);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
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
					stmt3.executeUpdate();
					
					stmt4 = conn.prepareStatement(
							"create table items (" +
							"	item_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	type integer," +
							"   name varchar(40)," +
							"	description varchar(100)," +
							"   throwable varchar(40)," +
							"   damage integer," + 
							"   effect varchar(40)," + 
							"   room_id integer," + 
							"   owner_id integer" + 
							")"
						);
						stmt4.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}
			}
		});
	}

	public void dropTables() {
	    executeTransaction(new Transaction<Boolean>() {
	        @Override
	        public Boolean execute(Connection conn) throws SQLException {
	            PreparedStatement stmt1 = null;
	            PreparedStatement stmt2 = null;
	            PreparedStatement stmt3 = null;
	            PreparedStatement stmt4 = null;
	            PreparedStatement dropFk1 = null;
	            PreparedStatement dropFk2 = null;

	            try {
	                // Drop foreign key constraints
	                dropFk1 = conn.prepareStatement("ALTER TABLE roomConnections DROP CONSTRAINT room_id");
	                dropFk1.executeUpdate();

	                // Drop rooms table
	                stmt1 = conn.prepareStatement("DROP TABLE rooms");
	                stmt1.executeUpdate();

	                // Drop roomConnections table
	                stmt2 = conn.prepareStatement("DROP TABLE roomConnections");
	                stmt2.executeUpdate();

	                // Drop actors table
	                stmt3 = conn.prepareStatement("DROP TABLE actors");
	                stmt3.executeUpdate();

	                // Drop items table
	                stmt4 = conn.prepareStatement("DROP TABLE items");
	                stmt4.executeUpdate();

	                System.out.println("Tables dropped successfully.");
	                return true;
	            } finally {
	                // Close prepared statements
	                DBUtil.closeQuietly(stmt1);
	                DBUtil.closeQuietly(stmt2);
	                DBUtil.closeQuietly(stmt3);
	                DBUtil.closeQuietly(stmt4);
	                DBUtil.closeQuietly(dropFk1);
	                DBUtil.closeQuietly(dropFk2);
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
	            List<Actor> actorList;
	            List<Item> itemList;
	            List<User> userList;

	            try {
	                roomList = InitialData.getRooms();
	                connectionList = InitialData.getConnections();
	                actorList = InitialData.getActors();
	                itemList = InitialData.getItems();
	                userList = InitialData.getUsers();
	            } catch (IOException e) {
	                throw new SQLException("Couldn't read initial data", e);
	            }

	            PreparedStatement insertRoom = null;
	            PreparedStatement insertConnection = null;
	            PreparedStatement insertActor = null;
	            PreparedStatement insertItem = null;
	            PreparedStatement insertUsers = null;

	            try {
	                // Populate rooms table
	                insertRoom = conn.prepareStatement("insert into rooms (name, longDescription, shortDescription, hasVisited, needsKey, keyName) values (?, ?, ?, ?, ?, ?)");
	                for (Room room : roomList) {
	                    insertRoom.setString(1, room.getName());
	                    insertRoom.setString(2, room.getLongDescription());
	                    insertRoom.setString(3, room.getShortDescription());
	                    insertRoom.setString(4, room.getVisited());
	                    insertRoom.setString(5, room.getNeedsKey());
	                    insertRoom.setString(6, room.getKeyName());
	                    insertRoom.addBatch();
	                }
	                insertRoom.executeBatch();

	                // Populate connections table
	                insertConnection = conn.prepareStatement("insert into roomConnections (room_id, move, destId) values (?, ?, ?)");
	                for (RoomConnection roomConnection : connectionList) {
	                    insertConnection.setInt(1, roomConnection.getRoomID());
	                    insertConnection.setString(2, roomConnection.getMove());
	                    insertConnection.setInt(3, roomConnection.getDestId());
	                    insertConnection.addBatch();
	                }
	                insertConnection.executeBatch();

	                // Populate actors table
	                insertActor = conn.prepareStatement("insert into actors (room_id, name, level, xp, current_health, max_health) values (?, ?, ?, ? ,?, ?)");
	                for (Actor actor : actorList) {
	                    insertActor.setInt(1, actor.getRoomID());
	                    insertActor.setString(2, actor.getName());
	                    insertActor.setInt(3, actor.getLevel());
	                    insertActor.setInt(4, actor.getXP());
	                    insertActor.setInt(5, actor.getCurrentHealth());
	                    insertActor.setInt(6, actor.getMaxHealth());
	                    insertActor.addBatch();
	                }
	                insertActor.executeBatch();

	                // Populate items table
	                insertItem = conn.prepareStatement("insert into items (type, name, description, throwable, damage, effect, room_id, owner_id) values (?, ?, ?, ?, ?, ?, ?, ?)");
	                for (Item item : itemList) {
	                    insertItem.setInt(1, item.getType());
	                    insertItem.setString(2, item.getName());
	                    insertItem.setString(3, item.getDescription());
	                    insertItem.setString(4, item.getThrowable());
	                    insertItem.setInt(5, item.getDamage());
	                    insertItem.setString(6, item.getEffect());
	                    insertItem.setInt(7, item.getRoomID());
	                    insertItem.setInt(8, item.getOwnerID());
	                    insertItem.addBatch();
	                }
	                insertItem.executeBatch();

	                // Populate users table
	                insertUsers = conn.prepareStatement("insert into users (username, password) values (?, ?)");
	                for (User user : userList) {
	                    insertUsers.setString(1, user.getUsername());
	                    insertUsers.setString(2, user.getPassword());
	                    insertUsers.addBatch();
	                }
	                insertUsers.executeBatch();
	                
	                actorCount = actorList.size();
	                roomCount = roomList.size();
	                itemCount = itemList.size();
	                return true;
	            } finally {
	                DBUtil.closeQuietly(insertRoom);
	                DBUtil.closeQuietly(insertConnection);
	                DBUtil.closeQuietly(insertActor);
	                DBUtil.closeQuietly(insertItem);
	                DBUtil.closeQuietly(insertUsers);
	            }
	        }
	    });
	}
	
	public void reLoadInitialData() {
	    executeTransaction(new Transaction<Boolean>() {
	        @Override
	        public Boolean execute(Connection conn) throws SQLException {
	            List<Room> roomList;
	            List<RoomConnection> connectionList;
	            List<Actor> actorList;
	            List<Item> itemList;
	           
	            try {
	                roomList = InitialData.getRooms();
	                connectionList = InitialData.getConnections();
	                actorList = InitialData.getActors();
	                itemList = InitialData.getItems();
	               
	            } catch (IOException e) {
	                throw new SQLException("Couldn't read initial data", e);
	            }

	            PreparedStatement insertRoom = null;
	            PreparedStatement insertConnection = null;
	            PreparedStatement insertActor = null;
	            PreparedStatement insertItem = null;
	           

	            try {
	                // Populate rooms table
	                insertRoom = conn.prepareStatement("insert into rooms (name, longDescription, shortDescription, hasVisited, needsKey, keyName) values (?, ?, ?, ?, ?, ?)");
	                for (Room room : roomList) {
	                    insertRoom.setString(1, room.getName());
	                    insertRoom.setString(2, room.getLongDescription());
	                    insertRoom.setString(3, room.getShortDescription());
	                    insertRoom.setString(4, room.getVisited());
	                    insertRoom.setString(5, room.getNeedsKey());
	                    insertRoom.setString(6, room.getKeyName());
	                    insertRoom.addBatch();
	                }
	                insertRoom.executeBatch();

	                // Populate connections table
	                insertConnection = conn.prepareStatement("insert into roomConnections (room_id, move, destId) values (?, ?, ?)");
	                for (RoomConnection roomConnection : connectionList) {
	                    insertConnection.setInt(1, roomConnection.getRoomID());
	                    insertConnection.setString(2, roomConnection.getMove());
	                    insertConnection.setInt(3, roomConnection.getDestId());
	                    insertConnection.addBatch();
	                }
	                insertConnection.executeBatch();

	                // Populate actors table
	                insertActor = conn.prepareStatement("insert into actors (room_id, name, level, xp, current_health, max_health) values (?, ?, ?, ? ,?, ?)");
	                for (Actor actor : actorList) {
	                    insertActor.setInt(1, actor.getRoomID());
	                    insertActor.setString(2, actor.getName());
	                    insertActor.setInt(3, actor.getLevel());
	                    insertActor.setInt(4, actor.getXP());
	                    insertActor.setInt(5, actor.getCurrentHealth());
	                    insertActor.setInt(6, actor.getMaxHealth());
	                    insertActor.addBatch();
	                }
	                insertActor.executeBatch();

	                // Populate items table
	                insertItem = conn.prepareStatement("insert into items (type, name, description, throwable, damage, effect, room_id, owner_id) values (?, ?, ?, ?, ?, ?, ?, ?)");
	                for (Item item : itemList) {
	                    insertItem.setInt(1, item.getType());
	                    insertItem.setString(2, item.getName());
	                    insertItem.setString(3, item.getDescription());
	                    insertItem.setString(4, item.getThrowable());
	                    insertItem.setInt(5, item.getDamage());
	                    insertItem.setString(6, item.getEffect());
	                    insertItem.setInt(7, item.getRoomID());
	                    insertItem.setInt(8, item.getOwnerID());
	                    insertItem.addBatch();
	                }
	                insertItem.executeBatch();

	                actorCount = actorList.size();
	                roomCount = roomList.size();
	                itemCount = itemList.size();
	                return true;
	            } finally {
	                DBUtil.closeQuietly(insertRoom);
	                DBUtil.closeQuietly(insertConnection);
	                DBUtil.closeQuietly(insertActor);
	                DBUtil.closeQuietly(insertItem);
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

}
