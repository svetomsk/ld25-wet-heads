package entity.mob.mignons;

import items.seeds.LightMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Pictures;

public class DarkMignon extends Mignon
{

//	private int recoveryTime;

	@Override
	protected void initPictures()
	{
		img = Pictures.darkMignon;
		super.initPictures();
	}

	@Override
	public void loseOwner()
	{
		super.loseOwner();
		new LightMignonSeed().init(x, y, lvx, lvy, gvx, gvy, world);
	}

	@Override
	public void tick()
	{
		super.tick();
//		recoveryTime--;
	}

//	@Override
//	protected boolean interactOnMob(Mob mob) {
//		if( !super.interactOnMob(mob)) return false;
//		if(recoveryTime > 0) return false;
//		if(getOwner().getGroup().isMember(mob)) return false;
//		
//		double dir = mob.getX()-x >= 0 ? 1 : -1; 
//		mob.damage(10, 0, dir);
//		recoveryTime = 150;
//		return super.interactOnMob(mob);
//	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.black);
		super.draw(g);
	}
}
