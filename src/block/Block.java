package block;

import java.awt.Color;

import block.decor.Background;
import block.decor.Ghost_Rock;
import block.decor.Grass;
import block.decor.Wood;

public class Block {
	
//	public static final double elasticity = 0;
//	public static final int damage = 0;
//	public static final boolean collidable = true;
//	public int strength = 1;
//	public static Color color = Color.BLACK;
//	public static Random random = new Random();
	
	protected boolean isDeleted = false;
	
	public Block()
	{
		
	}
	public Color getColor()
	{
		return Color.BLACK;
	}
	public boolean getCollidable()
	{
		return true;
	}
	public int getDamage()
	{
		return 0;
	}
	public double getElasticity()
	{
		return 0;
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
				
//				----------------------
				if(byt == 125)
				{
					res[q][w] = new Rock();
					continue;
				}
//				----------------------
				
				else if(byt == 0 || byt>96)
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
				else if(byt == 3  || (byt>=64 && byt<=96) )
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
				else if(byt == 7)
				{
					res[q][w] = new Background();
					continue;
				}
				else if(byt == 8)
				{
					res[q][w] = new Wood();
					continue;
				}
				else
				{
					res[q][w] = new Rock();
				}
			}
		}
		return res;
	}
}
