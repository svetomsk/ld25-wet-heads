package weapon.shell;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;

import entity.mob.Mob;

import weapon.Weapon;

import main.Game;
import main.Input;
import main.Pictures;
import main.World;

public class Arrow extends Shell{
	public Arrow(Mob owner, double angle)
	{
		super(owner, angle);
		
		damage = 10;		
		knockback = 7;
		length = 35;
		width = 5;
		speed = 20;
		GRAVITY = World.GRAVITY/3.0;
		SLOWLY = 0.99;
		vx = speed*Math.cos(currentAngle);
		vy = speed*Math.sin(currentAngle);
	}
	@Override
	public void tick(Input input)
	{
		super.tick(input);	
		currentAngle = getAngle(vx, vy);
	}
	@Override
	public void draw(Graphics2D g)
	{        
        double angle = currentAngle;
        
        double x = cx - Game.x;
        double y = cy - Game.y;
        
        g.setColor(Color.GREEN);
        g.fillOval((int)x-3, (int)y-3, 6, 6);
        
        Image value = null;
        
        value = Pictures.weps[World.k];
        Canvas s = new Canvas();
        AreaAveragingScaleFilter aasf = new AreaAveragingScaleFilter((int)width, (int) length);
        value = s.createImage(new FilteredImageSource(value.getSource(), aasf));                 
        
        g.rotate(angle, (int)x, (int)y);
        g.drawImage(value, (int) (x-width/2), (int) (y-length), null);
        g.rotate(-angle, (int)x, (int)y);
	}
}
