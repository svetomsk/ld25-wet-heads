package entity.mob.mignons;

import items.seeds.DamageMignonSeed;
import items.seeds.DarkMignonSeed;
import items.seeds.LightMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.Mob;
import entity.mob.controllers.MignonController;
import main.Pictures;
import main.World;

public class LightMignon extends Mignon{

	private int recoveryTime;
	
	public LightMignon(long x, long y, World world) {
		super(x, y, world);
	}
	public LightMignon(long x, long y, World world, Mob owner) {
		super(x, y, world, owner);
	}
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
		new DarkMignonSeed(x+getWidth()/2, y+getHeight()/2, world);
	}
	@Override
	public void tick() {
		super.tick();
		recoveryTime--;
	}
	
	@Override
	protected boolean interactOnMob(Mob mob) {
		if( !super.interactOnMob(mob)) return false;
		if(recoveryTime > 0) return false;
		if(getOwner().getGroup().isMember(mob)) return false;
		
		double dir = mob.getX()-x >= 0 ? 1 : -1; 
		mob.damage(10, 0, dir);
		recoveryTime = 150;
		return super.interactOnMob(mob);
	}
	
	@Override
	public void draw(Graphics2D g) 
	{
		g.setColor(Color.black);
		super.draw(g);
	}
}
