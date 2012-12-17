package entity.mob.mignons;

import items.seeds.DamageMignonSeed;
import items.seeds.JumpMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Pictures;
import main.World;
import entity.mob.Mob;

public class JumpMignon extends Mignon{

	public JumpMignon(long x, long y, World world) {
		super(x, y, world);
	}
	public JumpMignon(long x, long y, World world, Mob owner) {
		super(x, y, world, owner);
	}
	@Override
	protected void initPictures() 
	{
		img = Pictures.jumpMignon;
	}
	@Override
	public void loseOwner() 
	{
		super.loseOwner();
		new JumpMignonSeed(x+getWidth()/2, y+getHeight()/2, world);
	}
	@Override
	protected boolean interactOnMob(Mob mob) {
		if( !super.interactOnMob(mob)) return false;
		if( control.isReturned()) return false;
		
		mob.throwUp();
		
		return super.interactOnMob(mob);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		super.draw(g);
	}
}