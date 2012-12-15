package entity.mob;


import java.awt.Color;
import java.awt.Graphics2D;

import entity.mob.controllers.ZombieController;


import block.Block;

import main.Game;
import main.Island;
import main.World;
import weapon.Weapon;

public class Zombie extends Mob{
	
	private static int width = 30;
	private static int height = 45;
	
	public Zombie(long x, long y, World world)
	{
		super(x, y, world);
		
		damage = 10;
		knockback = 7;
		speed = 7;
		
		control = new ZombieController(this);
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
