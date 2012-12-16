package items.seeds;

import main.World;
import entity.Chest;
import entity.mob.Character;
import entity.mob.Mignon;
import entity.mob.Mob;
import items.Item;

public class MignonSeed extends Item{
	
	protected static final int APPEARANCE_HEIGHT = 128;

	public MignonSeed(long x, long y, World world)
	{
		this(x, y, null, world);
	}
	public MignonSeed(Mob owner)
	{
		this(0, 0, owner, owner.getWorld());
	}
	public MignonSeed(long x, long y, Mob owner, World world)
	{
		super(x, y, owner, world);
		isPickable = false;
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
