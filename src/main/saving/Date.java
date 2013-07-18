package main.saving;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import main.Island;
import main.PrintString;
import main.World;
import entity.Entity;
import entity.mob.Creator;

public class Date
{
	public static void save(World world, String name) throws IOException
	{
		DataOutputStream w = null;
		try
		{
			w = new DataOutputStream(new FileOutputStream(new File(name)));
		}
		catch(FileNotFoundException ex)
		{
			PrintString.printError("Incorrect filename!");
			return;
		}

		for (int q = 0; q < world.entities.size(); q++)
		{
			Entity e = world.entities.get(q);
			
			if(e.getClass().equals(Creator.class)) continue;
			
			w.writeInt(IDManager.getID(e.getClass()));
			
//			System.out.println(e.getId()+" "+e.getClass().getName());
			
			e.save(w);
		}
		w.writeInt(-1);
		
		for(int q=0;q<world.islands.size();q++)
		{
			Island i = world.islands.get(q);
			
			w.writeLong(i.getX());
			w.writeLong(i.getY());
			
			w.writeDouble(i.getVX());
			w.writeDouble(i.getVY());
			
			w.writeInt(i.blocks.length);
			w.writeInt(i.blocks[0].length);
			
			for(int e=0;e<i.blocks.length;e++)
			{
				w.write(i.blocks[e]);
			}
		}
		w.writeLong(-1);
		w.close();
	}

	public static World load(String name) throws IOException, ReflectiveOperationException
	{
		World world = new World();
		DataInputStream is = new DataInputStream(new FileInputStream(new File(name)));

		while (true)
		{
			int id = is.readInt();
			if (id == -1)
			{
				break;
			}
			Class[] params = new Class[] { DataInputStream.class, World.class };
			Method m = IDManager.getClass(id).getMethod("load", params);
			m.invoke(IDManager.getClass(id).newInstance(), is, world);
		}
		
		while(true)
		{
			long x = is.readLong();
			if(x == -1)
			{
				break;
			}
			long y = is.readLong();
			double vx = is.readDouble();
			double vy = is.readDouble();
			
			int width = is.readInt();
			int height = is.readInt();
			
			byte[][] arr = new byte[width][height];
			for(int q=0;q<width;q++)
			{
				is.read(arr[q]);
			}
			new Island(x, y, vx, vy, world, arr);
		}
		return world;
	}

}