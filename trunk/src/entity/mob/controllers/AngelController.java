package entity.mob.controllers;

import entity.mob.Character;
import entity.mob.Mob;

public class AngelController extends Controller{

	public AngelController(Mob mob)
	{
		super(mob);
	}	
	public void tick()
	{
		Character character = mob.getWorld().getCharacter();
		if(character == null)
		{
			return;
		}
		
		boolean right = (character.getCX()-mob.getCX()>0);		
		if(right) mob.onRight();
		else mob.onLeft();
		
		if(Math.abs(mob.getLVX()) < 3) mob.onUp();
	}
}
