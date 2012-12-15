package weapon.ranged;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;

import entity.mob.Mob;


import weapon.Weapon;
import weapon.shell.Arrow;

import main.Game;
import main.Input;
import main.Pictures;
import main.World;

public class Ranged extends Weapon{

	public double currentAngle;
	public boolean isHit;
	public double cooldown;
	
	public Ranged(Mob owner)
	{
		super(owner);
		length = 20;
		width = 35;
		cooldown = 45;
		
		World.weapons.add(this);
	}
	public void hit(Input input)
	{		
		currentAngle = getAngle(World.input.x - owner.x + Game.x - owner.width/2,
				World.input.y - owner.y + Game.y - owner.height/2 ); 		
		isHit = true;
	}
	public double getAngle(double dx, double dy)
	{
		double l = Math.sqrt(dx*dx+dy*dy);
		double asin = Math.asin(Math.abs(dy/l));
		if(dx>0 && dy>0) return asin-Math.PI/2;
		if(dx>0 && dy<0) return -asin-Math.PI/2;
		if(dx<0 && dy>0) return -asin+Math.PI/2;
		if(dx<0 && dy<0) return asin+Math.PI/2;
		return 0.0;
	}
	@Override
	public void tick(Input input)
	{
		hit(input);
		if(!isHit) return;
		cooldown--;
		if(cooldown<0)
		{
			isHit = false;
			cooldown = 45;
			return;
		}				
        cx = owner.x+owner.width/2;
        cy = owner.y+owner.height/2;
	}
	@Override
	public void draw(Graphics2D g)
	{        
		if(!isHit) return;
        double angle = currentAngle;
        
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
	}
}
