package entity.mob.mignons;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import entity.mob.Mob;
import entity.mob.controllers.MignonController;
import block.Block;
import main.Game;
import main.World;

public class Mignon extends Mob{

	public static final int RAD_RUN = 10000;
	public static final int RAD_SPIN = 20000;
	
	private static final double V_SLOWLY = 0.99;
	private Mob owner;

	protected MignonController control;
	
	public Mignon(long x, long y, World world) 
	{
		super(x, y, world);
		control = new MignonController(this);
		super.control = control;
	}
	public Mignon(long x, long y, World world, Mob owner) 
	{
		this(x, y, world);
		setOwner(owner);
	}
	protected void setOwner(Mob owner)
	{
		this.owner = owner;
		owner.addMignon(this);
	}
	public void loseOwner()
	{
		delete();
	}
	@Override
	public void draw(Graphics2D g) 
	{
		int drawx = (int) (x-Game.x+getWidth()/2);
    	int drawy = (int) (y-Game.y+getHeight()/2);
    	
        g.drawImage(img[currentFrame], drawx-img[currentFrame].getWidth(null)/2, drawy-img[currentFrame].getHeight(null)/2, null);
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
	protected boolean interactOnMignon(Mignon mignon)
	{
		return false;
	}
	
//	------------------------------------------- MOTION ------------------------------------------- 

	public boolean state(long tx, long ty) 
	{
		if(control.isState()) return true;
		control.state(tx, ty);
		return false;
	}
	public void comeBack() 
	{
		control.comeBack();
	}
	public void goTo(long tx, long ty) 
	{
		long r = (tx-x)*(tx-x) + (ty-y)*(ty-y); 
		if(r>250)
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
		lvy += -0.5*Math.cos(angle);
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
