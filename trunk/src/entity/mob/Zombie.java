package entity.mob;

import main.World;
import entity.mob.controllers.ArchAngelController;
import entity.mob.mignons.LightMignon;

public class Zombie extends Mob{
	
	private static int width = 42;
	private static int height = 64;
	
	public Zombie(long x, long y, World world)
	{
		super(x, y, world);
		
		damage = 10;
		knockback = 7;
		speed = 7;
		
		hp = 50;
		max_hp = 50;
		
		control = new ArchAngelController(this);
		
		for(int q=0;q<8;q++)
		{
			new LightMignon(x+q, y+q, world, this);
		}
	}

//	@Override
//	protected boolean interactOnCharacter(Character character) {
//		double dir = character.getX()-x >= 0 ? 1 : -1; 
//		character.damage(damage, knockback, dir);
//		return true;
//	}
	
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
