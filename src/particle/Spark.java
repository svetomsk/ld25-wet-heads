package particle;

import java.awt.Graphics2D;
import java.awt.Image;

import main.Game;
import main.Pictures;
import main.World;

public class Spark extends Particle
{
	public Spark(long x, long y, World world)
	{
		super(x, y, world);
		lifeTime = 50;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		vy-=0.1;
	}

//	@Override
//	public void interactOn(Particle p) 
//	{
//		super.interactOn(p);
//		
//		double power = ( 10 - (p.x-x)*(p.x-x)+(p.y-y)*(p.y-y) );
//		if(power<=0) return;
//		power = 100/power;
//		
//		double angle = Math.atan((p.x-x)/(p.y-y));
//		vx += Math.cos(angle)*power;
//		vy += Math.sin(angle)*power;
//	}
	
	@Override
	public void draw(Graphics2D g)
	{
		super.draw(g);
		Image value = Pictures.spark;
		g.drawImage(value, (int) (x - Game.x - value.getWidth(null)/2), (int) (y - Game.y - value.getHeight(null)), null);
	}

	@Override
	protected double getSpeed()
	{
		return 3;
	}
}
