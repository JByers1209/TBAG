package edu.ycp.cs320.tbag.dataBase;

import java.util.List;
import edu.ycp.cs320.tbag.model.Actor;


public interface IDatabase {
	
	
	public Actor findActorByID(int actorID);

}
