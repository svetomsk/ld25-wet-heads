package tool;

import javax.swing.JLabel;

import main.World;

public class BlocksTool extends Tool 
{
	private byte bid;
	
	public BlocksTool()
	{
		super("BlockTool");
	}
	public void setFiller(byte bid)
	{
		this.bid = bid;
	}
	public void use(World world, long x, long y)
	{
		
	}
}
