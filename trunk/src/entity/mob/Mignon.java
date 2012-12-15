package entity.mob;

import entity.Entity;
import entity.mob.controllers.MignonController;
import block.Block;
import main.World;

public class Mignon extends Mob{

	public static final int RAD_RUN = 10000;
	public static final int RAD_SPIN = 20000;
	
	private static final double V_SLOWLY = 0.99;
	private Mob owner;
	
	public Mignon(long x, long y, World world) {
		super(x, y, world);
		control = new MignonController(this);
	}
	public Mignon(long x, long y, World world, Mob owner) {
		super(x, y, world);
		control = new MignonController(this);
		setOwner(owner);
	}
	protected void setOwner(Mob owner)
	{
		this.owner = owner;
		owner.addMignon(this);
	}
	
	@Override
	protected void updateVelocity() 
	{
		lvx *= V_SLOWLY;
		lvy *= V_SLOWLY;
	}

//	------------------------------------------- ENTITIES ------------------------------------------- 
	
	@Override
	protected void interactOn(Block block) {}
	
	@Override
	protected boolean interactOnMignon(Mignon mignon) {
		return false;
	}
	
//	------------------------------------------- MOTION ------------------------------------------- 

	public void attack(long tx, long ty) 
	{
		control.attack(tx, ty);
	}
	
	
	public void state(long tx, long ty) 
	{
		long r = (tx-x)*(tx-x) + (ty-y)*(ty-y); 
		if(r>2500)
		{
			followPoint(tx, ty);
		}
		else
		{
			slowDown();
		}
	}
	protected void slowDown()
	{
		lvx *= 0.9;
		lvy *= 0.9;
	}
	public void followPoint(long tx, long ty)
	{
		// v = 1
		double angle = Entity.getAngle(tx-x, ty-y)+Math.PI/2;
		lvx += Math.cos(angle);
		lvy += Math.sin(angle);
	}
	public void runPoint(long tx, long ty)
	{
		// v = 1
		double angle = Entity.getAngle(tx-x, ty-y)+Math.PI/2;
		lvx -= Math.cos(angle);
		lvy -= Math.sin(angle);
	}
	
	private void spinRight(long tx, long ty)
	{
		// v = 0.5
		double angle = Entity.getAngle(tx-x, ty-y)+Math.PI/2;
		lvx += 0.5*Math.sin(angle);
		lvy += 0.5*Math.sin(angle);
	}
	
	private boolean spinRight = true;
	
	public void spin(long tx, long ty)
	{
		double r = (tx-x)*(tx-x)+(ty-y)*(ty-y);
		//  0 - 200 - 250
		if(r>RAD_SPIN) followPoint(tx, ty);
		else if(r>RAD_RUN)
		{
			if(spinRight)
			{
				spinRight(tx, ty);
			}
		}
		else runPoint(tx, ty);
	}
	
//	------------------------------------------- GETTERS ------------------------------------------- 
	
	public Mob getOwner()
	{
		return owner;
	}
}
