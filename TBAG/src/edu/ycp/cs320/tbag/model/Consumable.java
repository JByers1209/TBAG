package edu.ycp.cs320.tbag.model;

public class Consumable extends Item {
    private String effect;

    public Consumable(String name, boolean throwable, String effect) {
        super(name, throwable);
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }
}
