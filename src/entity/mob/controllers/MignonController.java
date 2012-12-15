package entity.mob.controllers;

import entity.Entity;
import entity.mob.Mignon;
import entity.mob.Mob;

public class MignonController extends Controller{
	
	private Mignon mob;
	
	private long x, y;

	public MignonController(Mignon mob) {
		super(mob);
		this.mob = mob;
	}

	public void lostOwner()
	{
		x = mob.getX();
		y = mob.getY();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(mob.getOwner() == null)
		{
			mob.spin(x, y);
		}
		else
		{
			mob.spin(mob.getOwner().getX()+mob.getOwner().getWidth()/2, mob.getOwner().getY()+mob.getOwner().getHeight()/2);
		}
	}
	
}
