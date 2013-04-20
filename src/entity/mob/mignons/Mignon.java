package entity.mob.mignons;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.Game;
import main.World;
import entity.Entity;
import entity.mob.Mob;
import entity.mob.controllers.MignonController;

public class Mignon extends Mob
{

	public static final int RAD_RUN = 10000;
	public static final int RAD_SPIN = 20000;

	private Mob owner;

	protected MignonController control;

	public void init(long x, long y, Mob owner)
	{
		init(x, y, owner.getWorld());
		setOwner(owner);
	}
	public Entity init(long x, long y, double lvx, double lvy, double gvx, double gvy, Mob owner)
	{
		init(x, y, lvx, lvy, gvx, gvy, owner.getWorld());
		setOwner(owner);
		return this;
	}
	
	@Override
	protected void afterBirth()
	{
		super.afterBirth();
		control = new MignonController(this);
		super.control = control;
	}

	@Override
	public void save(DataOutputStream os) throws IOException
	{
		super.save(os);
		os.writeInt(getOwner().getId());
//		System.out.println(getId()+" - "+getOwner().getId());
		control.save(os);
	}

	@Override
	public void load(DataInputStream is, World world) throws IOException
	{
		super.load(is, world);
		int id = is.readInt();
		setOwner((Mob) world.getEntityByID(id));

		control = new MignonController(this);
		control.load(is, world);
		super.control = control;
	}

	protected void setOwner(Mob owner)
	{
		this.owner = owner;
		owner.addMignon(this);
	}

	public void loseControl()
	{
		control.loseOwner();
	}

	public void loseOwner()
	{
		delete();
	}

	@Override
	public void draw(Graphics2D g)
	{
		int drawx = (int) (x - Game.x + getWidth() / 2);
		int drawy = (int) (y - Game.y + getHeight() / 2);

		g.drawImage(img[currentFrame], drawx - img[currentFrame].getWidth(null)
				/ 2, drawy - img[currentFrame].getHeight(null) / 2, null);
	}

	@Override
	protected void updateVelocity()
	{
		lvx *= getSpeed() / (getSpeed() + 1);
		lvy *= getSpeed() / (getSpeed() + 1);
	}

//	------------------------------------------- ENTITIES ------------------------------------------- 

	@Override
	protected boolean collideIslands(boolean verticalWalls)
	{
		return false;
	}

	@Override
	protected void interactOn(byte id){}

	@Override
	protected boolean interactOnMignon(Mignon mignon)
	{
		return false;
	}

//	------------------------------------------- MOTION ------------------------------------------- 

	public boolean state(long tx, long ty)
	{
		if (control.isState())
			return true;
		control.state(tx, ty);
		return false;
	}

	public void comeBack()
	{
		control.comeBack();
	}

	public void goTo(long tx, long ty)
	{
		long r = (tx - x) * (tx - x) + (ty - y) * (ty - y);
		if (r > 250)
		{
			followPoint(tx, ty);
		} else
		{
			slowDown();
		}
	}

	protected void slowDown()
	{
		lvx *= 0.9;
		lvy *= 0.9;
	}

	public void followPoint(long tx, long ty)
	{
		// v = 1
		double angle = Entity.getAngle(tx - x, ty - y) + Math.PI / 2;
		lvx += Math.cos(angle);
		lvy += Math.sin(angle);
	}

	public void runPoint(long tx, long ty)
	{
		// v = 1
		double angle = Entity.getAngle(tx - x, ty - y) + Math.PI / 2;
		lvx -= Math.cos(angle);
		lvy -= Math.sin(angle);
	}

	private void spinRight(long tx, long ty)
	{
		// v = 0.5
		double angle = Entity.getAngle(tx - x, ty - y) + Math.PI / 2;
		lvx += 0.5 * Math.sin(angle);
		lvy += -0.5 * Math.cos(angle);
	}

	private boolean spinRight = true;

	public void spin(long tx, long ty)
	{
		double r = (tx - x) * (tx - x) + (ty - y) * (ty - y);
		// 0 - 200 - 250
		if (r > RAD_SPIN)
			followPoint(tx, ty);
		else if (r > RAD_RUN)
		{
			if (spinRight)
			{
				spinRight(tx, ty);
			}
		} else
			runPoint(tx, ty);
	}

//	------------------------------------------- GETTERS ------------------------------------------- 

	public Mob getOwner()
	{
		return owner;
	}

//
	@Override
	public double getSpeed()
	{
		return 70;
	}

	@Override
	public double getJumpPower()
	{
		return 0;
	}

	@Override
	public int getMaxHP()
	{
		return 10;
	}

	@Override
	public int getDamage()
	{
		return 0;
	}

	@Override
	public int getKnokback()
	{
		return 0;
	}

	@Override
	public double getStrength()
	{
		return 0;
	}

	@Override
	public int getWidth()
	{
		return 16;
	}

	@Override
	public int getHeight()
	{
		return 16;
	}
}
