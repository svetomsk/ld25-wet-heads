package block.decor;

import java.awt.Color;

import block.Rock;

public class Ghost_Rock extends Rock{
	
	public static final double elasticity = 1;
	
	@Override
	public void tick()
	{
		super.tick();
		delete();
	}
	
	@Override
	public boolean getCollidable() 
	{
		return false;
	}
	@Override
	public Color getColor()
	{
		return Color.decode("#7f7f7e");
	}
}
