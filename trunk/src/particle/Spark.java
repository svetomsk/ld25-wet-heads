package particle;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Game;
import main.Pictures;
import main.World;
import entity.Entity;

public class Spark extends Particle{

	private static final int MAX_V = 5;
		
	public Spark(long x, long y, World world) {
		super(x, y, world);
		lvx = (Math.random()-0.5)*MAX_V;
		lvy = (Math.random()-0.5)*MAX_V;
		
		lifeTime = 25;
	}	
	public Spark(long x, long y, World world, double vx, double vy) {
		super(x, y, world);
		lvx = vx+(Math.random()-0.5)*2;
		lvy = vy+(Math.random()-0.5)*2;
	}	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawImage(Pictures.spark, (int)(x-Game.x), (int)(y-Game.y), null);
		
	}

}
