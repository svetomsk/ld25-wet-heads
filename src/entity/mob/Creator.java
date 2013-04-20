package entity.mob;

import java.awt.Graphics2D;

import main.Game;
import main.Pictures;
import main.World;
import GUI.CreatorGUI;

public class Creator extends Mob
{
	private CreatorGUI control;
	@Override
	public void finalInit(World world)
	{
		super.finalInit(world);
		super.control = new CreatorGUI(this, Game.getInput());
		control = (CreatorGUI) super.control;
		Game.setGUI(control);
		
		group.removeMob(this);
	}
	
	@Override
	public void damage(int damage, int knockback, double dir){}
	@Override
	protected boolean collideIslands(boolean verticalWalls){return false;}
	
	@Override
	protected void updateVelocity() 
	{
		if(Math.abs(lvx) < 1) lvx = 0;
		if(Math.abs(lvy) < 1) lvy = 0;
        slowly();
	}
	@Override
	public void onUp() 
	{
		lvy--;
	}
	@Override
	public void onDown() 
	{
		lvy++;
	}
	@Override
	protected void slowly()
	{
		lvx *= getSpeed()/(getSpeed()+1);
		lvy *= getSpeed()/(getSpeed()+1);
	}
	
    @Override
    protected void initPictures() 
    {
    	img = Pictures.creator;
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
        
//        drawHealth(g);
    }
    
    @Override
	public double getSpeed()
	{
		return 32;
	}
    @Override
	public double getJumpPower()
	{
		return 13;
	}
    @Override
	public int getMaxHP()
	{
		return 1;
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
