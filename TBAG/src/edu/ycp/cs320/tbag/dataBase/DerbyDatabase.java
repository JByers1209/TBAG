package edu.ycp.cs320.booksdb.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.tbag.model.Room;
import edu.ycp.cs320.tbag.model.RoomConnection;

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

	@Override
	public List<Room> findConnectionByRoomID(String roomId, String move) {
		return executeTransaction(new Transaction<List<Room>>() {
			@Override
			public List<Room> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select Rooms.*, RoomConnections.* " +
							"  from authors, books " +
							" where authors.author_id = books.author_id " +
							"   and books.title = ?"
					);
					stmt.setString(1, );
					
					List<Room> result = new ArrayList<Room>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Author object
						// retrieve attributes from resultSet starting with index 1
						Room room = new Room();
						loadRoom(room), resultSet, 1);
						
						// create new Book object
						// retrieve attributes from resultSet starting at index 4
						Book book = new Book();
						loadRoom(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + room + "> was not found in the books table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String lastname) {
		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
			@Override
			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							"select authors.*, books.* "
							+ "  from authors, books "
							+ "  where authors.author_id = books.author_id and "
							+ "        authors.lastname = ? "
							+ "  order by title ASC"
					);
					stmt.setString(1, lastname);
					
					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						
						// create new Author object
						// retrieve attributes from resultSet starting with index 1
						Author author = new Author();
						loadAuthor(author, resultSet, 1);
						
						// create new Book object
						// retrieve attributes from resultSet starting at index 4
						Book book = new Book();
						loadBook(book, resultSet, 4);
						
						result.add(new Pair<Author, Book>(author, book));
					}
					
					// check if the title was found
					if (!found) {
						System.out.println("<" + lastname + "> was not found in the authors table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
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
		room.setVisited(resultSet.getBoolean(index++));
		room.setNeedsKey(resultSet.getBoolean(index++));
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
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				
				try {
					stmt1 = conn.prepareStatement(
						"create table authors (" +
						"	author_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	lastname varchar(40)," +
						"	firstname varchar(40)" +
						")"
					);	
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"create table books (" +
							"	book_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	author_id integer constraint author_id references authors, " +
							"	title varchar(70)," +
							"	isbn varchar(15)," +
							"   published integer " +
							")"
					);
					stmt2.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
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
				
				try {
					roomList = InitialData.getRooms();
					connectionList = InitialData.getConnections();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertRoom = null;
				PreparedStatement insertConnection   = null;

				try {
					// populate rooms table (do authors first, since author_id is foreign key in books table)
					insertRoom = conn.prepareStatement("insert into rooms (roomId, name, longDescription, shortDescription, hasVisited, needsKey, keyName) values (?, ?, ?, ?, ?, ?)");
					for (Room room : roomList) {
//						insertRoom.setInt(1, room.getRoomId());		// auto-generated primary key, don't insert this
						insertRoom.setString(1, room.getName());
						insertRoom.setString(2, room.getLongDescription());
						insertRoom.setString(3, room.getShortDescription());
						insertRoom.setBoolean(4, room.getVisited());
						insertRoom.setBoolean(5, room.getNeedsKey());
						insertRoom.setString(6, room.getKeyName());
						insertRoom.addBatch();
					}
					insertRoom.executeBatch();
					
					// populate connections table
					insertConnection = conn.prepareStatement("insert into roomConnections (roomId, move1, dest1, move2, dest2, move3, dest3, move4, dest4) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertRoom);
					DBUtil.closeQuietly(insertConnection);
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