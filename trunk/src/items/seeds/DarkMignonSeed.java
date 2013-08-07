package items.seeds;

import main.Pictures;
import entity.mob.Character;
import entity.mob.Mob;
import entity.mob.controllers.Group;
import entity.mob.mignons.DarkMignon;
import entity.mob.mignons.Mignon;

public class DarkMignonSeed extends MignonSeed{
	
	@Override
	protected void initPictures() 
	{
		img = Pictures.darkMignon;
	}
	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( !super.interactOnMob(mob) ) return false;
		
		if(mob.getGroup() == Group.villians)
		{
			new DarkMignon().init(x, y, lvx, lvy, gvx, gvy, mob);
			delete();
			return true;
		}
		return false;
	}
}
