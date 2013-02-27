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
		lifeTime = 25;
	}

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
