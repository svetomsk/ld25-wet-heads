package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

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

	private static ArrayList<Integer> coords;

	public static byte[][] parse(String name)
	{
		coords = new ArrayList<Integer>();
		try
		{
			image = ImageIO.read(new File(name));
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
					mas[g][i] = 1;
				} else if (rgb == COLOR_BROWN) // dirt
				{
					mas[g][i] = 2;
				} else if (rgb == COLOR_GRAY) // rock
				{
					mas[g][i] = 3;
				} else if (rgb == COLOR_RED) // lava
				{
					mas[g][i] = 4;
				}
//                else if(rgb == COLOR_PURPLE)
//                {
//                	mas[g][i] = 5;
//                }
				else if (rgb == COLOR_LIGHT_GRAY) // ghost_rock
				{
					mas[g][i] = 6;
				} else if (rgb == COLOR_BACKGROUND) // background
				{
					mas[g][i] = 7;
				} else if (rgb == COLOR_WOOD) // wood
				{
					mas[g][i] = 8;
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

	public static ArrayList<Integer> coords()
	{
		return coords;
	}
}
