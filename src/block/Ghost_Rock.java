package block;

public class Ghost_Rock extends Rock{
	
	public static final double elasticity = 1;
	@Override
	public void tick() {
		super.tick();
		delete();
	}
	@Override
	public double getElasticity()
	{
		return elasticity;
	}
}
