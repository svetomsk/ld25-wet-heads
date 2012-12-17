package entity;

import entity.mob.Character;
import main.Game;
import main.World;

public class End extends Entity
{

	public End(long x, long y, World world) 
	{
		super(x, y, world);
	}
	
	@Override
	protected boolean interactOnCharacter(Character character) 
	{
		Game.showEnd();
		return true;
	}
	
	@Override
	public int getHeight() 
	{
		return 64;
	}
	@Override
	public int getWidth() 
	{
		return 64;
	}

}
