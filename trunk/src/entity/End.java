package entity;

import main.Game;
import entity.mob.Character;

public class End extends Entity
{

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
