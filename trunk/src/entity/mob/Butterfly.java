package entity.mob;

import items.seeds.JumpMignonSeed;
import main.World;
import entity.mob.controllers.ButterflyController;

public class Butterfly extends Mob{

	private static int width = 32;
	
	
	public Butterfly(long x, long y, World world)
	{
		super(x, y, world);
		
		damage = 0;
		knockback = 0;
		hp = 20;
		max_hp = 20;
				
		control = new ButterflyController(this);
	}
	@Override
	public void onDead() 
	{
		new JumpMignonSeed(x+getWidth()/2, y+getHeight()/2, world);
	}
	@Override
	public int getWidth() 
	{
		return width;
	}
	@Override
	public int getHeight() {
		return width;
	}
}
