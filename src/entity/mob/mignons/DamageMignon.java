package entity.mob.mignons;

import items.seeds.DamageMignonSeed;
import main.Pictures;
import entity.mob.Mob;

public class DamageMignon extends Mignon{

	private int recoveryTime;
	
	@Override
	protected void initPictures() 
	{
		img = Pictures.damageMignon;
		super.initPictures();
	}
	@Override
	public void loseOwner() 
	{
		super.loseOwner();
		new DamageMignonSeed().init(x, y, world);
	}
	@Override
	public void tick() {
		super.tick();
		recoveryTime--;
	}
	
	@Override
	protected boolean interactOnMob(Mob mob) {
		if( !super.interactOnMob(mob)) return false;
		if(recoveryTime > 0) return false;
		if(getOwner().getGroup().isMember(mob)) return false;
		
		double dir = mob.getX()-x >= 0 ? 1 : -1; 
		mob.damage(10, 0, dir);
		recoveryTime = 150;
		return super.interactOnMob(mob);
	}
}
