package entity.mob.controllers;

import items.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.World;

import entity.mob.Mob;
import entity.mob.mignons.DamageMignon;
import entity.mob.mignons.JumpMignon;
import entity.mob.mignons.Mignon;

public class Controller
{

	protected Mob mob;
	protected ArrayList<Mignon> floak = new ArrayList<Mignon>();

	public Controller(Mob mob)
	{
		this.mob = mob;
	}
	public void tick()
	{

	}
	public void save(DataOutputStream os) throws IOException
	{
		
	}
	public void load(DataInputStream is, World world) throws IOException
	{
		
	}

	public void addMignon(Mignon mignon)
	{
		floak.add(mignon);
	}

	protected void floakFollow(long tx, long ty)
	{
		for (int q = 0; q < floak.size(); q++)
		{
			if ((floak.get(q) instanceof DamageMignon))
			{
				Mignon m = floak.get(q);
				m.followPoint(tx, ty);
			}
		}
	}

	protected void floakState(long tx, long ty)
	{
		for (int q = 0; q < floak.size(); q++)
		{
			if (floak.get(q) instanceof JumpMignon)
			{
				Mignon m = floak.get(q);
				if (!m.state(tx, ty))
					break;
			}
		}
	}

	protected void floakReturn()
	{
		for (int q = 0; q < floak.size(); q++)
		{
			if (floak.get(q) instanceof JumpMignon)
			{
				Mignon m = floak.get(q);
				m.comeBack();
			}
		}
	}

	public void floakFree()
	{
		for (int q = 0; q < floak.size(); q++)
		{
			floak.get(q).loseControl();
		}
	}

//	protected void floakAttack(long tx, long ty)
//	{
//		for(int q=0;q<floak.size();q++)
//		{
//			if(floak.get(q) instanceof DamageMignon) 
//			{
//				Mignon m = floak.get(q);
//				long r = Entity.getDistanse(m, m.getOwner()); 
//				if(r<Mignon.RAD_SPIN) m.attack(tx, ty);
//			}
//		}
//	}
//	public void attack(long tx, long ty){}

	public boolean tryGet(Item item)
	{
		return false;
	}
}
