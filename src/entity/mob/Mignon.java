package entity.mob;

import entity.Entity;
import entity.mob.controllers.MignonController;
import block.Block;
import main.World;

public class Mignon extends Mob{

	private static final double V_SLOWLY = 0.99;
	private Mob owner;
	
	public Mignon(long x, long y, World world) {
		super(x, y, world);
		control = new MignonController(this);
	}
	public Mignon(long x, long y, World world, Mob owner) {
		super(x, y, world);
		control = new MignonController(this);
		this.owner = owner;
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
	protected boolean interactOnMob(Mob mob) {
		if( !super.interactOnMob(mob) ) return false;
		mob.throwUp();
		return true;
	}
	
	@Override
	protected boolean interactOnMignon(Mignon mignon) {
		return false;
	}
	@Override
	protected boolean interactOnCharacter(Character character) {
		return false;
	}
	
//	------------------------------------------- MOTION ------------------------------------------- 

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
		if(r>20000) followPoint(tx, ty);
		else if(r>10000)
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
