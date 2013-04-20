package entity.mob.controllers;

import entity.mob.Character;
import entity.mob.Mob;

public class ArchAngelController extends Controller{

	public ArchAngelController(Mob mob)
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
		
		if(Math.abs(character.getX()-mob.getX())>1000) return;
		
		boolean right = (character.getX()-mob.getX()>0);		
		if(right) mob.onRight();
		else mob.onLeft();
		
		if(Math.abs(mob.getLVX()) < 3) mob.onUp();
		
		floakFollow(character.getX(), character.getY());
	}
}
