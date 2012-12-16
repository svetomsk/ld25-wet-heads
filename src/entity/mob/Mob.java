package entity.mob;


import items.Item;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import entity.mob.controllers.Controller;
import entity.mob.controllers.Group;
import entity.mob.mignons.Mignon;


import block.Block;

import main.Game;
import main.Island;
import main.World;
import weapon.Weapon;

public class Mob extends Entity{
	public double speed;
	public double MAX_VY;
	
	public double choosenDir = 1;
	public boolean onGround;
	
	public double JUMP_POWER = 13;
	
	public int hp = 100;
	public int max_hp = 100;
	public double strength = 0;

	public int damage;
	public int knockback;	
	protected Controller control;
	protected Group group;
//	public Weapon[] weapons;
//	public int currentWeapon;
	
	private static int width = 10;
	private static int height = 10;
	
	public Mob(long x, long y, World world, Group group)
	{
		this(x, y, world);
		group.addMob(this);
		this.group = group;
	}
	
	public Mob(long x, long y, World world)
	{
		super(x, y, world);
		lvx = 0;
		lvy = 0;
		damage = 10;
		knockback = 7;
		speed = 7;
		MAX_VY = 15;
		
		Group.mobs.addMob(this);
		group = Group.mobs;
	}
	public void damage(int damage, int knockback, double dir)
	{	
		if(damage == 0) return;
		hp -= Math.max(damage - strength, 0);
		if(knockback>0)
		{
			this.lvx=dir*knockback;
			this.lvy=-JUMP_POWER*0.5;
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
		super.interactOn(block);
		tmp_damage = Math.max(block.getDamage(), tmp_damage);
	}
	@Override
	public void draw(Graphics2D g)
	{                  
        g.setColor(Color.RED);        
        drawBounds(g);
        drawHealth(g);
	}
	
	protected void drawHealth(Graphics2D g)
	{
		g.setColor(new Color((float)(1-Math.max(hp/(double)max_hp, 0)), (float)Math.max(hp/(double)max_hp, 0), (float)0.0));
        g.fillRect((int)(x+getWidth()/2-(Math.max(hp/(double)max_hp, 0)*getWidth())) - Game.x, 
        		(int)y-18 - Game.y, (int)(getWidth()*Math.max(hp/(double)max_hp, 0))*2, 6);
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
		if(onGround) lvy-=JUMP_POWER;
	}
	public void onDown()
	{
		
	}
	public boolean tryGet(Item item) {
		return control.tryGet(item);
	}
	
	public void addMignon(Mignon mignon)
	{
		control.addMignon(mignon);
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

	public Group getGroup() {
		return group;
	}

}
