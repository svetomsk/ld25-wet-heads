package entity.mob.controllers;

import java.util.ArrayList;

import entity.mob.Mob;

public class Group {

	public static Group mobs = new Group(); 
	public static Group villians = new Group();
	
	private ArrayList<Mob> members = new ArrayList<Mob>();
	
	public void addMob(Mob mob)
	{
		members.add(mob);
	}
	public void removeMob(Mob mob)
	{
		members.remove(mob);
	}
	public boolean isMember(Mob mob)
	{
		return members.contains(mob);
	}
	
}
