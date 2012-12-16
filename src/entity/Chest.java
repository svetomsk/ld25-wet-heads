package entity;

import java.util.ArrayList;

import entity.mob.Mignon;
import entity.mob.Mob;

import items.Item;
import items.seeds.MignonSeed;
import main.World;

public class Chest extends Mob{

	private ArrayList<Item> items = new ArrayList<Item>();
	
	private int width = 32;
	
	public Chest(long x, long y, World world) {
		super(x, y, world);
	}
	
	public void addItem(Item item)
	{
		items.add(item);
	}
	
	@Override
	protected boolean interactOnMob(Mob mob) {
		if( !super.interactOnMob(mob)) return false;
		open();
		return true;
	}
	
	private void open()
	{
		while(!items.isEmpty())
		{
			items.get(items.size()-1).splash();
			items.remove(items.size()-1);
		}
	}
	
	@Override
	protected boolean interactOnMignon(Mignon mignon) {
		return false;
	}
	
	@Override
	public void tick()
	{
		updateVelocity();
		updateCoord();
	}
	@Override
	public void damage(int damage, int knockback, double dir) {}
	@Override
	public void addMignon(Mignon mignon) {}
	
	@Override
	public int getWidth() 
	{
		return width;
	}
	@Override
	public int getHeight() 
	{
		return width;
	}
}
