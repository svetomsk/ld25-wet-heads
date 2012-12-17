package entity.mob.mignons;

import items.seeds.DamageMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.Mob;
import entity.mob.controllers.MignonController;
import main.Game;
import main.Pictures;
import main.World;

public class DamageMignon extends Mignon{

	private int recoveryTime;
	
	public DamageMignon(long x, long y, World world) {
		super(x, y, world);
	}
	public DamageMignon(long x, long y, World world, Mob owner) {
		super(x, y, world, owner);
	}
	@Override
	protected void initPictures() 
	{
		img = Pictures.damageMignon;
	}
	@Override
	public void loseOwner() 
	{
		super.loseOwner();
		new DamageMignonSeed(x+getWidth()/2, y+getHeight()/2, world);
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
}
