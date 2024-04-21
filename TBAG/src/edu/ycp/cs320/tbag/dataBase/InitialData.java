package edu.ycp.cs320.tbag.dataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import edu.ycp.cs320.tbag.model.Actor;
import edu.ycp.cs320.tbag.model.NPC;
import edu.ycp.cs320.tbag.model.Player;



public class InitialData {

	public static List<Actor> getActors() throws IOException {
		List<Actor> actorList = new ArrayList<Actor>();
		ReadCSV readActors = new ReadCSV("Actors.csv");
		try {
			int actorID = 1;
			while (true) {
				List<String> tuple = readActors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
			
				if(actorID == 1) {
					Player actor = new Player();
					actor.setActorID(actorID);
					actor.setRoomID(Integer.parseInt(i.next()));
					actor.setName(i.next());
					actor.setLevel(Integer.parseInt(i.next()));
					actor.setXP(Integer.parseInt(i.next()));
					actor.setCurrentHealth(Integer.parseInt(i.next()));
					actor.setMaxHealth(Integer.parseInt(i.next()));
					actorList.add(actor);
					
				}else {
					NPC actor = new NPC();
					actor.setActorID(actorID);
					actor.setRoomID(Integer.parseInt(i.next()));
					actor.setName(i.next());
					actor.setLevel(Integer.parseInt(i.next()));
					actor.setXP(Integer.parseInt(i.next()));
					actor.setCurrentHealth(Integer.parseInt(i.next()));
					actor.setMaxHealth(Integer.parseInt(i.next()));
					actorList.add(actor);
				}
				actorID++;
				
			}
			return actorList;
		} finally {
			readActors.close();
		}
	}
	
}

