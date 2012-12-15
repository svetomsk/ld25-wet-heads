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
import weapon.shell.Buillet;
import weapon.shell.Knife;

import main.Game;
import main.Input;
import main.Pictures;
import main.World;

public class Bow extends Ranged{
	
	public Bow(Mob owner)
	{
		super(owner);
		length = 20;
		width = 35;
		cooldown = 60;
	}
	public void hit(Input input)
	{
		if(isHit) return;
		if(!input.b0) return;
		super.hit(input);
		new Arrow(owner, currentAngle);
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
