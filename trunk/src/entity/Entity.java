package entity;

import items.Item;
import items.seeds.DamageMignonSeed;
import items.seeds.JumpMignonSeed;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.Game;
import main.Island;
import main.Pictures;
import main.World;
import main.saving.IDManager;
import block.Block;
import entity.mob.Angel;
import entity.mob.ArchAngel;
import entity.mob.Butterfly;
import entity.mob.Character;
import entity.mob.Creator;
import entity.mob.Mob;
import entity.mob.mignons.Mignon;

public class Entity {

	protected long x, y;
	
	public double lvx, lvy;
	protected double gvx, gvy;
	
	protected static int height = 50;
	protected static int width = 50;
	
	protected boolean isDeleted = false;
	
	protected World world;
	
	public static int ids = 0;
	protected int id = -1;
		
	// ------------------------------------------- MAIN -------------------------------------------
	

	public Entity init(long x, long y, double lvx, double lvy, double gvx, double gvy, World world)
	{
		this.lvx = lvx;
		this.lvy = lvy;
		this.gvx = gvx;
		this.gvy = gvy;
		return init(x, y, world);
	}
	public Entity init(long x, long y, World world)
	{
		this.x = x;
		this.y = y;
		this.world = world;
		finalInit(world);
		afterBirth();
		return this;
	}
	public void save(DataOutputStream os) throws IOException 
	{
		os.writeInt(id);
		os.writeLong(x);
		os.writeLong(y);
		os.writeDouble(lvx);
		os.writeDouble(lvy);
		os.writeDouble(gvx);
		os.writeDouble(gvy);
	}
	public void load(DataInputStream is, World world) throws IOException
	{
		this.world = world;
		
		id = is.readInt();
		x = is.readLong();
		y = is.readLong();
		lvx = is.readDouble();
		lvy = is.readDouble();
		gvx = is.readDouble();
		gvy = is.readDouble();
		finalInit(world);
	}
	protected void finalInit(World world)
	{
		world.entities.add(this);
		initPictures();
	}
	protected void afterBirth()
	{
		if(id == -1)
		{
			id = ids;
			ids++;
		}
	}
	
	protected void initPictures()
	{
		if(img == null)
		{
			img = Pictures.damageMignon;
		}
		currentFrame = (int) Math.random()*100;
		subFrame = (int) Math.random()*100;
	}
	public void tick()
	{
		updateVelocity();
		updateCoord();
		
		subFrame++;
		if(subFrame>=3)
		{
			currentFrame++;
			if(currentFrame>=img.length) currentFrame = 0;
			subFrame = 0;
		}
	}
	protected void updateVelocity()
	{    	
        lvy += world.GRAVITY;
        if(Math.abs(lvx) < 1) lvx = 0;
        slowly();
	}
	protected void slowly()
	{
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
	

//	------------------------------------------- DRAW -------------------------------------------
	
	protected Image[] img;
	protected int currentFrame;
	protected int subFrame;

	public void draw(Graphics2D g) 
	{
    	int drawx = (int) (x-Game.x+getWidth()/2);
    	int drawy = (int) (y-Game.y+getHeight()/2);
    	
        g.drawImage(img[currentFrame], drawx-img[currentFrame].getWidth(null)/2, drawy-img[currentFrame].getHeight(null)/2, null);
        
//        drawBounds(g);
	}
	public void drawBounds(Graphics2D g)
	{
        int dx = (int) (x-Game.x);
        int dy = (int) (y-Game.y);
        
        g.drawLine(dx, dy, dx+getWidth(), dy);
        g.drawLine(dx, dy, dx, dy+getHeight());
        g.drawLine(dx+getWidth(), dy, dx+getWidth(), dy+getHeight());
        g.drawLine(dx, dy+getHeight(), dx+getWidth(), dy+getHeight());
	}
	
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
//				Block block = null;
				byte id = 0;
				int qb = -1;
				int wb = -1;
				if(verticalWalls)
				{
					if(gvx + lvx - island.getVX() >= 0)
					{
						qb = x2; wb = q; 
					}
					else
					{
						qb = x1; wb = q;
					}
				}
				else
				{
					if(gvy + lvy - island.getVY() >= 0)
					{
						qb = q; wb = y2;
					}
					else
					{
						qb = q; wb = y1;
					}
				}			
				
				try
				{
					id = island.blocks[qb][wb];
				}
				catch(ArrayIndexOutOfBoundsException ex){}
				
				if(id == 0) continue;
				interactOn(id);
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
		
		if(isCollide && lvy<0 && lvy>=-4*world.GRAVITY) lvy = 0; // Затухание лишних колебаний
		
		return isCollide;
	}
	protected void interactOn(byte id)
	{
		Block block = null;
		try
		{
			block = (Block) IDManager.getBlockClass(id).newInstance();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!block.getCollidable()) return;
			
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
		lvy-=2;
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
	public void onDeath() {}
	
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
					new Character().init(x, y, world);
					continue;
				}
				else if(b == 126)
				{
					new Butterfly().init(x, y, world);
					continue;
				}
				else if(b == 125)
				{
					chest(arr, q, w, (Chest) new Chest().init(x-8, y-world.BLOCK_SIZE-16, world));
				}
				else if(b == 124)
				{
					new ArchAngel().init(x, y, world);
				}
				else if(b == 123)
				{
					Angel a = new Angel();
					a.init(x, y, world);
				}
				else if(b == 122)
				{
					new End().init(x, y, world);
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
				DamageMignonSeed seed = new DamageMignonSeed();
				seed.init(chest);
				chest.addItem(seed);
				continue;
			}
			if(b == 65)
			{
				JumpMignonSeed seed = new JumpMignonSeed();
				seed.init(chest);
				chest.addItem(seed);
				continue;
			}
			break;
		}
	}
	
	// Надо куда-нибудь перетащить это.
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
	public long getX() {return x + getWidth()/2;}
	public long getY() {return y + getHeight()/2;}
	
	public double getLVX() {return lvx;}
	public double getLVY() {return lvy;}
	public double getGVX() {return gvx;}
	public double getGVY() {return gvy;}
	
	public int getHeight() {return height;}
	public int getWidth() {return width;}
	
	public World getWorld() {return world;}
	public int getId(){return id;}
	public boolean isDeleted() {return isDeleted;}
}
