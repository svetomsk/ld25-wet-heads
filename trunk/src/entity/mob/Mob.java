package entity.mob;

import items.Item;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.Game;
import main.World;
import block.Block;
import entity.Entity;
import entity.mob.controllers.Controller;
import entity.mob.controllers.Group;
import entity.mob.mignons.Mignon;

public class Mob extends Entity{
	
	public double choosenDir = 1;
	public boolean onGround;
	public int hp = 100;
	protected Controller control;
	protected Group group;
	
//	public void init(long x, long y, World world, Group g)
//	{
//		super.init(x, y, world);
//		
//		g.addMob(this);
//		this.group = g;
//	}
	@Override
	protected void finalInit(World world)
	{
		super.finalInit(world);
		hp = getMaxHP();
		Group.mobs.addMob(this);
		this.group = Group.mobs;
	}
	@Override
	public void save(DataOutputStream os) throws IOException
	{
		super.save(os);
		os.writeInt(hp);
	}
	@Override
	public void load(DataInputStream is, World world) throws IOException
	{
		super.load(is, world);
		hp = is.readInt();
	}
	
	@Override
	public void onDeath() 
	{
		control.floakFree();
		super.delete();
	}
	
	public void damage(int damage, int knockback, double dir)
	{	
		if(damage == 0) return;
		hp -= Math.max(damage - getStrength(), 0);
		if(knockback>0)
		{
			this.lvx=dir*knockback;
			this.lvy=-getJumpPower()*0.5;
		}
	}	
	public void tick()
	{
		control.tick();
    	if(hp<0.01)
		{
    		isDeleted = true;
    		return;
		}   
    	super.tick();
	}
	@Override
	protected void slowly()
	{
		lvx *= getSpeed()/(getSpeed()+1);
	}
	@Override 
	protected void updateCoord() 
	{
		onGround = false;
		super.updateCoord();
	}
	@Override
	protected boolean collideIslands(boolean verticalWalls)
	{
		tmp_damage = 0;
		boolean isCollide = super.collideIslands(verticalWalls);
		if(!verticalWalls && isCollide) onGround = true;
		damage(tmp_damage, 10, -1*Math.signum(lvx));
		return isCollide;
	}
	private static int tmp_damage;
	@Override
	protected void interactOn(Block block) {
		tmp_damage = Math.max(block.getDamage(), tmp_damage);
		super.interactOn(block);
	}
	@Override
	public void draw(Graphics2D g)
	{                  
		super.draw(g);
        drawBounds(g);
        drawHealth(g);
	}
	
	protected void drawHealth(Graphics2D g)
	{
		g.setColor(new Color((float)(1-Math.max(hp/(double)getMaxHP(), 0)), (float)Math.max(hp/(double)getMaxHP(), 0), (float)0.0));
        g.fillRect((int)(x+getWidth()/2-(Math.max(hp/(double)getMaxHP(), 0)*getWidth())) - Game.x, 
        		(int)y-18 - Game.y, (int)(getWidth()*Math.max(hp/(double)getMaxHP(), 0))*2, 6);
	}
	
	public void onRight()
	{
		choosenDir = 1;
		lvx++;
	}
	public void onLeft()
	{
		choosenDir = -1;
		lvx--;
	}
	public void onUp()
	{
		if(onGround) lvy -= getJumpPower();
	}
	public void onDown()
	{
		
	}
	public boolean tryGet(Item item)
	{
		return control.tryGet(item);
	}
	
	public void addMignon(Mignon mignon)
	{
		control.addMignon(mignon);
	}

	public void addLVX(double value)
	{
		lvx += value;		
	}
	public void addLVY(double value)
	{
		lvy += value;
	}
	
//	public static double speed = 7;
//	public static double JUMP_POWER = 13;
//	public static int max_hp = 100;
//	public static int damage = 10;
//	public static int knockback = 7;	
//	protected static int width = 16;
//	protected static int height = 16;
//	public static double strength = 0;
	
	public double getSpeed()
	{
		return 7;
	}
	public double getJumpPower()
	{
		return 13;
	}
	public int getMaxHP()
	{
		return 100;
	}
	public int getDamage()
	{
		return 10;
	}
	public int getKnokback()
	{
		return 7;
	}
	public double getStrength()
	{
		return 0;
	}
	@Override
	public int getWidth()
	{
		return width;
	}
	@Override
	public int getHeight()
	{
		return height;
	}
	public Group getGroup()
	{
		return group;
	}
}
