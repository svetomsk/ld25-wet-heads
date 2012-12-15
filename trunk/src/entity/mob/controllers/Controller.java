package entity.mob.controllers;

import entity.mob.Mob;
import items.Item;

public class Controller {

	protected Mob mob;
	
	public Controller(Mob mob)
	{
		this.mob = mob;
	}
	
	public void tick()
	{
		
	}

	public boolean tryGet(Item item) {
		return false;
	}
}
