package entity.mob.mignons;

import items.seeds.JumpMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Pictures;
import entity.mob.Mob;

public class JumpMignon extends Mignon{

	@Override
	protected void initPictures() 
	{
		img = Pictures.jumpMignon;
		super.initPictures();
	}
	@Override
	public void loseOwner() 
	{
		super.loseOwner();
		new JumpMignonSeed().init(x, y, lvx, lvy, gvx, gvy, world);
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