package items.seeds;

import main.Pictures;
import main.World;
import entity.mob.Character;
import entity.mob.Mob;
import entity.mob.mignons.DamageMignon;
import entity.mob.mignons.DarkMignon;
import entity.mob.mignons.JumpMignon;
import entity.mob.mignons.LightMignon;
import entity.mob.mignons.Mignon;

public class DarkMignonSeed extends MignonSeed{
	
	public DarkMignonSeed(long x, long y, World world)
	{
		this(x, y, null, world);
	}
	public DarkMignonSeed(Mob owner)
	{
		this(0, 0, owner, owner.getWorld());
	}
	public DarkMignonSeed(long x, long y, Mob owner, World world)
	{
		super(x, y, owner, world);
	}
	@Override
	protected void initPictures() 
	{
		img = Pictures.darkMignon;
	}
	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( super.interactOnMob(mob) ) return false;
		new DarkMignon(x, y, world, mob);
		delete();
		return true;
	}
	@Override
	protected boolean interactOnCharacter(Character character) 
	{
		return false;
	}
	@Override
	protected boolean interactOnMignon(Mignon mignon) 
	{
		return true;
	}
}
