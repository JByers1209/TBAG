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

	
//<<<<<<< HEAD
//=======
	@Override
	public int findConnectionByRoomIDandDirection(int roomId, String move) {
	    return executeTransaction(new Transaction<Integer>() {
	        @Override
	        public Integer execute(Connection conn) throws SQLException {
	            PreparedStatement stmt = null;
	            ResultSet resultSet = null;
	            int destinationId = 0; // Default value if no connection found or 'none' is returned
	            
	            try {
	                // Construct the SQL query to fetch the destination based on room ID and direction
	                String query = "";
	                switch (move.toLowerCase()) {
	                    case "north":
	                        query = "SELECT dest1 FROM RoomConnections WHERE room_id = ?";
	                        break;
	                    case "west":
	                        query = "SELECT dest2 FROM RoomConnections WHERE room_id = ?";
	                        break;
	                    case "east":
	                        query = "SELECT dest3 FROM RoomConnections WHERE room_id = ?";
	                        break;
	                    case "south":
	                        query = "SELECT dest4 FROM RoomConnections WHERE room_id = ?";
	                        break;
	                    default:
	                        // Invalid direction
	                        System.out.println("Invalid direction: " + move);
	                        return destinationId;
	                }
	                
	                stmt = conn.prepareStatement(query);
	                stmt.setInt(1, roomId);
	                
	                resultSet = stmt.executeQuery();
	                
	                if (resultSet.next()) {
	                    // If a connection is found, retrieve the destination string
	                    String destString = resultSet.getString(1);
	                    // Transform string to integer, if 'none' return 0
	                    if (!destString.equals("none")) {
	                        destinationId = Integer.parseInt(destString);
	                    }
	                } else {
	                    System.out.println("No connection found for room " + roomId + " and direction " + move);
	                }
	            } catch (NumberFormatException e) {
	                // Handle parsing error
	                System.out.println("Error parsing destination ID.");
	            } finally {
	                // Close resources
	                DBUtil.closeQuietly(resultSet);
	                DBUtil.closeQuietly(stmt);
	            }
	            
	            return destinationId;
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
	
	public void updateRoomByRoomID(Room room) {
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


	/*
	public void insertNewBookWithAuthor(String firstname, String lastname, String title,
            String isbn, int published) {
        executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
            @Override
            public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                PreparedStatement stmt2 = null;
                ResultSet resultSet = null;

                try {

                    boolean authorInDatabase;
                    String author_id;

                    author_id = getAuthorID(conn, firstname, lastname);
                    if (author_id.equals("")) {
                        authorInDatabase = false;
                    }else {
                        authorInDatabase = true;
                    }

                    if(authorInDatabase == false) {
                        System.out.println("Adding Author to database...");
                        stmt = conn.prepareStatement(
                                "insert into authors (firstname, lastname) "
                                        + " values (?, ?)"

                                );

                        stmt.setString(1, firstname);
                        stmt.setString(2, lastname);
                        stmt.executeUpdate();

                        author_id = getAuthorID(conn, firstname, lastname);

                    }
// add book to database
                    stmt2 = conn.prepareStatement(
                            "insert into books (author_id, title, isbn, published) "
                            + " values (?, ?, ?, ?)"

                    );

                    stmt2.setString(1, author_id);
                    stmt2.setString(2, title);
                    stmt2.setString(3, isbn);
                    stmt2.setInt(4, published);


                    stmt2.executeUpdate();

                    return null;

                } finally {
                    DBUtil.closeQuietly(resultSet);
                    DBUtil.closeQuietly(stmt);
                }
            }
        });//end transaction

    }//end insert new author
	

private static String getAuthorID(Connection conn, String firstName, String lastName) throws SQLException {
        String author_id = "";
        PreparedStatement stmt;
        ResultSet resultSet;

        // get author id query
         stmt = conn.prepareStatement(
                "select authors.author_id"
                + "  from authors"
                + "  where authors.lastname = ?"
                + "and authors.firstname = ?"
        );


        // substitute the lastName entered by the user for the placeholder in the query
        stmt.setString(1, lastName);
        stmt.setString(2, firstName);
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
                author_id = obj.toString();
                if (i > 1) {
                    System.out.print(",");
                }
                System.out.print("Retrieved author id: "+ author_id);
            }
            System.out.println();

            // count # of rows returned
            rowsReturned++;
        }

        // indicate if the query returned nothing
        if (rowsReturned == 0) {
            System.out.println("No Author ID in database");
        }


        return author_id;
    }
	*/
//>>>>>>> master
	
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
			@SuppressWarnings("null")
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Room> roomList;
				List<RoomConnection> connectionList;
				List<User> userList = null;
				
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

//<<<<<<< HEAD
	@Override
	public int findConnectionByRoomIDandDirection(String roomId, String move) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateCurrentRoomByRoomAndActorID(int newRoomId, int actorId) {
		// TODO Auto-generated method stub
		
	}

	public Object findUserByUserID(int i, String string) {
		// TODO Auto-generated method stub
		return null;
	}
//=======
//>>>>>>> master
}