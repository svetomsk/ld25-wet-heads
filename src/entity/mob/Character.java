package entity.mob;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import entity.mob.controllers.Controller;
import entity.mob.controllers.Group;

import particle.Spark;

import GUI.GUI;
import main.Game;
import main.Pictures;
import main.World;
import weapon.Lance;
import weapon.Sword;
import weapon.Weapon;
import weapon.ranged.Bow;

public class Character extends Mob{

	public int cooldownAfterDamage;
	private int width = 64;
	private int height = 64;
	private GUI control;
	
	public Character(int x, int y, World world, Group group)
	{
		this(x, y, world);
		group.addMob(this);
		this.group = group;
		Group.mobs.removeMob(this);
	}
	public Character(int x, int y, World world) {
		super(x, y, world);
		damage = 0;
		knockback = 0;
		speed = 8;
		
		hp = 100;
		max_hp = 100;
		
		super.control = new GUI(this, Game.getInput());
		control = (GUI) super.control;
		Game.setGUI((GUI)control);
	}
	@Override
	public void damage(int damage, int knockback, double dir)
	{
		if(cooldownAfterDamage>0) return;
		super.damage(damage, knockback, dir);
		cooldownAfterDamage = 12;
	}
    @Override
    public void tick()
    {
    	super.tick();
	    cooldownAfterDamage--;
	    
//	    new Spark(x+width/2, y+height/2, world);
    }
    
    @Override
    public void onUp() 
    {
    	super.onUp();
    	
    	lvy-=0.7;
    }
    
//    @Override
//    protected boolean interactOnMob(Mob mob)
//    {
//    	if(!super.interactOnMob(mob)) return true;
//    	damage(mob.damage, mob.knockback, -choosenDir);
//    	
//    	return true;
//    }
    
    @Override
    public void draw(Graphics2D g)
    {
    	int drawx = (int) (x-Game.x+width/2);
    	int drawy = (int) (y-Game.y+height/2);

    	g.drawImage(Pictures.roll, drawx-width/2, drawy-height/2, null);
        
    	double angle = getAngle(lvx, lvy)+Math.PI/2;
    	
        g.rotate(angle, drawx, drawy);
        g.drawImage(Pictures.roll_flame, drawx-64, drawy-64, null);
        g.rotate(-angle, drawx, drawy);
        
        angle = getAngle(control.getX()-drawx, control.getY()-drawy)+Math.PI/2;        
        Image eye = control.getX()-drawx >= 0 ? Pictures.eye_right : Pictures.eye_left;
        if(control.getX()-drawx < 0 ) angle -= Math.PI;
        
        g.rotate(angle, drawx, drawy);
        g.drawImage(eye, drawx-width/2, drawy-height/2, null);
        g.rotate(-angle, drawx, drawy);
        super.draw(g);
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
}
