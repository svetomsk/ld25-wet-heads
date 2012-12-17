package main;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import java.awt.Graphics2D;

import entity.Entity;
import entity.mob.Character;

public class World {
	public final int BLOCK_SIZE = 16;
	public final int GRAVITY = 1;
	public static final double VX_SLOWLY = 0.9;
	
    public boolean stepState = true;
        
	public static Input input;
	
	public static int k = 0;
	public static ArrayList<Island> islands; 
	public static Character character;
	
	public ArrayList<Entity> entities;
    
	public World()
	{
		islands = new ArrayList<Island>();
		entities = new ArrayList<Entity>();
		
		createLevel();
        Game.pic = new Pictures();
	}
	public void clear()
	{
		islands = new ArrayList<Island>();
		entities = new ArrayList<Entity>();
	}
	public void createLevel()
	{
//		ArrayList<Integer> mobsCoords = ImageParser.coords();
//		character = new Character(mobsCoords.get(0), mobsCoords.get(1));
//		new Island(0, 3000, 0, 0);
		parseInput();
		
		Random r = new Random();
		for(int q=0;q<100;q++)
		{
//			new Island((int)(5000*r.nextGaussian()), (int)(-7000*r.nextGaussian()), (int)(12*r.nextDouble()), (int)(12*r.nextDouble()));
		}
	}
	private void parseInput()
	{
		byte[][] arr = ImageParser.parse("resources/firstIsland.png");
		new Island(0, 0, 0, 0, this, arr);
		Entity.parse(arr, this);
		findCharacter();
	}
	private void findCharacter()
	{
		for(Entity e:entities)
		{
			if(e instanceof Character) character = (Character) e;
		}
	}
	public void step()
	{
		for(int q=0;q<entities.size();q++)
		{
			Entity e = entities.get(q);
			if(e.isDeleted())
			{
				e.onDead();
				entities.remove(q);
				q--;
				continue;
			}
			e.tick();
		}
		for(int q=0;q<entities.size();q++)
		{
			for(int w=q+1;w<entities.size();w++)
			{
				if(!entities.get(q).isCollide(entities.get(w))) continue;
				entities.get(q).interactOn(entities.get(w));
				entities.get(w).interactOn(entities.get(q));
			}
		}
		
//        for(int q=0;q<islands.size();q++)
//        {
//        	Island island = islands.get(q);
//        	island.tick();
//        }
    }
	public boolean collideIslands(double x, double y)
	{
		boolean c = false;
		for(Island island:islands)
		{
        	try
        	{        		
    			 if( island.blocks[(int) ((x-island.getX())/BLOCK_SIZE)][(int) ((y-island.getY())/BLOCK_SIZE)].getCollidable()) c = true;
        	}
        	catch(Exception ex){}
		}      
		return c;
	}
	
//	public static boolean collidePoint(double x, double y, Mob mob)
//	{
//		double xk = mob.x;
//		double yk = mob.y;
//		double w = mob.width;
//		double h = mob.height;
//		return (x>=xk && x<=xk+w && y>=yk && y<=yk+h);
//	}
	public void draw(Graphics2D g)
	{       
//            count++;
//            for(int q=Math.max(0, Game.x/BLOCK_SIZE);q<Math.min((Game.x+Game.WIDTH*Game.SIZE)/BLOCK_SIZE+1, level.length);q++)
//            {
//                    for(int w=Math.max(0, Game.y/BLOCK_SIZE);w<Math.min((Game.y+Game.HEIGHT*Game.SIZE)/BLOCK_SIZE+1, level[0].length);w++)
//                    {
//                        if(level[q][w] == 0) continue;
//                        
//                        if(level[q][w] == 1)g.setColor(Color.BLACK);
//                        g.fillRect(q*BLOCK_SIZE - Game.x, w*BLOCK_SIZE - Game.y, BLOCK_SIZE, BLOCK_SIZE);
//                    }
//            }
            
            g.setColor(Color.BLACK);
            for(Island island:islands)
            {
            	island.draw(g);
            }           
	        for(Entity e:entities)
	        {
	            e.draw(g);
	        }            
            g.setColor(Color.BLUE);
            g.fillOval(-10-Game.x, -10-Game.y, 20, 20);            
	}
}
