package entity.mob.controllers;

import entity.mob.mignons.Mignon;

public class MignonController extends Controller{
	
	private Mignon mob;
	
	protected long x, y;
	protected boolean isState = false;
	protected boolean isReturned = false;
	protected boolean isLost = false;
	

	public MignonController(Mignon mignon) {
		super(mignon);
		this.mob = mignon;
		isReturned = true;
	}
	public void loseOwner()
	{
		isLost = true;
		isState = false;
		isReturned = false;
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
		if(isLost)
		{
			if(! mob.getWorld().collideIslands(mob.getX()+mob.getWidth()/2, mob.getY()+mob.getHeight()/2))
			{
//				System.out.print(b)
				mob.loseOwner();
			}
			else
			{
				mob.spin(x, y);
			}
		}
	}
	
	public boolean isReturned() {return isReturned;}
	public boolean isState() {return isState;}
}
