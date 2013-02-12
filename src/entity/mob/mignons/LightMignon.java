package entity.mob.mignons;

import items.seeds.DarkMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Pictures;
import entity.mob.Mob;

public class LightMignon extends Mignon
{

	private int recoveryTime;

	@Override
	protected void initPictures()
	{
		img = Pictures.lightMignon;
		super.initPictures();
	}

	@Override
	public void loseOwner()
	{
		super.loseOwner();
		new DarkMignonSeed().init(x + getWidth() / 2, y + getHeight() / 2, world);
	}

	@Override
	public void tick()
	{
		super.tick();
		recoveryTime--;
	}

	@Override
	protected boolean interactOnMob(Mob mob)
	{
		if (!super.interactOnMob(mob))
			return false;
		if (recoveryTime > 0)
			return false;
		if (getOwner().getGroup().isMember(mob))
			return false;

		double dir = mob.getX() - x >= 0 ? 1 : -1;
		mob.damage(getDamage(), getKnokback(), dir);
		recoveryTime = 150;
		return super.interactOnMob(mob);
	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.black);
		super.draw(g);
	}
	
	@Override
	public int getDamage()
	{
		return 10;
	}
	@Override
	public int getKnokback()
	{
		return 0;
	}
}