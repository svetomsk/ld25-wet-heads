package items;

import entity.mob.Mob;
import weapon.Lance;
import weapon.Sword;
import main.World;

public class SwordItem extends Item{
	
	public SwordItem(long x, long y, World world) {
		this(x, y, null, world);		
	}
	public SwordItem(Mob mob) {
		this(0, 0, mob, mob.getWorld());
	}
	public SwordItem(long x, long y, Mob mob, World world)
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
			new Sword(owner, this);
			timer = 0;
		}
	}
	

}
