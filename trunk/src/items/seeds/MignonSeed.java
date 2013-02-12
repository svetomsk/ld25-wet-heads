package items.seeds;

import items.Item;
import main.World;
import entity.Chest;
import entity.Entity;
import entity.mob.Mob;
import entity.mob.mignons.Mignon;

public class MignonSeed extends Item{

	@Override
	public Entity init(long x, long y, Mob owner, World world)
	{
		isPickable = false;
		return super.init(x, y, owner, world);
	}
	@Override
	protected boolean interactOnMob(Mob mob) {
		if(pickupTime<0) return super.interactOnMob(mob);		
		return false;
	}
	@Override
	protected boolean interactOnMignon(Mignon mignon) {
		return false;
	}
	@Override
	protected boolean interactOnChest(Chest chest) {
		return false;
	}
}
