package main;

import java.awt.Graphics2D;

import block.Block;
import block.decor.Background;
import block.decor.Ghost_Rock;

public class Island {

	public Block[][] blocks;
	private double vx, vy;
	private long x, y;
	private World world;
	
	//ATTENTION! -12<=v<=12 only
	
	public Island(int x, int y, int vx, int vy, World world, byte[][] mas)
	{
		blocks = Block.parse(mas);
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;	
		this.world = world;
		world.islands.add(this);
	}
	public void tick()
	{
		addVelocity();
	}
	public void tickBlock(int x, int y)
	{
		Block block = blocks[x][y];
		if(block == null) return;
		blocks[x][y].tick();
		if(blocks[x][y].isDeleted())
		{
			
//			----------------------------------
			if(blocks[x][y] instanceof Ghost_Rock)
			{
				blocks[x][y] = new Background();
				return;
			}
//			-----------------------------------
			blocks[x][y] = null;
		}
	}
	public void addVelocity()
	{		
		int block_size = world.BLOCK_SIZE;
		int steps=1;
		steps = (int) Math.ceil(Math.max(steps, Math.abs(vx/block_size)));
		steps = (int) Math.ceil(Math.max(steps, Math.abs(vy/block_size)));
		for(int w=0;w<steps;w++)
		{
			x += vx/steps;
	        y += vy/steps;		
//	        for(Island i2:World.islands)
//	        {
//	        	
//	        }
		}
	}
	public void draw(Graphics2D g)
	{
		int BLOCK_SIZE = world.BLOCK_SIZE;
		int x1 = Math.max(0, (int) ((Game.x-x)/BLOCK_SIZE));
		int x2 = Math.min(blocks.length, (int) ((Game.x+Game.WIDTH-x)/BLOCK_SIZE)+1);
		int y1 = Math.max(0, (int) ((Game.y-y) / BLOCK_SIZE));
		int y2 = Math.min(blocks[0].length, (int) ((Game.y+Game.HEIGHT-y) / BLOCK_SIZE)+1);		
		
    	for(int q=x1;q<x2;q++)
    	{
    		for(int w=y1;w<y2;w++)
    		{
    			Block block;
    			block = blocks[q][w];

    			if(block == null) continue;
    			g.setColor(block.getColor());
    			
    			g.fillRect((int)x+q*world.BLOCK_SIZE - Game.x, (int)y+w*world.BLOCK_SIZE - Game.y, world.BLOCK_SIZE, world.BLOCK_SIZE);
    		}
    	}
	}
	public static void collide(Island i1, Island i2)
	{
		
	}
	public long getX() {return x;}
	public long getY() {return y;}
	public double getVX() {return vx;}
	public double getVY() {return vy;}
	
}
