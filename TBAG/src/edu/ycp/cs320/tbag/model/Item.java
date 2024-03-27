package edu.ycp.cs320.tbag.model;


public abstract class Item {
    private String name;
	private boolean throwable;

    public Item(String name, boolean throwable) {
        this.name = name;
        this.throwable = false;
    }

    public String getName() {
        return name;
    }
    
    public boolean getThrowable() {
    	return throwable;
    }
}
