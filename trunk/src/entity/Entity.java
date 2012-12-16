package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.Character;
import entity.mob.Mignon;
import entity.mob.Mob;
import entity.mob.Zombie;

import block.Block;
import items.Item;
import items.seeds.DamageMignonSeed;
import main.Game;
import main.Island;
import main.World;

public class Entity {

	protected long x, y;
	
	protected double lvx, lvy;
	protected double gvx, gvy;
	
	protected static int height = 50;
	protected static int width = 50;
	
	protected boolean isDeleted = false;
	
	protected World world;
		
	// ------------------------------------------- MAIN -------------------------------------------
	
	public Entity(long x, long y, World world)
	{
		this.x = x;//-getWidth()/2;
		this.y = y;//-getHeight()/2;
		this.world = world;
		world.entities.add(this);
	}
	public void tick()
	{
		updateVelocity();
		updateCoord();
	}
	protected void updateVelocity()
	{    	
        lvy += world.GRAVITY;
        if(Math.abs(lvx) < 1) lvx = 0;
        lvx *= 0.9;
	}
	protected void updateCoord()
	{
		int block_size = world.BLOCK_SIZE;
		int steps=1;
		x+=gvx;
		y+=gvy;
		steps = (int) Math.ceil(Math.max(steps, Math.abs(lvx/block_size)));
		steps = (int) Math.ceil(Math.max(steps, Math.abs(lvy/block_size)));
		boolean isCollide = false;
		for(int w=0;w<steps;w++)
		{
			x += lvx/steps;
			isCollide |= collideIslands(true);
	        y += lvy/steps;	
			isCollide |= collideIslands(false);
			if(isCollide) break;
		}
	}
	
	public void delete()
	{
		isDeleted = true;
	}

	public void draw(Graphics2D g){}	
	
	// ------------------------------------------- ISLANDS -------------------------------------------
	
	// tmp
	protected static double elasticity;
	protected static boolean collide;
	// /tmp
	
	protected boolean collideIslands(boolean verticalWalls)
	{		
		boolean isCollide = false;
		for(Island island:world.islands)
		{
			int BLOCK_SIZE = world.BLOCK_SIZE;
			int x1 = (int) ((x-island.getX()) / BLOCK_SIZE);
			int x2 = (int) ((x+getWidth()-island.getX()-1) / BLOCK_SIZE);
			int y1 = (int) ((y-island.getY()+1) / BLOCK_SIZE);
			int y2 = (int) ((y+getHeight()-island.getY()-1) / BLOCK_SIZE);
			
			if(x2<0 || y1>=island.blocks[0].length || x1>=island.blocks.length || y2<0) continue;
			
			elasticity = -300;
			collide = false;
			
			int q = verticalWalls ? y1 : x1;
			int q1 = verticalWalls ? y2 : x2;			
			
			for(; q <= q1 ;q++)
			{								
				Block block = null;
				int qb = -1;
				int wb = -1;
				if(verticalWalls)
				{
					if(gvx + lvx - island.getVX() >= 0)
					{
						try{ block = island.blocks[x2][q]; qb = x2; wb = q;} catch(Exception ex) {continue;} 
					}
					else
					{
						try{ block = island.blocks[x1][q]; qb = x1; wb = q;} catch(Exception ex) {continue;}
					}
				}
				else
				{
					if(gvy + lvy - island.getVY() >= 0)
					{
						try{ block = island.blocks[q][y2]; qb = q; wb = y2;} catch(Exception ex) {continue;}
					}
					else
					{
						try{ block = island.blocks[q][y1]; qb = q; wb = y1;} catch(Exception ex) {continue;}
					}
				}				
				if(block == null) continue;
				interactOn(block);
				island.tickBlock(qb, wb);
			}
			
			if(collide)
			{
				if(verticalWalls)
				{
					if(gvx + lvx - island.getVX() >= 0)
					{
						x = island.getX()+x2*BLOCK_SIZE-getWidth();					
					}
					else
					{
						x = island.getX()+(x1+1)*BLOCK_SIZE;
					}					
				}
				else 
				{
					if(gvy + lvy -  island.getVY() >= 0)
					{
						y = island.getY()+y2*BLOCK_SIZE-getHeight();
						gvx += (island.getVX()-gvx)*0.1;
						if(Math.abs(gvx-island.getVX())<0.7) gvx = island.getVX();
						gvy = island.getVY();					
					}
					else
					{
						y = island.getY()+(y1+1)*BLOCK_SIZE;
					}
				}
			}			
			if(elasticity!=-300)
			{
				if(verticalWalls)
				{
					lvx *= elasticity;
				}
				else
				{
					lvy *= elasticity;
				}
			}
			isCollide |= collide;
		}
		
		if(isCollide && lvy<0 && lvy>=-4*world.GRAVITY) lvy = 0; // ��������� ������ ���������
		
		return isCollide;
	}
	protected void interactOn(Block block)
	{
		elasticity = Math.max(elasticity, block.getElasticity());
		collide |= block.getCollidable();
	}	
	
