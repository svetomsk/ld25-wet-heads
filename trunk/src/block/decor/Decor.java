package block.decor;

import java.awt.Color;

import block.Block;

public class Decor extends Block{
	
	public static final double elasticity = 1;
	
	public Decor()
	{
		
	}
	@Override
	public Color getColor()
	{
		return Color.decode("#22b14c");
	}
	@Override
	public boolean getCollidable()
	{
		return false;
	}
	@Override
	public double getElasticity()
	{
		return elasticity;
	}
}
