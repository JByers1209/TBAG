package edu.ycp.cs320.tbag.main;

import java.util.Scanner;

import edu.ycp.cs320.tbag.dataBase.DatabaseProvider;
import edu.ycp.cs320.tbag.dataBase.IDatabase;
import edu.ycp.cs320.tbag.dataBase.InitDatabase;
import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.NPC;



public class TestDatabase {

		public static void main(String[] args) throws Exception {
			Scanner keyboard = new Scanner(System.in);

			// Create the default IDatabase instance
			InitDatabase.init();
			
			System.out.print("Enter Actor ID: ");
			int actorID = Integer.parseInt(keyboard.nextLine());
			
			// get the DB instance and execute transaction
			IDatabase db = DatabaseProvider.getInstance();
			
		
			
			Actor actor = db.findActorByID(actorID);
			System.out.println(actor.toString());
			
		
			System.out.print("Enter Room ID: ");
			int roomID = Integer.parseInt(keyboard.nextLine());
			
			Actor actor2 = db.findActorByRoomID(roomID);
			System.out.println(actor2.toString());
			
			Actor actor3 = new NPC(2, 7, "Less Friendly Ghost", 4, 600, 6, 64);
			db.updateActor(2, actor3);
			
			Actor actor4 = db.findActorByID(2);
		
	
			}
		}
	


