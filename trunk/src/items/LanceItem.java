package items;

import entity.mob.Mob;
import weapon.Lance;
import weapon.Sword;
import main.World;

public class LanceItem extends Item{
	
	public LanceItem(long x, long y, World world) {
		this(x, y, null, world);		
	}
	public LanceItem(Mob mob) {
		this(0, 0, mob, mob.getWorld());
	}
	public LanceItem(long x, long y, Mob mob, World world)
	{
		super(x, y, mob, world);
		cooldown = 15;
		pickupTime = PICKUP_TIME;
	}
	@Override
	public void use(long x, long y)
	{
		if(timer>cooldown)
		{
			new Lance(owner, this, getAngle(x-owner.getX(), y-owner.getY()));
			timer = 0;
		}
	}
}
