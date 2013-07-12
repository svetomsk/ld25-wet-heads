package GUI;

import items.Item;

import java.awt.Graphics2D;

import main.Game;
import main.Input;
import panels.SavingPanel;
import tool.Tool;
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
	
	private Tool lmb;
	private Tool wheel;
	private Tool rmb;
	
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
        
        if(input.space.typed)
        {
    		Game.throwFlowingFrame(Game.createToolsPanel());
        }
        
/////////////////////////////////////////////////////////////////////////////////////////////////
        if(lmb != null)
        {
	        if(input.lmbClicked)
	        {
	        	lmb.useClicked(mob.getWorld(), getWorldX(), getWorldY());
	        }
	        if(input.lmb)
	        {
	        	lmb.use(mob.getWorld(), getWorldX(), getWorldY());
	        }
        }
        if(rmb != null)
        {
	        if(input.rmbClicked)
	        {
	        	rmb.useClicked(mob.getWorld(), getWorldX(), getWorldY());
	        }
	        if(input.rmb)
	        {
	        	rmb.use(mob.getWorld(), getWorldX(), getWorldY());
	        }
        }
        if(wheel != null)
        {
	        if(input.wheelClicked)
	        {
	        	wheel.useClicked(mob.getWorld(), getWorldX(), getWorldY());
	        }
	        if(input.wheel)
	        {
	        	wheel.use(mob.getWorld(), getWorldX(), getWorldY());
	        }
        }
///////////////////////////////////////////////////////////////////////////////////////////////
        
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
        if(input.save.typed)
        {
        	Game.throwFlowingFrame(new SavingPanel(this));
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
	public long getWorldX()
	{
		return (long) (Game.x+Game.scale*input.x);
	}
	public long getWorldY()
	{
		return (long) (Game.y+Game.scale*input.y);
	}
	
	public void setLMB(Tool tool)
	{
		lmb = tool;
	}
	public void setWheel(Tool tool)
	{
		wheel = tool;
	}
	public void setRMB(Tool tool)
	{
		rmb = tool;
	}
}
