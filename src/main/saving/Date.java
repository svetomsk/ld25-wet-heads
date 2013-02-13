package main.saving;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import main.World;
import entity.Entity;

public class Date
{
	public static void save(World world, String name) throws IOException
	{
		DataOutputStream w = new DataOutputStream(new FileOutputStream(new File(name + ".dat")));

		for (int q = 0; q < world.entities.size(); q++)
		{
			Entity e = world.entities.get(q);
			w.writeInt(IDManager.getID(e.getClass()));
//			System.out.println(e.getId()+" "+e.getClass().getName());
			e.save(w);
		}
		w.writeInt(-1);
		w.close();
	}

	public static World load(String name) throws IOException, ReflectiveOperationException
	{
		World world = new World();
		DataInputStream r = new DataInputStream(new FileInputStream(new File(name + ".dat")));

		while (true)
		{
			int id = r.readInt();
			if (id == -1)
			{
				break;
			}
			Class[] params = new Class[] { DataInputStream.class, World.class };
			Method m = IDManager.getClass(id).getMethod("load", params);
			m.invoke(IDManager.getClass(id).newInstance(), r, world);
		}
		return world;
	}

}