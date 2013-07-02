package tool;

import main.saving.IDManager;

public class ClassName
{
	public static ClassName[] blocks = getBlockClasses();
	public static ClassName[] entities = getEntitiesClasses();
	
	private Class cl;
	
	public ClassName(Class cl) 
	{
		
		this.cl = cl;
	}
	public Class getClassN()
	{
		return cl;
	}
	public String getName()
	{
		return cl.getSimpleName();
	}
	
	private static ClassName[] getBlockClasses()
	{
		Class[] bcl = IDManager.getBlockClasses();
		ClassName[] res = new ClassName[bcl.length];
		for(int q=0;q<bcl.length;q++)
		{
			res[q] = new ClassName(bcl[q]);
		}
		return res;
	}
	private static ClassName[] getEntitiesClasses()
	{
		Class[] ecl = IDManager.getEntitiesClasses();
		ClassName[] res = new ClassName[ecl.length];
		for(int q=0;q<ecl.length;q++)
		{
			res[q] = new ClassName(ecl[q]);
		}
		return res;
	}
}
