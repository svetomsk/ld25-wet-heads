package entity.mob.controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.World;

import entity.mob.mignons.Mignon;

public class MignonController extends Controller{
	
	private Mignon mob;
	
	protected long x, y;
	protected boolean isState = false;
	protected boolean isReturned = false;
	protected boolean isLost = false;

	@Override
	public void save(DataOutputStream os) throws IOException
	{
		super.save(os);
		os.writeLong(x);
		os.writeLong(y);
		os.writeBoolean(isState);
		os.writeBoolean(isReturned);
		os.writeBoolean(isLost);
	}
	@Override
	public void load(DataInputStream is, World world) throws IOException
	{
		super.load(is, world);
		x = is.readLong();
		y = is.readLong();
		isState = is.readBoolean();
		isReturned = is.readBoolean();
		isLost = is.readBoolean();
	}
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
			x = mob.getOwner().getX()+mob.getOwner().getWidth()/2;
			y = mob.getOwner().getY()+mob.getOwner().getHeight()/2;
			mob.spin(x, y);
		}
		if(isState)
		{
			mob.goTo(x, y);
		}
		if(isLost)
		{
			if(! mob.getWorld().collideIslands(mob.getX()+mob.getWidth()/2, mob.getY()+mob.getHeight()/2))
			{
//				System.out.print(b);
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
