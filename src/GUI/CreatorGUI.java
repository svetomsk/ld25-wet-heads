package GUI;

import items.Item;

import java.awt.Graphics2D;

import main.Game;
import main.Input;
import main.PrintString;
import main.World;
import panels.LoadingPanel;
import panels.SavingPanel;
import panels.ToolsPanel;
import tool.Tool;
import entity.mob.Creator;
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
		Game.setCreator((Creator) mob);
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
    		Game.throwFlowingFrame(new ToolsPanel());
        }
        
/////////////////////////////////////////////////////////////////////////////////////////////////
        World w = mob.getWorld();
        long x = getWorldX();
        long y = getWorldY();
        
        if(lmb != null)
        {
	        if(input.lmbClicked)
	        {
	        	if(input.ctrl.down)
	        	{
	        		lmb.useControlClicked(w, x, y);
	        	}
	        	else
        		{
	        		lmb.useClicked(w, x, y);
        		}
	        }
	        
	        if(input.lmb)
	        {
	        	if(input.ctrl.down)
	        	{
	        		lmb.useControl(w, x, y);
	        	}
	        	else
	        	{
	        		lmb.use(w, x, y);
	        	}
	        }
        }
        //////////
        if(rmb != null)
        {
        	if(input.rmbClicked)
	        {
	        	if(input.ctrl.down)
	        	{
	        		rmb.useControlClicked(w, x, y);
	        	}
	        	else
        		{
	        		rmb.useClicked(w, x, y);
        		}
	        }
        	
	        if(input.rmb)
	        {
	        	if(input.ctrl.down)
	        	{
	        		rmb.useControl(w, x, y);
	        	}
	        	else
	        	{
	        		rmb.use(w, x, y);
	        	}
	        }
        }
        //////////
        if(wheel != null)
        {
        	if(input.wheelClicked)
	        {
	        	if(input.ctrl.down)
	        	{
	        		wheel.useControlClicked(w, x, y);
	        	}
	        	else
        		{
	        		wheel.useClicked(w, x, y);
        		}
	        }
	        if(input.wheel)
	        {
	        	if(input.ctrl.down)
	        	{
	        		wheel.useControl(w, x, y);
	        	}
	        	else
	        	{
	        		wheel.use(w, x, y);
	        	}
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
        if(input.load.typed)
        {
        	Game.throwFlowingFrame(new LoadingPanel(this));
        }
        if(input.test.typed)
        {
        	Game.save("buffer.dat");
        	Game.load("buffer.dat");
        	
        }
//        if(input.pause.typed)
//        {
//           stepState = !stepState;
//        }
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
