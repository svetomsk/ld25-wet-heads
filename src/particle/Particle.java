package particle;

import main.World;

public class Particle
{
	protected long x, y;
	protected double vx, vy;
	protected World world;
	
	protected double speed = 3;
	
	protected int lifeTime;
	protected boolean isDeleted = false;
	
	public Particle(long x, long y, World world) 
	{
		this.x = x;
		this.y = y;
		this.world = world;
		randomizeMoving();
		
		lifeTime = 50;
		
		world.particles.add(this);
	}
	protected void randomizeMoving()
	{
		double angle = 2*Math.PI*Math.random();
		vx = speed * Math.cos(angle);
		vy = speed * Math.sin(angle);
	}
	
	public void tick() 
	{
		lifeTime--;
		if(lifeTime < 0)
		{
			delete();
		}
		updateCoords();
	}
	public void interactOn(Particle particle)
	{
		
	}
	
	private void delete()
	{
		isDeleted = true;
	}
	public boolean isDeleted()
	{
		return isDeleted;
	}
	
	public void onDeath()
	{
		
	}
	
	protected void updateCoords() 
	{
		x+=vx;
		y+=vy;
	}
//	protected void updateVelocity() 
//	{
//		
//	}
	
}
