package tool;

import java.lang.reflect.InvocationTargetException;

import main.Game;
import main.PrintString;
import main.World;
import main.saving.IDManager;
import entity.mob.Character;

public class EntitiesTool extends Tool 
{
	private Class eclass;
	
	public EntitiesTool(Class eclass)
	{
		this.eclass = eclass;
	}
	public void setFiller(Class eclass)
	{
//		if eclass ? Entity throw..
		this.eclass = eclass;
	}
	public void setFiller(byte eid)
	{
		setFiller(IDManager.getClass(eid));
	}
	private static final Class[] params = new Class[] { long.class, long.class, World.class };
	@Override
	public void useClicked(World world, long x, long y)
	{
		try
		{
			if(eclass == Character.class)
			{
				world.removeCharacters();
			}
			eclass.getMethod("init", params).invoke(eclass.newInstance(), x, y, world);
			
		} catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String getName()
	{
		return "Entities placer";
	}
}
