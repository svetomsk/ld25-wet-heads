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

public class Lance extends Weapon{

	protected static int damage = 10;
	protected static int knockback = 7;	
	protected static int length = 150;
	protected static int handlerLength = 120;	
	protected static int width = 20;	
	protected static int hitLength = 100;
	protected static int hitTime = 20;
	
	protected double currentAngle;
	protected double sin;
	protected double cos;
	protected double hitSpeed;
	protected double currentLength;
	
	public Lance(Mob owner, Item item, double angle)
	{
		super(owner, item);
		hitSpeed = hitLength/hitTime;
		currentAngle = angle; 
		currentLength = hitLength;
//				getAngle(World.input.x - owner.x + Game.x - owner.width/2,
//				World.input.y - owner.y + Game.y - owner.height/2 ); 
	}
//	public double getAngle(double dx, double dy)
//	{
//		double l = Math.sqrt(dx*dx+dy*dy);
//		double asin = Math.asin(Math.abs(dy/l));
//		if(dx>0 && dy>0) return asin-Math.PI/2;
//		if(dx>0 && dy<0) return -asin-Math.PI/2;
//		if(dx<0 && dy>0) return -asin+Math.PI/2;
//		if(dx<0 && dy<0) return asin+Math.PI/2;
//		return 0.0;
//	}
	@Override
	public void tick()
	{
		currentLength-= hitSpeed;
		if(currentLength<0)
		{
			World.weapons.remove(this);
			return;
		}		
		
        sin = Math.sin(currentAngle+Math.PI/2);
        cos = Math.cos(currentAngle+Math.PI/2);

        cx = owner.getX()+owner.getWidth()/2-currentLength*cos;
        cy = owner.getY()+owner.getHeight()/2-currentLength*sin;
               
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
        double angle = currentAngle;
//        System.out.println(""+angle);
        
        double x = cx - Game.x;
        double y = cy - Game.y;
        
        g.setColor(Color.GREEN);
        g.fillOval((int)x-3, (int)y-3, 6, 6);
        
        Image value = null;
//        if(wep.type.equals("Sword"))
        value = Pictures.weps[World.k];
        Canvas s = new Canvas();
        AreaAveragingScaleFilter aasf = new AreaAveragingScaleFilter((int)width, (int) length);
        value = s.createImage(new FilteredImageSource(value.getSource(), aasf));                 
        
        g.rotate(angle, (int)x, (int)y);
        g.drawImage(value, (int) (x-width/2), (int) (y), null);
        g.rotate(-angle, (int)x, (int)y);
        
        g.setColor(Color.GREEN);
        g.drawLine((int)p1x-Game.x, (int)p1y-Game.y, (int)p2x-Game.x, (int)p2y-Game.y);
        g.drawLine((int)p2x-Game.x, (int)p2y-Game.y, (int)p3x-Game.x, (int)p3y-Game.y);
        g.drawLine((int)p3x-Game.x, (int)p3y-Game.y, (int)p4x-Game.x, (int)p4y-Game.y);
        g.drawLine((int)p4x-Game.x, (int)p4y-Game.y, (int)p1x-Game.x, (int)p1y-Game.y);
	}
}
