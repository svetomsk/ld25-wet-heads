package items.seeds;

import main.World;
import entity.mob.Mob;
import entity.mob.mignons.DamageMignon;
import entity.mob.mignons.Mignon;

public class DamageMignonSeed extends MignonSeed{
	
	public DamageMignonSeed(long x, long y, World world)
	{
		this(x, y, null, world);
	}
	public DamageMignonSeed(Mob owner)
	{
		this(0, 0, owner, owner.getWorld());
	}
	public DamageMignonSeed(long x, long y, Mob owner, World world)
	{
		super(x, y, owner, world);
	}

	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( !super.interactOnMob(mob) ) return false;
		new DamageMignon(x, y-APPEARANCE_HEIGHT, world, mob);
		delete();
		return true;
	}
}
