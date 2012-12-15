package weapon;
import items.Item;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.Entity;
import entity.mob.Mob;

import main.Input;
import main.World;


public class Weapon {
//	protected int damage;
//	protected int knockback;
	
//	protected double length;
//	protected double width;	

	protected double cx;
	protected double cy;
	
//	protected double sin;
//	protected double cos;
    
	protected double p1x;
	protected double p1y;
    
	protected double p2x;
	protected double p2y;
    
	protected double p3x;
	protected double p3y; 
    
	protected double p4x;
	protected double p4y;
	
	protected Mob owner;
	protected Item item;

	public Weapon(Mob owner, Item item)
	{
		this.owner = owner;		
		this.item = item;
		World.weapons.add(this);
	}
	public void draw(Graphics2D g)
	{
		
	}
	public void tick()
	{		
		//collide
		for(int q = 0;q < owner.getWorld().entities.size();q++)
		{
			Entity e = owner.getWorld().entities.get(q);
			if(e.equals(owner)) continue;
			if(!collideEntity(e)) continue;
			
            interactOn(e);
            //        e.interactOn(this);
		}
	}
	
	protected void interactOn(Entity e)
	{
		secondaryInteract(e);
	}
	protected void secondaryInteract(Entity e)
	{
		if(e instanceof Mob) interactOnMob((Mob) e);
		if(e instanceof Item) interactOnItem((Item) e);
	}	
	protected void interactOnMob(Mob mob){}
	protected void interactOnItem(Item item){}
	
	protected boolean collideEntity(Entity e)
	{		
		//we need better collision model
		
        if(e.isCollide(p1x, p1y)) return true;

        if(e.isCollide(p2x, p2y)) return true;

        if(e.isCollide(p3x, p3y)) return true;

        if(e.isCollide(p4x, p4y)) return true;
	
        if(isInside(e.getX(), e.getY(), p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y)) return true;
        if(isInside(e.getX()+e.getWidth(), e.getY(), p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y)) return true;
        if(isInside(e.getX(), e.getY()+e.getHeight(), p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y)) return true;
        if(isInside(e.getX()+e.getWidth(), e.getY()+e.getHeight(), p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y)) return true;
		return false;
	}        
    protected boolean isInside(double x, double y, double x1, double y1, double x2, double y2, double x3, double y3, double x4,double y4)
    {
        if(sign(x, y, x1, y1, x2, y2) & sign(x, y, x2, y2, x3, y3) & sign(x, y, x3, y3, x4, y4) & sign(x, y, x4, y4, x1, y1))
            return true;
        return false;
    }    
    private boolean sign(double x, double y, double x1, double y1,double x2, double y2)
    {
        double a = x1 - x;
        double b = y2 - y;
        double c = x2 - x;
        double d= y1 - y;
        
        return a * b - c * d >= 0;
    }
}