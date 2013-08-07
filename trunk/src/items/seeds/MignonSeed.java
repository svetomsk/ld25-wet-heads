package items.seeds;

import items.Item;
import main.World;
import entity.Entity;
import entity.mob.Butterfly;
import entity.mob.Mob;
import entity.mob.mignons.Mignon;

public class MignonSeed extends Item
{
	@Override
	public Entity init(long x, long y, Mob owner, World world)
	{
		isPickable = false;
		return super.init(x, y, owner, world);
	}
	@Override
	protected boolean interactOnMignon(Mignon mignon) 
	{
		return false;
	}
	@Override
	protected boolean interactOnButterfly(Butterfly butterfly) 
	{
		return false;
	}
}
