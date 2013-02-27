package particle;

import java.awt.Graphics2D;
import java.util.Random;

import main.World;

public class Particle
{
	protected double x, y;
	protected double vx, vy;
	protected World world;

	protected int lifeTime;
	protected boolean isDeleted;

	public Particle(long x, long y, World world)
	{
		this.x = x;
		this.y = y;
		this.world = world;
		randomizeMoving();

		lifeTime = 50;

		world.particles.add(this);
	}

	protected static Random r = new Random();
	
	protected void randomizeMoving()
	{
		double angle = 2 * Math.PI * r.nextDouble();
		vx = getSpeed() * Math.cos(angle);
		vy = getSpeed() * Math.sin(angle);
	}

	public void tick()
	{
		lifeTime--;
		if (lifeTime < 0)
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
		x += vx;
		y += vy;
	}

//	protected void updateVelocity() 
//	{
//		
//	}

	public void draw(Graphics2D g)
	{

	}

	protected double getSpeed()
	{
		return 0;
	}

}
