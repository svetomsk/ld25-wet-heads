package entity.mob.mignons;

import items.seeds.DarkMignonSeed;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Pictures;
import entity.mob.Mob;

public class LightMignon extends DamageMignon
{

	private int recoveryTime;

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
		new DarkMignonSeed().init(x + getWidth() / 2, y + getHeight() / 2, world);
	}
}
