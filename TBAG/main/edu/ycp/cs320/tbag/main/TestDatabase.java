package edu.ycp.cs320.tbag.main;

import java.util.Scanner;

import edu.ycp.cs320.tbag.dataBase.DatabaseProvider;
import edu.ycp.cs320.tbag.dataBase.IDatabase;
import edu.ycp.cs320.tbag.dataBase.InitDatabase;
import edu.ycp.cs320.tbag.model.Actor;



public class TestDatabase {

		public static void main(String[] args) throws Exception {
			Scanner keyboard = new Scanner(System.in);

			// Create the default IDatabase instance
			InitDatabase.init(keyboard);
			
			System.out.print("Enter Actor ID: ");
			int actorID = Integer.parseInt(keyboard.nextLine());
			
			// get the DB instance and execute transaction
			IDatabase db = DatabaseProvider.getInstance();
			
			Actor actor = db.findActorByID(actorID);
			System.out.println(actor.toString());
			
	
			}
		}
	


