package block;

import java.awt.Color;

public class Rubber extends Block{
	
	public static final double elasticity = -1;
	public static final int damage = 0;
	public int strength = 1;
	public static Color color = Color.decode("#a349a4");
	public static final boolean collidable = true;
	
	public Rubber()
	{
		
	}
	@Override
	public Color getColor()
	{
		return color;
	}
	@Override
	public boolean getCollidable()
	{
		return collidable;
	}
	@Override
	public int getDamage()
	{
		return damage;
	}
	@Override
	public double getElasticity()
	{
		return elasticity;
	}
}
