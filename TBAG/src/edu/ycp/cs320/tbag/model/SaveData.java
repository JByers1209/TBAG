package edu.ycp.cs320.tbag.model;





public class SaveData {
	
	
	private int saveID;
	private String saveType;
	private int idSlot1, idSlot2, idSlot3;
	private int level, xp, currentHealth, maxHealth;
	private String hasVisited, needsKey, log;
	
	
	
	public SaveData() {
		
	}
	
	public String getSaveType() {
		return saveType;
	}



	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}



	public int getIdSlot1() {
		return idSlot1;
	}



	public void setIdSlot1(int idSlot1) {
		this.idSlot1 = idSlot1;
	}



	public int getIdSlot2() {
		return idSlot2;
	}



	public void setIdSlot2(int idSlot2) {
		this.idSlot2 = idSlot2;
	}



	public int getIdSlot3() {
		return idSlot3;
	}



	public void setIdSlot3(int idSlot3) {
		this.idSlot3 = idSlot3;
	}



	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public int getXp() {
		return xp;
	}



	public void setXp(int xp) {
		this.xp = xp;
	}



	public int getCurrentHealth() {
		return currentHealth;
	}



	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}



	public int getMaxHealth() {
		return maxHealth;
	}



	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}



	public String getHasVisited() {
		return hasVisited;
	}



	public void setHasVisited(String hasVisited) {
		this.hasVisited = hasVisited;
	}



	public String getNeedsKey() {
		return needsKey;
	}



	public void setNeedsKey(String needsKey) {
		this.needsKey = needsKey;
	}



	public String getLog() {
		return log;
	}



	public void setLog(String log) {
		this.log = log;
	}



	public int getSaveID() {
		return saveID;
	}



	public void setSaveID(int saveID) {
		this.saveID = saveID;
	}
	
	
	

}
