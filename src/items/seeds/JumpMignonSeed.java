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
		JumpMignon jm = new JumpMignon();
		jm.init(x, y, world, mob);
		delete();
		return true;
	}
}
