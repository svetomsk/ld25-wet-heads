package particle;

import main.World;
import entity.Entity;

public class Particle extends Entity {

	protected int lifeTime;
	
	protected static int width = 6;
	
	public Particle(long x, long y, World world) {
		super(x, y, world);
	}

	@Override
	public void tick() {
		super.tick();
		lifeTime--;
		if(lifeTime < 0)
		{
			delete();
		}
	}
	
	@Override
	protected void updateVelocity() {}
	
	@Override
	public int getHeight() {
		return width;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
}
