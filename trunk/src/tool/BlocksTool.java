package tool;

import main.Game;
import main.Island;
import main.World;
import main.saving.IDManager;

public class BlocksTool extends Tool 
{
	private byte bid;
	
	public BlocksTool(Class bclass)
	{
		setFiller(bclass);
	}
	public void setFiller(Class bclass)
	{
//		if bclass ? Block throw..
		setFiller(IDManager.getBlockID(bclass));
	}
	public void setFiller(byte bid)
	{
		this.bid = bid;
	}
	@Override
	public void use(World world, long x, long y)
	{
		Island isl = world.findIsland(x, y);
		if(isl == null) return;
		int bx = (int) ((x-isl.getX())/world.BLOCK_SIZE);
		int by = (int) ((y-isl.getY())/world.BLOCK_SIZE);
		int r = (int) (Game.scale*10-8);
		for(int q=bx-r;q<=bx+r;q++)
		{
			for(int w=by-r;w<=by+r;w++)
			{
				double dr = Math.sqrt( (bx-q)*(bx-q)+(by-w)*(by-w) );
				if(dr>r) continue;
				
				try
				{
					isl.blocks[q][w] = bid;
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					
				}
			}
		}
	}
	@Override
	public String getName()
	{
		return "Block placer";
	}
}
