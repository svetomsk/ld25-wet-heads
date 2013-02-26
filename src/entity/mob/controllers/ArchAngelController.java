package entity.mob.controllers;

import entity.mob.Mob;

public class ArchAngelController extends Controller{

	public ArchAngelController(Mob mob)
	{
		super(mob);
	}	
	public void tick()
	{
		if(Math.abs(mob.getWorld().character.getX()-mob.getX())>1000) return;
		
		boolean right = (mob.getWorld().character.getX()-mob.getX()>0);		
		if(right) mob.onRight();
		else mob.onLeft();
		
		if(Math.abs(mob.getLVX()) < 3) mob.onUp();
		
		floakFollow(mob.getWorld().character.getX(), mob.getWorld().character.getY());
	}
}
