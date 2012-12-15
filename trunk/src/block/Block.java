package block;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.Game;
import main.World;

public class Block {
	
	public static final double elasticity = 0;
	public static final int damage = 0;
	public static final boolean collidable = true;
	public int strength = 1;
	public static Color color = Color.BLACK;
	public static Random random = new Random();
	
	protected boolean isDeleted = false;
	
	public Block()
	{
		
	}
	public Color getColor()
	{
		return color;
	}
	public boolean getCollidable()
	{
		return collidable;
	}
	public int getDamage()
	{
		return damage;
	}
	public double getElasticity()
	{
		return elasticity;
	}
	public boolean isDeleted()
	{
		return isDeleted;
	}
	protected void delete()
	{
		isDeleted = true;
	}
	public void tick()
	{
		
	}
	public static Block[][] parse(byte[][] arr)
	{
		Block[][] res = new Block[arr.length][arr[0].length];
		for(int q=0;q<arr.length;q++)
		{
			for(int w=0;w<arr[0].length;w++)
			{
				int byt = arr[q][w];
				if(byt == 0)
				{
					res[q][w] = null;
					continue;
				}
				else if(byt == 1)
				{
					res[q][w] = new Grass();
					continue;
				}
				else if(byt == 2)
				{
					res[q][w] = new Dirt();
					continue;
				}
				else if(byt == 3)
				{
					res[q][w] = new Rock();
					continue;
				}
				else if(byt == 4)
				{
					res[q][w] = new Lava();
					continue;
				}
				else if(byt == 5)
				{
					res[q][w] = new Rubber();
					continue;
				}
				else if(byt == 6)
				{
					res[q][w] = new Ghost_Rock();
					continue;
				}
			}
		}
		return res;
	}
}
