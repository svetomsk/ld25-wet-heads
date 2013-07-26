package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

import main.saving.IDManager;
import block.Block;
import block.Rock;
import block.decor.Background;
import block.decor.Ghost_Rock;

public class Island {

	public byte[][] blocks;
	private double vx, vy;
	private long x, y;
	private World world;
	
	//ATTENTION! -12<=v<=12 only
	
	public Island(long x, long y, double vx, double vy, World world, byte[][] mas)
	{
//		blocks = mas;
		blocks = Block.parse(mas);
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;	
		this.world = world;
		world.islands.add(this);
	}
	public Island(World world)
	{
		int size = 16;
		blocks = new byte[size][size];
		byte id = IDManager.getBlockID(Rock.class);
		for(int q=0;q<blocks.length;q++)
		{
			for(int w=0;w<blocks[0].length;w++)
			{
				blocks[q][w] = id;
			}
		}
		
		x = -world.BLOCK_SIZE*size/2;
		y = x;
		
		this.world = world;
		world.islands.add(this);
	}
	public void tick()
	{
		addVelocity();
	}
	public void tickBlock(int x, int y)
	{
		if(blocks[x][y] == IDManager.getBlockID(Ghost_Rock.class))
		{
			blocks[x][y] = IDManager.getBlockID(Background.class);
		}
		
//		Block block = blocks[x][y];
//		if(block == null) return;
//		blocks[x][y].tick();
//		if(blocks[x][y].isDeleted())
//		{
//			
//			----------------------------------
//			if(blocks[x][y] instanceof Ghost_Rock)
//			{
//				blocks[x][y] = new Background();
//				return;
//			}
//			-----------------------------------
//			blocks[x][y] = null;
//		}
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
		
		Block block;
		
    	for(int q=x1;q<x2;q++)
    	{
    		for(int w=y1;w<y2;w++)
    		{
    			block = null;
    			if(blocks[q][w] == 0) continue;
    			try
				{
					block = (Block) IDManager.getBlockClass(blocks[q][w]).newInstance();
				}
    			catch (InstantiationException e)
				{
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}

    			if(block == null) continue;
    			g.setColor(block.getColor());
    			
    			g.fillRect((int)x+q*world.BLOCK_SIZE - Game.x, (int)y+w*world.BLOCK_SIZE - Game.y, world.BLOCK_SIZE, world.BLOCK_SIZE);
    		}
    	}
    	drawBounds(g);
	}
	private void drawBounds(Graphics2D g)
	{
		g.setColor(Color.black);
        int dx = (int) (x-Game.x);
        int dy = (int) (y-Game.y);
        
        int w = blocks.length*world.BLOCK_SIZE;
        int h = blocks[0].length*world.BLOCK_SIZE;
        
        g.drawLine(dx, dy, dx+w, dy);
        g.drawLine(dx, dy, dx, dy+h);
        g.drawLine(dx+w, dy, dx+w, dy+h);
        g.drawLine(dx, dy+h, dx+w, dy+h);
	}
	private static final int expansionPower = 40;
	public void expand(int x, int y) 
	{
		byte[][] buffer;
		int width = blocks.length, height = blocks[0].length;
		boolean up, left, down, right;
		up = down = left = right = false;
		
		if(x < 0)
		{
			left = true;
			width += expansionPower;
		}
		if(y < 0)
		{
			up = true;
			height += expansionPower;
		}
		if(x >= blocks.length)
		{
			right = true;
			width += expansionPower;
		}
		if(y >= blocks[0].length)
		{
			down = true;
			height += expansionPower;
		}
		buffer = new byte[width][height];
		int newy0 = up?expansionPower:0, newx0 = left?expansionPower:0;
		for(int q=newx0;q<blocks.length+newx0;q++)
		{
			for(int w=newy0;w<blocks[0].length+newy0;w++)
			{
				buffer[q][w] = blocks[q-newx0][w-newy0];
			}
		}
		blocks = buffer;
		if(left) this.x -= expansionPower*world.BLOCK_SIZE;
		if(up) this.y -= expansionPower*world.BLOCK_SIZE;
	}
	public static void collide(Island i1, Island i2)
	{
		
	}
	public long getX() {return x;}
	public long getY() {return y;}
	public double getVX() {return vx;}
	public double getVY() {return vy;}
	
}
