package entity.mob;

import items.seeds.JumpMignonSeed;
import main.World;
import entity.mob.controllers.ButterflyController;
import entity.mob.mignons.Mignon;

public class Butterfly extends Mob
{
	@Override
	protected void finalInit(World world)
	{
		super.finalInit(world);
		control = new ButterflyController(this);
	}

	@Override
	public boolean addMignon(Mignon mignon) { return false; }
	
	@Override
	public void onDeath()
	{
		new JumpMignonSeed().init(x + getWidth() / 2, y + getHeight() / 2,
				world);
		super.onDeath();
	}

	@Override
	public double getSpeed()
	{
		return 7;
	}

	@Override
	public double getJumpPower()
	{
		return 0;
	}

	@Override
	public int getMaxHP()
	{
		return 20;
	}

	@Override
	public int getDamage()
	{
		return 0;
	}

	@Override
	public int getKnokback()
	{
		return 0;
	}

	@Override
	public double getStrength()
	{
		return 0;
	}

	@Override
	public int getWidth()
	{
		return 32;
	}

	@Override
	public int getHeight()
	{
		return 32;
	}
}
