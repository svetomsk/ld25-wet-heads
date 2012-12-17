package items.seeds;

import main.Pictures;
import main.World;
import entity.mob.Mob;
import entity.mob.mignons.DamageMignon;
import entity.mob.mignons.JumpMignon;
import entity.mob.mignons.LightMignon;
import entity.mob.mignons.Mignon;

public class LightMignonSeed extends MignonSeed{
	
	public LightMignonSeed(long x, long y, World world)
	{
		this(x, y, null, world);
	}
	public LightMignonSeed(Mob owner)
	{
		this(0, 0, owner, owner.getWorld());
	}
	public LightMignonSeed(long x, long y, Mob owner, World world)
	{
		super(x, y, owner, world);
	}
	@Override
	protected void initPictures() 
	{
		img = Pictures.lightMignon;
	}
	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( !super.interactOnMob(mob) ) return false;
		new LightMignon(x, y, world, mob);
		delete();
		return true;
	}
}
