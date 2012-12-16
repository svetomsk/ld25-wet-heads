package entity.mob.controllers;

import entity.Entity;
import entity.mob.Mignon;
import entity.mob.Mob;

public class MignonController extends Controller{
	
	private Mignon mob;
	
	protected long x, y;
	protected boolean isAttack = false;
	protected boolean isState = false;
	protected boolean isReturned = false;

	public MignonController(Mignon mob) {
		super(mob);
		this.mob = mob;
		isReturned = true;
	}

	public void lostOwner()
	{
		x = mob.getX();
		y = mob.getY();
	}
	
	public void attack(long tx, long ty)
	{
		isAttack = true;
		isState = false;
		isReturned = false;
		x = tx;
		y = ty;
	}
	public void comeBack()
	{
		isAttack = false;
		isState = false;
		isReturned = true;
	}
	public void state(long tx, long ty)
	{
		isAttack = false;
		isState = true;
		isReturned = false;
		x = tx;
		y = ty;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(isAttack)
		{
			mob.followPoint(x, y);
			long r = (x-mob.getX())*(x-mob.getX()) + (y-mob.getY())*(y-mob.getY());
			if(r<10000)
			{
				comeBack();
			}
			return;
		}
		if(isReturned)
		{
			mob.spin(mob.getOwner().getX()+mob.getOwner().getWidth()/2, mob.getOwner().getY()+mob.getOwner().getHeight()/2);
		}
		if(isState)
		{
			mob.state(x, y);
		}
	}
}
