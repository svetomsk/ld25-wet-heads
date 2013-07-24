package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.saving.IDManager;
import block.Block;

import particle.Particle;
import entity.Entity;
import entity.mob.Character;

public class World
{
	public final int BLOCK_SIZE = 16;
	public final int GRAVITY = 1;
	public static final double VX_SLOWLY = 0.9;

	public boolean stepState = true;

	public static Input input;

	public static int k = 0;

	public ArrayList<Island> islands;
	public ArrayList<Entity> entities;
	public ArrayList<Particle> particles;

	private Character character = null;

	public World()
	{
		clear();

//		createLevel();
		Game.pic = new Pictures();
	}

	public void clear()
	{
		islands = new ArrayList<Island>();
		entities = new ArrayList<Entity>();
		particles = new ArrayList<Particle>();
	}

	public void createLevel()
	{
//		ArrayList<Integer> mobsCoords = ImageParser.coords();
//		character = new Character(mobsCoords.get(0), mobsCoords.get(1));
//		new Island(0, 3000, 0, 0);
		parseInput();

//		Random r = new Random();
//		for(int q=0;q<100;q++)
//		{
//			new Island((int)(5000*r.nextGaussian()), (int)(-7000*r.nextGaussian()), (int)(12*r.nextDouble()), (int)(12*r.nextDouble()));
//		}
	}

	private void parseInput()
	{
		byte[][] arr = ImageParser.parseBlocks("resources/firstIsland.png");
		new Island(0, 0, 0, 0, this, arr);
	}

	public void parseInputForEntities()
	{
		byte[][] arr = ImageParser.getArr();//parseBlocks("resources/firstIsland.png");
		Entity.parse(arr, this);
		findCharacter();
	}

	public void findCharacter()
	{
		for (Entity e : entities)
		{
			if (e instanceof Character) character = (Character) e;
		}
	}
	public Character getCharacter()
	{
		if(character == null)
		{
			findCharacter();
		}
		return character;
	}
	public void removeCharacters()
	{
		for(int q=0;q<entities.size();q++)
		{
			if(entities.get(q).getClass() == Character.class)
			{
				entities.remove(q);
				q--;
			}
		}
		character = null;
	}
	public void findGUI()
	{
		for(Entity e:entities)
		{
			if(e.getClass() == Character.class)
			{
				Game.setGUI( ((Character) e).getGUI() );
				return;
			}
		}
	}

	public void step()
	{
		for (int q = 0; q < entities.size(); q++)
		{
			Entity e = entities.get(q);
			if (e.isDeleted())
			{
				e.onDeath();
				entities.remove(q);
				q--;
				continue;
			}
			e.tick();
		}
		for (int q = 0; q < entities.size(); q++)
		{
			for (int w = q + 1; w < entities.size(); w++)
			{
				if (!entities.get(q).isCollide(entities.get(w)))
					continue;
				entities.get(q).interactOn(entities.get(w));
				entities.get(w).interactOn(entities.get(q));
			}
		}

//		---------------------------------- Particles ------------------------------

		for (int q = 0; q < particles.size(); q++)
		{
			Particle p = particles.get(q);
			if (p.isDeleted())
			{
				p.onDeath();
				particles.remove(q);
				q--;
				continue;
			}
			p.tick();
		}
		for (int q = 0; q < particles.size(); q++)
		{
			for (int w = q + 1; w < particles.size(); w++)
			{
				particles.get(q).interactOn(particles.get(w));
				particles.get(w).interactOn(particles.get(q));
			}
		}

//        for(int q=0;q<islands.size();q++)
//        {
//        	Island island = islands.get(q);
//        	island.tick();
//        }
	}

	public boolean collideIslands(double x, double y)
	{
		boolean c = false;
		for (Island island : islands)
		{
			try
			{
				byte id = island.blocks[(int) ((x - island.getX()) / BLOCK_SIZE)][(int) ((y - island.getY()) / BLOCK_SIZE)];
				Block block = (Block) IDManager.getBlockClass(id).newInstance(); 
				if(block.getCollidable())
					c = true;
			} catch (Exception ex)
			{
			}
		}
		return c;
	}
	public Island getIsland(long x, long y)
	{
		for (Island island : islands)
		{
			if(x < island.getX()) continue;
			if(y < island.getY()) continue;
			if(x > island.getX()+island.blocks.length*BLOCK_SIZE) continue;
			if(y > island.getY()+island.blocks[0].length*BLOCK_SIZE) continue;
			return island;
		}
		return null;
	}
	public Entity getEntity(long x, long y)
	{
		for (Entity entity : entities)
		{
			if(entity.isCollide(x, y)) return entity;
		}
		return null;
	}
	
	public void deleteEntities(long x, long y)
	{
		for(int q=0;q<entities.size();q++)
		{
			if(entities.get(q).isCollide(x, y))
			{
				entities.remove(q);
				q--;
			}
		}
	}

	public Entity getEntityByID(int id)
	{
		for (Entity e : entities)
		{
			if (e.getId() == id)
			{
				return e;
			}
		}
		return null;
	}

//	public static boolean collidePoint(double x, double y, Mob mob)
//	{
//		double xk = mob.x;
//		double yk = mob.y;
//		double w = mob.width;
//		double h = mob.height;
//		return (x>=xk && x<=xk+w && y>=yk && y<=yk+h);
//	}
	public void draw(Graphics2D g)
	{
//            count++;
//            for(int q=Math.max(0, Game.x/BLOCK_SIZE);q<Math.min((Game.x+Game.WIDTH*Game.SIZE)/BLOCK_SIZE+1, level.length);q++)
//            {
//                    for(int w=Math.max(0, Game.y/BLOCK_SIZE);w<Math.min((Game.y+Game.HEIGHT*Game.SIZE)/BLOCK_SIZE+1, level[0].length);w++)
//                    {
//                        if(level[q][w] == 0) continue;
//                        
//                        if(level[q][w] == 1)g.setColor(Color.BLACK);
//                        g.fillRect(q*BLOCK_SIZE - Game.x, w*BLOCK_SIZE - Game.y, BLOCK_SIZE, BLOCK_SIZE);
//                    }
//            }

		g.setColor(Color.BLACK);
		for (Island i : islands)
		{
			i.draw(g);
		}
		for (Entity e : entities)
		{
			e.draw(g);
		}
		for (Particle p : particles)
		{
			p.draw(g);
		}
		g.setColor(Color.BLUE);
		g.fillOval(-10 - Game.x, -10 - Game.y, 20, 20);
	}
}
