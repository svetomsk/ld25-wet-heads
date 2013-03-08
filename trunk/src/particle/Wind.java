package particle;

import main.World;
import entity.Entity;

public class Wind extends Particle
{
	public Wind(long x, long y, World world)
	{
		super(x, y, world);
		
		lifeTime = 30;
	}

	@Override
	public void interactOn(Particle p) 
	{
		super.interactOn(p);
		
		double power = 3000 /( ((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) );
		power = Math.min(power, 10);
		
		double angle = Math.PI/2+Entity.getAngle((p.x-x), (p.y-y));
		p.vx += Math.cos(angle)*power;
		p.vy += Math.sin(angle)*power;
	}
	
//	@Override
//	public void draw(Graphics2D g)
//	{
//		super.draw(g);
//		Image value = Pictures.smoke;
//		g.drawImage(value, (int) (x - Game.x - value.getWidth(null)/2), (int) (y - Game.y - value.getHeight(null)), null);
//	}

	@Override
	protected double getSpeed()
	{
		return 3;
	}
}
