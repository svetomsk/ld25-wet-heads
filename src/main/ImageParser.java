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
    private static final int COLOR_GREEN = -14503604;
    private static final int COLOR_BROWN = -4621737;
    private static final int COLOR_GRAY = -8421505;
    private static final int COLOR_LIGHT_GRAY = -3947581;
    private static final int COLOR_PURPLE = -6075996;
    private static final int COLOR_RED = -1237980;
    
    private static byte [][] mas;
    
    private static ArrayList<Integer> coords;
    
    public static byte[][] parse(String name)
    {
    	coords = new ArrayList<Integer>();
        try {
            image = ImageIO.read(new File(name));
        } catch (IOException ex) {System.out.println(ex);}
        
        int width = image.getWidth();
        int height = image.getHeight();
        mas = new byte[width][height];
        for(int i = 0; i < height; i++)
        {
            for(int g = 0; g < width; g++)
            {
            	int rgb = image.getRGB(g, i);
                if(rgb == COLOR_GREEN) // grass
                {
                	mas[g][i] = 1;
                }
                else if(rgb == COLOR_BROWN) // dirt
                {
                	mas[g][i] = 2;
                }
                else if(rgb == COLOR_GRAY) // rock
                {
                	mas[g][i] = 3;
                }
                else if(rgb == COLOR_RED) // lava
                {                	
                	mas[g][i] = 4;
                }
//                else if(rgb == COLOR_PURPLE)
//                {
//                	mas[g][i] = 5;
//                }
                else if(rgb == COLOR_LIGHT_GRAY) // ghost_rock
                {
                	mas[g][i] = 6;
                }
                
                else if(rgb == COLOR_PURPLE) // character
                {
                	mas[g][i] = 127;
                }
                else if(rgb == COLOR_YELLOW) // zombie
                {
                	mas[g][i] = 126;
                }
                
                else if(rgb == COLOR_BLACK) // chest
                {
                	mas[g][i] = 125;
                }
                else if(rgb == COLOR_PINK) // damage_mignon
                {
                	mas[g][i] = 64;
                }
                else if(rgb != -1)
                {
                	mas[g][i] = -1;
                	System.out.println(""+rgb);
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
