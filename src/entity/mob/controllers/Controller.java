package entity.mob.controllers;

import java.util.ArrayList;

import entity.Entity;
import entity.mob.Mob;
import entity.mob.mignons.DamageMignon;
import entity.mob.mignons.Mignon;
import items.Item;

public class Controller {

	protected Mob mob;
	
	protected ArrayList<Mignon> floak = new ArrayList<Mignon>();
	
	public Controller(Mob mob)
	{
		this.mob = mob;
	}
	
	public void tick()
	{
		
	}
	
	public void addMignon(Mignon mignon)
	{
		floak.add(mignon);
	}
	
	protected void floakFollow(long tx, long ty)
	{
		for(int q=0;q<floak.size();q++)
		{
			Mignon m = floak.get(q);
			m.followPoint(tx, ty);
		}
	}
	protected void floakAttack(long tx, long ty)
	{
		for(int q=0;q<floak.size();q++)
		{
			if(floak.get(q) instanceof DamageMignon) 
			{
				Mignon m = floak.get(q);
				long r = Entity.getDistanse(m, m.getOwner()); 
				if(r<Mignon.RAD_SPIN) m.attack(tx, ty);
			}
		}
	}
	public void attack(long tx, long ty){}

	public boolean tryGet(Item item) {
		return false;
	}
}
