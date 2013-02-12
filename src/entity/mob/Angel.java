package entity.mob;

import main.World;
import entity.mob.controllers.ArchAngelController;
import entity.mob.mignons.LightMignon;

public class Angel extends Mob{
	
	@Override
	protected void finalInit(World world)
	{
		super.finalInit(world);
		control = new ArchAngelController(this);
	}
	@Override
	protected void afterBirth()
	{
		super.afterBirth();
		for(int q=0;q<3;q++)
		{
			new LightMignon().init(x+q, y+q, world, this);
		}
	}
	@Override
	protected boolean interactOnCharacter(Character character) 
	{
		double dir = character.getX()-x >= 0 ? 1 : -1; 
		character.damage(getDamage(), getKnokback(), dir);
		return true;
	}
    @Override
	public double getSpeed()
	{
		return 9;
	}
    @Override
	public double getJumpPower()
	{
		return 13;
	}
    @Override
	public int getMaxHP()
	{
		return 50;
	}
    @Override
	public int getDamage()
	{
		return 5;
	}
    @Override
	public int getKnokback()
	{
		return 7;
	}
    @Override
	public double getStrength()
	{
		return 0;
	}
	@Override
	public int getWidth()
	{
		return 42;
	}
	@Override
	public int getHeight()
	{
		return 64;
	}
}
