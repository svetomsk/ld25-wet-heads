package GUI;

import items.Item;

import java.awt.Graphics2D;

import main.Game;
import main.Input;
import entity.mob.Mob;

public class CreatorGUI extends GUI
{
//	private Item leftHand;
	
	public CreatorGUI(Mob mob, Input input) 
	{
		super(mob, input);
		this.input = input;
		stepState = false;
//		leftHand = new SwordItem(mob);
	}	
	@Override
	public void tick() {}
	
	public void tickGlobal()
	{	
		if(!stepState)
		{
			mob.tick();
		}
		
        //walk
        if(input.right.down) mob.onRight();
        if(input.left.down) mob.onLeft();
        if(input.up.down) mob.onUp();
        if(input.down.down) mob.onDown();
        
        //TODO "choose tool" menu (space)
        
        if(input.space.typed)
        {
    		Game.throwFlowingFrame(Game.createToolsPanel());
        }
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
        if(input.pause.typed)
        {
           stepState = !stepState;
        }
	}
	@Override
	public boolean tryGet(Item item) 
	{
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
	
	public int getX()
	{
		return input.x;
	}
	public int getY()
	{
		return input.y;
	}
}
