package GUI;

import items.Item;

import java.awt.Graphics2D;

import main.Game;
import main.Input;
import entity.mob.Mob;
import entity.mob.controllers.Controller;

public class GUI extends Controller
{
	private Input input;
	public boolean stepState = true;
	private Item leftHand;
	
	public GUI(Mob mob, Input input) {
		super(mob);
		this.input = input;
		
//		leftHand = new SwordItem(mob);
	}	
	@Override
	public void tick()
	{
        //walk
        if(input.right.down) mob.onRight();
        else if(input.left.down) mob.onLeft();
        
        //jump
        if(input.up.down) mob.onUp();
        
//        //weapon
//        if(input.b0Clicked)
//        {
//        	if(leftHand != null)
//        		leftHand.use(input.x+Game.x, input.y+Game.y);
//        }
        
        //throw item
        if(input.q.typed)
        {
        	if(leftHand!=null)
        	{
		    	leftHand.throwItem();
		    	leftHand = null;
        	}
        }
        
    	//----------------------------------------------------------
        
		if(input.b2)
		{
//			new DamageMignonSeed((input.x+Game.x), (input.y+Game.y), mob.getWorld());
//			new JumpMignon((input.x+Game.x), (input.y+Game.y), mob.getWorld(), mob);
//			new DamageMignon((input.x+Game.x), (input.y+Game.y), mob.getWorld(), mob);
//			new Zombie((input.x+Game.x), (input.y+Game.y), mob.getWorld());
//			new SwordItem((input.x+Game.x), (input.y+Game.y), mob.getWorld());
		}
		if(input.b0Clicked)
		{
			floakState((input.x+Game.x), (input.y+Game.y));
		}
		if(input.b1)
		{
			floakFollow((input.x+Game.x), (input.y+Game.y));
		}
		if(input.space.typed)
		{
			floakReturn();
		}
	}
	
	public void tickGlobal()
	{	
//		if(input.restart.typed)
//		{
//			mob.getWorld().clear();
//			mob.getWorld().createLevel();
//		}
        if(input.quit.typed)
        {
            Game.addMenu();
        }
        if(input.quicksave.typed)
        {
        	Game.quickSave();
        }
        if(input.quickload.typed)
        {
        	Game.quickLoad();
        }
        
        if(mob.isDeleted())
        {
//        	Game.addMenu();
        	Game.showDeath();
        }
        if(input.pause.typed)
        {
           stepState = !stepState;
        }
//        if(input.b1)
//        {
//        		level[(Game.x + input.x) / BLOCK_SIZE][(Game.y + input.y) / BLOCK_SIZE] = 0;    		  	
//        }
	}
	@Override
	public boolean tryGet(Item item) {
//		if(leftHand==null)
//		{
//			leftHand = item;
//			return true;
//		}
		return false;
	}
	public void draw(Graphics2D g)
	{
//		Image value = Pictures.field;        
//        g.drawImage(value, 10, (int) Game.HEIGHT-138, null);
//        
//        if(leftHand!=null)
//	    {
//        	value = Pictures.weps[World.k];	 
//        	int x = 30;
//        	int y = Game.HEIGHT-30;
//	        g.rotate(-Math.PI*(135/180.0), (int)x, (int)y);
//	        g.drawImage(value, (int) x - (int)value.getWidth(null)/2, (int) y, null);
//	        g.rotate(Math.PI*(135/180.0), (int)x, (int)y);
//        }
	}
	
//	private void knock(long tx, long ty)
//	{
//		for(int q=0;q<fl.size();q++)
//		{
//			fl.get(q).knock(tx, ty);
//		}
//	}
//	
//	(Mignon)
//	public void knock(long x, long y)
//	{
//		controller.knock(x, y);
//	}
//	
//	(Controller)
//	{
//		isKnock
//		isState
//		isSpin
//		
//		knock(long tx, long ty)
//		{
//			isKnock = true;
//			x = tx
//					y = ty
//					
//					
//
//		}
//		
//		tick()
//		{
//			if(isKnock)
//			{
//				mob.follow(x, y);
//			}
//			if(isSpin)
//			{
//				mob.spin(x, y);
//			}
//			isS
//		}
//	}
	
	
	public int getX()
	{
		return input.x;
	}
	
	public int getY()
	{
		return input.y;
	}
}
