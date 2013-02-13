package main.saving;

import items.seeds.DamageMignonSeed;
import items.seeds.DarkMignonSeed;
import items.seeds.JumpMignonSeed;
import items.seeds.LightMignonSeed;

import java.util.HashMap;

import entity.Chest;
import entity.End;
import entity.mob.Angel;
import entity.mob.ArchAngel;
import entity.mob.Butterfly;
import entity.mob.Character;
import entity.mob.mignons.DamageMignon;
import entity.mob.mignons.DarkMignon;
import entity.mob.mignons.JumpMignon;
import entity.mob.mignons.LightMignon;

public class IDManager
{

	private static HashMap<Integer, Class> classes = new HashMap()
	{
		{
			put(1, Character.class);
			put(2, Angel.class);
			put(3, ArchAngel.class);
			put(4, Butterfly.class);
			
			put(65, Chest.class);
			put(66, End.class);

			put(129, DamageMignon.class);
			put(130, DarkMignon.class);
			put(131, JumpMignon.class);
			put(132, LightMignon.class);
			
			put(1025, DamageMignonSeed.class);
			put(1026, DarkMignonSeed.class);
			put(1027, JumpMignonSeed.class);
			put(1028, LightMignonSeed.class);
		}
	};
	
	private static HashMap<Class, Integer> ids = new HashMap()
	{
		{
			put(Character.class, 1);
			put(Angel.class, 2);
			put(ArchAngel.class, 3);
			put(Butterfly.class, 4);
			
			put(Chest.class, 65);
			put(End.class, 66);

			put(DamageMignon.class, 129);
			put(DarkMignon.class, 130);
			put(JumpMignon.class, 131);
			put(LightMignon.class, 132);
			
			put(DamageMignonSeed.class, 1025);
			put(DarkMignonSeed.class, 1026);
			put(JumpMignonSeed.class, 1027);
			put(LightMignonSeed.class, 1028);
		}
	};
	
	public static int getID(Class cl)
	{
		return ids.get(cl);
	}
	public static Class getClass(int id)
	{
		return classes.get(id);
	}
}
