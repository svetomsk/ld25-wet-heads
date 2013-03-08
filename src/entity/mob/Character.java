package entity.mob;

import java.awt.Graphics2D;

import particle.Spark;
import particle.Wind;

import entity.mob.controllers.Group;

import main.Game;
import main.Pictures;
import main.World;
import GUI.GUI;

public class Character extends Mob
{
//	public int cooldownAfterDamage;
	private GUI control;
	
	@Override
	public void finalInit(World world)
	{
		super.finalInit(world);
		super.control = new GUI(this, Game.getInput());
		control = (GUI) super.control;
		Game.setGUI((GUI)control);
		
		group.removeMob(this);
		Group.villians.addMob(this);
		group = Group.villians;
	}
	
//	@Override
//	public void damage(int damage, int knockback, double dir)
//	{
//		if(cooldownAfterDamage>0) return;
//		super.damage(damage, knockback, dir);
//		cooldownAfterDamage = 12;
//	}
    @Override
    public void tick()
    {
    	super.tick();
//	    cooldownAfterDamage--;
    	
    	for(int q=0;q<10;q++)
    	{
    		double angle = Math.PI*2*Math.random();
    		double persent = Math.random();
    		long sx = (long) (x+(Math.cos(angle)*persent+1)*getWidth()/2);
    		long sy = (long) (y+(Math.sin(angle)*persent+1)*getHeight()/2);
    		new Spark(sx, sy, world);
    	}
    	
    	if(Math.random()>0.99)
    	{
    		double angle = Math.PI*2*Math.random();
    		long wx = (long) (x+getWidth()/2+(Math.cos(angle))*getWidth()*5);
    		long wy = (long) (y+getHeight()/2+(Math.sin(angle))*getHeight()*5);
    		new Wind(wx, wy, world);
    	}
    }
    @Override
    public void onUp() 
    {
    	super.onUp();
    	
    	lvy-=0.7;
    }
    
    @Override
    protected void initPictures() 
    {
    	img = Pictures.roll;
    	super.initPictures();
    }
    @Override
    public void draw(Graphics2D g)
    {
    	
    	int drawx = (int) (x-Game.x+width/2);
    	int drawy = (int) (y-Game.y+height/2);

		double angle = getAngle(lvx, lvy);
		  
		g.rotate(angle, drawx, drawy);
		g.drawImage(img[currentFrame], drawx-img[currentFrame].getWidth(null)/2, drawy-img[currentFrame].getHeight(null)/2, null);
		g.rotate(-angle, drawx, drawy);
        
//        double angle = getAngle(control.getX()-drawx, control.getY()-drawy)+Math.PI/2;
        
//        Image eye = control.getX()-drawx >= 0 ? Pictures.eye_right : Pictures.eye_left;
//        if(control.getX()-drawx < 0 ) angle -= Math.PI;
//        
//        g.rotate(angle, drawx, drawy);
//        g.drawImage(eye, drawx-width/2, drawy-height/2, null);
//        g.rotate(-angle, drawx, drawy);
        
//        super.draw(g);
        drawHealth(g);
    }
    
    @Override
	public double getSpeed()
	{
		return 9;
	}
    @Override
	public double getJumpPower()
	{
		return 13;
	}
    @Override
	public int getMaxHP()
	{
		return 100;
	}
    @Override
	public int getDamage()
	{
		return 0;
	}
    @Override
	public int getKnokback()
	{
		return 0;
	}
    @Override
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
}
