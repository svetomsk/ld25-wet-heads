package block;

import java.awt.Color;

public class Fire extends Block{
	
	public static final double elasticity = 1;
	public static final int damage = 10;
	public int strength = 1;
	public static Color color = Color.decode("#ff0000");
	public static final boolean collidable = false;
	
	public Fire()
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
		if(random.nextInt()%10==0)delete();
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
