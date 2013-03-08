package items.seeds;

import main.Pictures;
import entity.mob.Mob;
import entity.mob.mignons.DamageMignon;

public class DamageMignonSeed extends MignonSeed{
	
	@Override
	protected void initPictures() 
	{
		img = Pictures.damageMignon;
	}

	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( !super.interactOnMob(mob) ) return false;
		new DamageMignon().init(x, y, lvx, lvy, gvx, gvy, mob);
		delete();
		return true;
	}
}