	// ------------------------------------------- ENTITIES -------------------------------------------
	
	public void interactOn(Entity e)
	{
		secondaryInteract(e);
	}	
	protected boolean secondaryInteract(Entity e)
	{
		if(e instanceof Mob) return interactOnMob((Mob) e);
		if(e instanceof Item) return interactOnItem((Item) e);
		return false;
	}	
	protected boolean interactOnMob(Mob mob)
	{
		if(mob instanceof Character) return interactOnCharacter((Character) mob);
		if(mob instanceof Mignon) return interactOnMignon((Mignon) mob);
		if(mob instanceof Chest) return interactOnChest((Chest) mob);
		
		return true;
	}
	
	protected boolean interactOnCharacter(Character character){return true;}
	protected boolean interactOnMignon(Mignon mignon){return true;}
	protected boolean interactOnChest(Chest chest){return true;}
	
	protected boolean interactOnItem(Item item){return true;}
	
	public void throwUp()
	{
		lvy--;
	}
	
	public boolean isCollide(Entity e)
	{
		boolean isnt = false;
		isnt |= getX() > e.getX() + e.getWidth();
		isnt |= getX() + getWidth() < e.getX();
		isnt |= getY() > e.getY() + e.getHeight();
		isnt |= getY() + getHeight() < e.getY();
		return !isnt;
	}
	public boolean isCollide(double p1x, double p1y)
	{
		boolean isnt = false;
		isnt |= p1x < getX();
		isnt |= p1x > getX() + getWidth();
		isnt |= p1y > getY() + getHeight();
		isnt |= p1y < getY();
		return !isnt;
	}
	
	// ------------------------------------------- GETTERS -------------------------------------------

	public static void parse(byte[][] arr, World world)
	{
		for(int q=0;q<arr.length;q++)
		{
			for(int w=0;w<arr[0].length;w++)
			{
				byte b = arr[q][w];
				int x = q*world.BLOCK_SIZE;
				int y = w*world.BLOCK_SIZE;
				if(b == 127)
				{
					new Character(x, y, world);
					continue;
				}
				else if(b == 126)
				{
//					new Zombie(x, y, world);
					continue;
				}
				else if(b == 125)
				{
					chest(arr, q, w, new Chest(x, y-17, world));
				}
				
			}
		}
	}
	private static void chest(byte[][] arr, int q, int w, Chest chest)
	{
		while(true)
		{
			w++;
			byte b = arr[q][w];
			if(b == 64)
			{
				chest.addItem(new DamageMignonSeed(chest));
				continue;
			}
//			if(b == )
			
			break;
		}
	}
	
	// ���� ����-������ ���������� ���.
	public static double getAngle(double dx, double dy)
	{
		double l = Math.sqrt(dx*dx+dy*dy);
		if(l == 0) return 0.0;
		double asin = Math.asin(Math.abs(dy/l));
		if(dx>=0 && dy>=0) return asin-Math.PI/2;
		if(dx>=0 && dy<=0) return -asin-Math.PI/2;
		if(dx<=0 && dy>=0) return -asin+Math.PI/2;
		if(dx<=0 && dy<=0) return asin+Math.PI/2;
		return 0.0;
	}
	public static long getDistanse(Entity e1, Entity e2)
	{
		return (e1.getX()+e1.getWidth()/2-e2.getX()-e2.getWidth()/2)
				*(e1.getX()+e1.getWidth()/2-e2.getX()-e2.getWidth()/2)
				+(e1.getY()+e1.getHeight()/2-e2.getY()-e2.getHeight()/2)
				*(e1.getY()+e1.getHeight()/2-e2.getY()-e2.getHeight()/2);
	}
	public long getX() {return x;}
	public long getY() {return y;}
	public double getLVX() {return lvx;}
	public int getHeight() {return height;}
	public int getWidth() {return width;}
	public World getWorld() {return world;}
	public boolean isDeleted() {return isDeleted;}
}
