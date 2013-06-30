package tool;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import main.saving.IDManager;

public class ClassName
{
	public static ClassName[] blocks = getBlockClasses();
	
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
}
