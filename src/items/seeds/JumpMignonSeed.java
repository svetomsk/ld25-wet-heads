package items.seeds;

import main.Pictures;
import entity.mob.Mob;
import entity.mob.mignons.JumpMignon;

public class JumpMignonSeed extends MignonSeed{
	
	@Override
	protected void initPictures() 
	{
		img = Pictures.jumpMignon;
	}
	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( !super.interactOnMob(mob) ) return false;
		new JumpMignon().init(x, y, lvx, lvy, gvx, gvy, mob);
		delete();
		return true;
	}
}
