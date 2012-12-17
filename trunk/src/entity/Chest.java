package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.mob.Character;
import entity.mob.Mob;
import entity.mob.controllers.Controller;
import entity.mob.mignons.Mignon;

import items.Item;
import items.seeds.MignonSeed;
import main.Game;
import main.Pictures;
import main.World;

public class Chest extends Mob{

	private ArrayList<Item> items = new ArrayList<Item>();
	
	private int width = 32;
	
	public Chest(long x, long y, World world) {
		super(x, y, world);
		control = new Controller(this); 
	}
	public void addItem(Item item)
	{
		items.add(item);
	}
	@Override
	protected void initPictures() 
	{
		super.initPictures();
		img = Pictures.chest;
	}
	@Override
	protected boolean interactOnMob(Mob mob) 
	{
		if( super.interactOnMob(mob) ) return false;
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
		delete();
	}
	@Override
	protected boolean interactOnCharacter(Character character) 
	{
		return false;
	}
	@Override
	public void draw(Graphics2D g) 
	{
    	int drawx = (int) (x-Game.x+getWidth()/2);
    	int drawy = (int) (y-Game.y+getHeight()/2);
    	
        g.drawImage(img[currentFrame], drawx-img[currentFrame].getWidth(null)/2, drawy-img[currentFrame].getHeight(null)/2, null);
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
