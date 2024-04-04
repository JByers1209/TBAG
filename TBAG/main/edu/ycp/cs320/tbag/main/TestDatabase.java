package edu.ycp.cs320.tbag.main;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.tbag.dataBase.DatabaseProvider;
import edu.ycp.cs320.tbag.dataBase.IDatabase;
import edu.ycp.cs320.tbag.dataBase.InitDatabase;



public class TestDatabase {

		public static void main(String[] args) throws Exception {
			Scanner keyboard = new Scanner(System.in);

			// Create the default IDatabase instance
			InitDatabase.init(keyboard);
			
			System.out.print("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEF: ");
			String lastName = keyboard.nextLine();
			
			// get the DB instance and execute transaction
			IDatabase db = DatabaseProvider.getInstance();
			
			
	
			}
		}
	


