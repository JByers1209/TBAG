package edu.ycp.cs320.tbag.model;

public class Weapon extends Item {
 private int damage;

 public Weapon(String name, boolean throwable, int damage) {
     super(name, throwable);
     this.damage = damage;
 }
 
 public int getDamage() {
     return damage;
 }
}