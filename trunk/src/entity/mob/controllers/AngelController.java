package entity.mob.controllers;

import entity.mob.Mob;
import main.World;

public class AngelController extends Controller{

	public AngelController(Mob mob)
	{
		super(mob);
	}	
	public void tick()
	{
		boolean right = (mob.getWorld().character.getX()-mob.getX()>0);		
		if(right) mob.onRight();
		else mob.onLeft();
		
		if(Math.abs(mob.getLVX()) < 3) mob.onUp();
	}
}
