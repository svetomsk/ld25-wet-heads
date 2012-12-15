package items;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import entity.mob.Mob;

import block.Block;
import main.Game;
import main.Island;
import main.World;

public class Item extends Entity{

	protected static final int PICKUP_TIME = 80;
	protected int cooldown;
	protected int pickupTime;
	protected int timer;
	
	private int width = 8;
	protected Mob owner;
	
	public Item(long x, long y, World world)
	{
		this(x, y, null, world);
	}
	public Item(Mob owner)
	{
		this(0, 0, owner, owner.getWorld());
	}
	public Item(long x, long y, Mob owner, World world)
	{
		super(x, y, world);
		this.world = world;
		this.x = x;
		this.y = y;
		this.owner = owner;
//		World.items.add(this);
//		world.entities.add(this);
	}

	public void tick()
	{
		timer++;			
		if(owner != null)
		{
			return;
		}
		pickupTime--;
        super.tick();
	}	
	@Override
	protected void interactOnMob(Mob mob)
	{
		if(pickupTime<0)
		if(mob.tryGet(this))
		{
			owner = mob;
			x=0;
			y=0;
			lvx = 0;
			lvy = 0;
		}
	}
	@Override
	public void draw(Graphics2D g)
	{                  
        g.setColor(Color.cyan);        
		g.fillRect((int)x - Game.x, (int)y - Game.y, width, width);
	}
	public void use(long x, long y)
	{		
		
	}
	public void throwItem()
	{
		x = owner.getX()+owner.getWidth()/2-getWidth()/2;
		y = owner.getY()+owner.getHeight()/2-getWidth()/2;
		owner = null;
		pickupTime = PICKUP_TIME;
	}
	public int getCooldown()
	{
		return cooldown;
	}

	@Override
	public int getWidth()
	{
		return width;
	}
	@Override
	public int getHeight()
	{
		return width;
	}
}
