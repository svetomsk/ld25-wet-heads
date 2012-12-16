package entity.mob.mignons;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.Mob;
import entity.mob.controllers.MignonController;
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
		g.setColor(Color.red);
		super.draw(g);
	}
}
