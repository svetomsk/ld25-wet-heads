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

public class Shell extends Weapon{

	protected double currentAngle;
	protected double vx, vy;
	protected int speed;
	protected double GRAVITY;
	protected double SLOWLY;
	
	public Shell(Mob owner, double angle)
	{
		super(owner);
		
		currentAngle = angle+Math.PI/2;
		cx = owner.x+owner.width/2;
		cy = owner.y+owner.height/2;
		isChoosen = true;
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
		vx*=SLOWLY;
		vy+=GRAVITY;

		addVelocity();
        
        for(int q=0;q<World.mobs.size();q++)
		{
			Mob mob = World.mobs.get(q);
			if(mob.equals(owner)) continue;
			if(!World.collidePoint(cx, cy, mob)) continue;
            mob.damage(this, (int)Math.signum(vx));
			World.weapons.remove(this);
			return;
		}
	}
	public void addVelocity()
	{
		int block_size = World.BLOCK_SIZE;
		int steps=1;
		steps = (int) Math.ceil(Math.max(steps, Math.abs(vx/block_size)));
		steps = (int) Math.ceil(Math.max(steps, Math.abs(vy/block_size)));
		steps++;
		for(int w=0;w<steps;w++)
		{
			cx += vx/steps;
	        cy += vy/steps;
	        if(World.collideIslands(cx, cy))
	        {
				World.weapons.remove(this);
				return;        	
	        }
		}
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
