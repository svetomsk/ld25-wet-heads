package entity.mob.controllers;

import main.World;
import entity.Entity;
import entity.mob.Mob;

public class ButterflyController extends Controller
{

	public ButterflyController(Mob mob) 
	{
		super(mob);
	}
	
	private boolean right;
	@Override
	public void tick()
	{
//		if(right) mob.onRight();
//		else mob.onLeft();
//		
//		if(isEnd()) right = !right;
	}
	
	private boolean isEnd()
	{
		if(Math.abs(mob.getLVX()) < 3) return true;
		if(!right)
		if(!mob.getWorld().collideIslands(mob.getX()-8, mob.getY()+mob.getHeight()+8) ) return true;
		if(right)
		if(!mob.getWorld().collideIslands(mob.getX()+mob.getWidth()+10, mob.getY()+mob.getHeight()+10) ) return true;
		return false;
	}
}
