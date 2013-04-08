package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.saving.IDManager;
import block.Dirt;
import block.Lava;
import block.Rock;
import block.decor.Background;
import block.decor.Ghost_Rock;
import block.decor.Grass;
import block.decor.Wood;
import entity.Chest;
import entity.End;
import entity.Entity;
import entity.mob.Angel;
import entity.mob.ArchAngel;
import entity.mob.Butterfly;
import entity.mob.Character;

public class ImageParser
{
	private static BufferedImage image;

	private static final int COLOR_BLACK = -16777216;
	private static final int COLOR_YELLOW = -3584;
	private static final int COLOR_ORANGE = -32985;
	private static final int COLOR_PINK = -20791;
	private static final int COLOR_LIGHT_YELLOW = -1055568;
	private static final int COLOR_GREEN = -14503604;
	private static final int COLOR_BROWN = -4621737;
	private static final int COLOR_GRAY = -8421505;
	private static final int COLOR_LIGHT_GRAY = -3947581;
	private static final int COLOR_PURPLE = -6075996;
	private static final int COLOR_RED = -1237980;
	private static final int COLOR_BLUE = -6694422;
	private static final int COLOR_BACKGROUND = -10398145;
	private static final int COLOR_WOOD = -6521554;
	private static final int COLOR_END = -3620889;

	private static byte[][] mas;

//	private static ArrayList<Entity> res;

	public static byte[][] parseBlocks(String filename)
	{		
//		res = new ArrayList<Entity>();
		try
		{
			image = ImageIO.read(new File(filename));
		} catch (IOException ex)
		{
			System.out.println(ex);
		}

		int width = image.getWidth();
		int height = image.getHeight();
		mas = new byte[width][height];
		for (int i = 0; i < height; i++)
		{
			for (int g = 0; g < width; g++)
			{
				int rgb = image.getRGB(g, i);
				if (rgb == COLOR_GREEN) // grass
				{
//					mas[g][i] = 1;
					mas[g][i] = IDManager.getBlockID(Grass.class);
				} else if (rgb == COLOR_BROWN) // dirt
				{
//					mas[g][i] = 2;
					mas[g][i] = IDManager.getBlockID(Dirt.class);
				} else if (rgb == COLOR_GRAY) // rock
				{
//					mas[g][i] = 3;
					mas[g][i] = IDManager.getBlockID(Rock.class);
				} else if (rgb == COLOR_RED) // lava
				{
//					mas[g][i] = 4;
					mas[g][i] = IDManager.getBlockID(Lava.class);
				}
//                else if(rgb == COLOR_PURPLE)
//                {
//                	mas[g][i] = 5;
//                }
				else if (rgb == COLOR_LIGHT_GRAY) // ghost_rock
				{
//					mas[g][i] = 6;
					mas[g][i] = IDManager.getBlockID(Ghost_Rock.class);
				} else if (rgb == COLOR_BACKGROUND) // background
				{
//					mas[g][i] = 7;
					mas[g][i] = IDManager.getBlockID(Background.class);
				} else if (rgb == COLOR_WOOD) // wood
				{
//					mas[g][i] = 8;
					mas[g][i] = IDManager.getBlockID(Wood.class);
				}

				else if (rgb == COLOR_PURPLE) // character
				{
					mas[g][i] = 127;
				} else if (rgb == COLOR_YELLOW) // butterfly
				{
					mas[g][i] = 126;
				}

				else if (rgb == COLOR_BLACK) // chest
				{
					mas[g][i] = 125;
				} else if (rgb == COLOR_ORANGE) // archangel
				{
					mas[g][i] = 124;
				} else if (rgb == COLOR_BLUE) // zombie
				{
					mas[g][i] = 123;
				} else if (rgb == COLOR_END) // end
				{
					mas[g][i] = 122;
				}

				else if (rgb == COLOR_PINK) // damageMignon
				{
					mas[g][i] = 64;
				} else if (rgb == COLOR_LIGHT_YELLOW) // jumpMignon
				{
					mas[g][i] = 65;
				}

				else if (rgb != -1)
				{
					mas[g][i] = -1;
					System.out.println("" + rgb);
				}
			}
		}
		return mas;
	}
	
	public static byte[][] getArr()
	{
		return mas;
	}

//	public static ArrayList<Entity> parseEntities(String filename, World world)
//	{
//		ArrayList<Entity> res = new ArrayList<Entity>();
//		try
//		{
//			image = ImageIO.read(new File(filename));
//		} catch (IOException ex)
//		{
//			System.out.println(ex);
//		}
//
//		int width = image.getWidth();
//		int height = image.getHeight();
//		for (int i = 0; i < height; i++)
//		{
//			for (int g = 0; g < width; g++)
//			{
//				int rgb = image.getRGB(g, i);
//				
//				int x = i*world.BLOCK_SIZE;
//				int y = g*world.BLOCK_SIZE;
//				
//				else if(b == 125)
//				{
//					chest(arr, q, w, (Chest) new Chest().init(x-8, y-world.BLOCK_SIZE-16, world));
//				}
//				else if(b == 124)
//				{
//					new ArchAngel().init(x, y, world);
//				}
//				else if(b == 123)
//				{
//					Angel a = new Angel();
//					a.init(x, y, world);
//				}
//				else if(b == 122)
//				{
//					new End().init(x, y, world);
//				}
//				
//				if (rgb == COLOR_PURPLE) // character
//				{
//					new Character().init(x, y, world);
//					continue;
//				} else if (rgb == COLOR_YELLOW) // butterfly
//				{
//					new Butterfly().init(x, y, world);
//					continue;
//				}
//
//				else if (rgb == COLOR_BLACK) // chest
//				{
//					chest(arr, q, w, (Chest) new Chest().init(x-8, y-world.BLOCK_SIZE-16, world));
//				} else if (rgb == COLOR_ORANGE) // archangel
//				{
//					mas[g][i] = 124;
//				} else if (rgb == COLOR_BLUE) // zombie
//				{
//					mas[g][i] = 123;
//				} else if (rgb == COLOR_END) // end
//				{
//					mas[g][i] = 122;
//				}
//
//				else if (rgb == COLOR_PINK) // damageMignon
//				{
//					mas[g][i] = 64;
//				} else if (rgb == COLOR_LIGHT_YELLOW) // jumpMignon
//				{
//					mas[g][i] = 65;
//				}
//
//				else if (rgb != -1)
//				{
//					mas[g][i] = -1;
//					System.out.println("" + rgb);
//				}
//			}
//		}
//		return mas;
//	}
	
//	public static ArrayList<Integer> coords()
//	{
//		return coords;
//	}
}
