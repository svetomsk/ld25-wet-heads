package entity.mob.controllers;

import entity.Entity;
import entity.mob.Mob;
import entity.mob.mignons.Mignon;

public class MignonController extends Controller{
	
	private Mignon mob;
	
	protected long x, y;
	protected boolean isState = false;
	protected boolean isReturned = false;

	public MignonController(Mignon mob) {
		super(mob);
		this.mob = mob;
		isReturned = true;
	}
	public void lostOwner()
	{
		comeBack();
		x = mob.getX();
		y = mob.getY();
	}
	public void comeBack()
	{
		isState = false;
		isReturned = true;
	}
	public void state(long tx, long ty)
	{
		isState = true;
		isReturned = false;
		x = tx;
		y = ty;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(isReturned)
		{
			mob.spin(mob.getOwner().getX()+mob.getOwner().getWidth()/2, mob.getOwner().getY()+mob.getOwner().getHeight()/2);
		}
		if(isState)
		{
			mob.goTo(x, y);
		}
	}
	
	public boolean isReturned() {return isReturned;}
	public boolean isState() {return isState;}
}
