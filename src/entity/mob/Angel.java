package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.controllers.ArchAngelController;
import entity.mob.mignons.LightMignon;


import block.Block;

import main.Game;
import main.Island;
import main.World;

public class Angel extends Mob{
	
	private static int width = 42;
	private static int height = 64;
	
	public Angel(long x, long y, World world)
	{
		super(x, y, world);
		
		damage = 5;
		knockback = 7;
		speed = 7;
		
		hp = 50;
		max_hp = 50;
		
		control = new ArchAngelController(this);
		
		for(int q=0;q<3;q++)
		{
			new LightMignon(x+q, y+q, world, this);
		}
	}

	@Override
	protected boolean interactOnCharacter(Character character) {
		double dir = character.getX()-x >= 0 ? 1 : -1; 
		character.damage(damage, knockback, dir);
		return true;
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
