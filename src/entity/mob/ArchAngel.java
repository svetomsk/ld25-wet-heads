package entity.mob;

import main.World;
import entity.mob.controllers.AngelController;
import entity.mob.mignons.LightMignon;

public class ArchAngel extends Mob
{

	@Override
	protected void finalInit(World world)
	{
		super.finalInit(world);
		control = new AngelController(this);
	}

	@Override
	protected void afterBirth()
	{
		super.afterBirth();
		for (int q = 0; q < 64; q++)
		{
			new LightMignon().init(x + q, y + q, this);
		}
	}

	@Override
	protected boolean interactOnCharacter(Character character)
	{
		double dir = character.getCX() - x >= 0 ? 1 : -1;
		character.damage(getDamage(), getKnokback(), dir);
		return true;
	}

	@Override
	public double getSpeed()
	{
		return 7;
	}

	@Override
	public double getJumpPower()
	{
		return 13;
	}

	@Override
	public int getMaxHP()
	{
		return 5000;
	}

	@Override
	public int getDamage()
	{
		return 33;
	}

	@Override
	public int getKnokback()
	{
		return 15;
	}

	@Override
	public double getStrength()
	{
		return 0;
	}

	@Override
	public int getWidth()
	{
		return 128;
	}

	@Override
	public int getHeight()
	{
		return 192;
	}
}
