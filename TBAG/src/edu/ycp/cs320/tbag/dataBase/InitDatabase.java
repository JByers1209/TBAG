package edu.ycp.cs320.tbag.dataBase;

import java.util.Scanner;


public class InitDatabase {
	public static void init(Scanner keyboard) {
		System.out.print("Which database (0=fake, 1=derby): ");
		int which = Integer.parseInt(keyboard.nextLine());
		if (which == 0) {
			//DatabaseProvider.setInstance(new FakeDatabase());
		} else if (which == 1) {
			DatabaseProvider.setInstance(new DerbyDatabase());
		} else {
			throw new IllegalArgumentException("Invalid choice: " + which);
		}
	}
	
	public static void init() {
		DatabaseProvider.setInstance(new DerbyDatabase());
	}
}
