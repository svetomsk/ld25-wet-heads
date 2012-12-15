package weapon;

import items.Item;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;

import entity.mob.Mob;

import main.Game;
import main.Input;
import main.Pictures;
import main.World;

public class Sword extends Weapon{

	protected static int damage = 10;
	protected static int knockback = 25;	
	protected static double length = 100;
	protected static double handlerLength = 30;	
	protected static double width = 30;	
	
	private static double angle1 = Math.PI*(-175/180.0);
	private static double angle2 = Math.PI*(-80/180.0);
	private static int hitTime = 20;
	
	protected double currentAngle;
	protected double dir;
	protected double w;
	protected double sin;
	protected double cos;
	
	
	public Sword(Mob owner, Item item)
	{
		super(owner, item);
		w = (angle2-angle1)/hitTime;
		
		dir = owner.choosenDir;
		currentAngle = angle1;
	}
	@Override
	protected void interactOnMob(Mob mob)
	{		
		mob.damage(damage, knockback, dir);
	}
	@Override
	public void tick()
	{
		currentAngle+=w;
		if(/*dir* */currentAngle>/* dir* */angle2 )//|| /* dir* */currentAngle</* dir* */angle1)
		{
			World.weapons.remove(this);
			return;
		}				
		double angle = dir*currentAngle;
		
        cx = owner.getX()+owner.getWidth()/2 - dir*owner.getWidth()/4;
        cy = owner.getY()+owner.getHeight()/2;
        sin = Math.sin(angle+Math.PI/2);
        cos = Math.cos(angle+Math.PI/2);
               
        double p0x = cx + (sin*width/2);
        double p0y = cy - (cos*width/2);
        
        p1x = p0x + (handlerLength * cos);
        p1y = p0y + (handlerLength * sin);
        
        p2x = p0x + (length * cos);
        p2y = p0y + (length * sin);
        
        p3x = p2x - (width * sin);
        p3y = p2y + (width * cos); 
        
        p4x = p3x - ((length - handlerLength) * cos);
        p4y = p3y - ((length - handlerLength) * sin);
        
		super.tick();
	}
	@Override
	public void draw(Graphics2D g)
	{        
        double angle = dir*currentAngle;
        
        double x = cx - Game.x;
        double y = cy - Game.y;
        
        g.setColor(Color.GREEN);
        g.fillOval((int)x-3, (int)y-3, 6, 6);
        
        Image value = null;
//        if(wep.type.equals("Sword"))
        
        value = Pictures.weps[World.k];
        
        g.rotate(angle, (int)x, (int)y);
        g.drawImage(value, (int) x - (int)width/2, (int) y, null);
        g.rotate(-angle, (int)x, (int)y);
        
        g.setColor(Color.GREEN);
        g.drawLine((int)p1x-Game.x, (int)p1y-Game.y, (int)p2x-Game.x, (int)p2y-Game.y);
        g.drawLine((int)p2x-Game.x, (int)p2y-Game.y, (int)p3x-Game.x, (int)p3y-Game.y);
        g.drawLine((int)p3x-Game.x, (int)p3y-Game.y, (int)p4x-Game.x, (int)p4y-Game.y);
        g.drawLine((int)p4x-Game.x, (int)p4y-Game.y, (int)p1x-Game.x, (int)p1y-Game.y);
	}
}