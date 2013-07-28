package items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.World;
import entity.Chest;
import entity.Entity;
import entity.mob.Mob;

public class Item extends Entity{

	protected static final int PICKUP_TIME = 60;
	protected int cooldown;
	protected int pickupTime;
	protected int timer;
	
	protected boolean isPickable = true;
	
	private int width = 16;
	protected Mob owner;
	
	public Entity init(long x, long y, World world)
	{
		return init(x, y, null, world);
	}
	public Entity init(Mob owner)
	{
		return init(0, 0, owner, owner.getWorld());
	}
	public Entity init(long x, long y, Mob owner, World world)
	{
		this.owner = owner;
		return super.init(x, y, world);
	}
	@Override
	public void save(DataOutputStream os) throws IOException
	{
		super.save(os);
		if(owner == null)
		{
			os.writeInt(-1);
		}
		else
		{
			os.writeInt(owner.getId());
		}
	}
	@Override
	public void load(DataInputStream is, World world) throws IOException
	{
		super.load(is, world);
		int id = is.readInt();
		if(id == -1)
		{
			return;
		}
		else
		{
			Mob owner = (Mob) world.getEntityByID(id);
			if(owner == null)
			{
				return;
			}
			else
			{
				this.owner = owner;
				if(owner instanceof Chest)
				{
					Chest ch = (Chest) owner;
					ch.addItem(this);
				}
			}
		}
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
	protected boolean interactOnMob(Mob mob)
	{
		if( !super.interactOnMob(mob) ) return false;
			
		if(isPickable)
		if(pickupTime<0)
		if(mob.tryGet(this))
		{
			owner = mob;
			x=0;
			y=0;
			lvx = 0;
			lvy = 0;
		}
		return true;
	}
	@Override
	public void draw(Graphics2D g)
	{
		if(owner != null) return;
		super.draw(g);
		g.setColor(Color.blue);
	}
	public void use(long x, long y)
	{		
		
	}
	public void throwItem()
	{
		x = owner.getCX()-getWidth()/2;
		y = owner.getCY()-getWidth()/2;
		owner = null;
		pickupTime = PICKUP_TIME;
	}
	public void splash()
	{
		throwItem();
		
		lvx = 20* (Math.random()-0.5);
		lvy = -40* Math.random();
	}
	public void setOwner(Mob mob)
	{
		owner = mob;
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
