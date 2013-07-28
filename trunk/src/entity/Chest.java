package entity;

import items.Item;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.Game;
import main.Pictures;
import main.World;
import entity.mob.Character;
import entity.mob.Mob;
import entity.mob.controllers.Controller;
import entity.mob.mignons.Mignon;

public class Chest extends Mob{

	private ArrayList<Item> items = new ArrayList<Item>();
	
	@Override
	public void finalInit(World world)
	{
		super.finalInit(world);
		control = new Controller(this); 
	}
	@Override
	public void save(DataOutputStream os) throws IOException
	{
		super.save(os);
		for(Item i:items)
		{
			os.writeInt(i.getId());
		}
		os.writeInt(-1);
	}
	@Override
	public void load(DataInputStream is, World world) throws IOException
	{
		super.load(is, world);
		while(true)
		{
			int id = is.readInt();
			if(id == -1) break;
			Item i = (Item) world.getEntityByID(id);
			if(i == null) continue;
			addItem(i);
		}
	}
	@Override
	protected void slowly() 
	{
		if(onGround) lvx *= 0.9;
		if(onWall) lvy *= 0.9;
	}
	public void addItem(Item item)
	{
		items.add(item);
		item.setOwner(this);
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
		return 32;
	}
	@Override
	public int getHeight() 
	{
		return 32;
	}
}
