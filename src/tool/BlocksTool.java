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
		if(bclass == null)
		{
			setFiller((byte)0);
			return;
		}
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
		
		int dx = world.BLOCK_SIZE/2;
				
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
					isl.expand(q, w);
					return;
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
