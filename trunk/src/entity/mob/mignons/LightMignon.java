package entity.mob.mignons;

import items.seeds.DarkMignonSeed;
import main.Pictures;

public class LightMignon extends DamageMignon
{

//	private int recoveryTime;

	@Override
	protected void initPictures()
	{
		super.initPictures();
		img = Pictures.lightMignon;
	}

	@Override
	public void loseOwner()
	{
		super.loseOwner();
		new DarkMignonSeed().init(x, y, lvx, lvy, gvx, gvy, world);
	}
}
