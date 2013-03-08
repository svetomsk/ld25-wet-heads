package items.seeds;

import main.Pictures;
import entity.mob.Mob;
import entity.mob.mignons.LightMignon;

public class LightMignonSeed extends MignonSeed
{

	@Override
	protected void initPictures()
	{
		img = Pictures.lightMignon;
	}

	@Override
	protected boolean interactOnMob(Mob mob)
	{
		if (!super.interactOnMob(mob)) return false;
		new LightMignon().init(x, y, lvx, lvy, gvx, gvy, mob);
		delete();
		return true;
	}
}
