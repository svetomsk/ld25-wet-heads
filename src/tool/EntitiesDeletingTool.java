package tool;

import main.World;

public class EntitiesDeletingTool extends Tool  
{
	public EntitiesDeletingTool()
	{
		
	}
	@Override
	public void use(World world, long x, long y)
	{
		world.deleteEntities(x, y);
	}
	@Override
	public String getName()
	{
		return "Entities deleting";
	}
}
