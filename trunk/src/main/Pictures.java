package main;


import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import main.World;



public class Pictures 
{
    public static Image imagesLtoR [];
    public static Image imagesRtoL [];
    public static Image weps [];
    public static Image field;
    
    public static Image roll[] = loadAndCut("resources/roll.png", 6, 256);
    public static Image damageMignon[] = loadAndCut("resources/damageMignon.png", 6, 64);
    public static Image jumpMignon[] = loadAndCut("resources/jumpMignon.png", 6, 64);
    public static Image lightMignon[] = loadAndCut("resources/lightMignon.png", 6, 64);
    public static Image darkMignon[] = loadAndCut("resources/darkMignon.png", 6, 64);
    public static Image chest[] = loadAndCut("resources/chest.png", 6, 32);
    
    public static Image eye_left;
    public static Image eye_right;
    
    public static Image spark;
    
    public Pictures()
    {
        imagesLtoR = new Image[9];
        imagesRtoL = new Image[9];
        weps = new Image[5];
        try 
        {
            weps[0] = ImageIO.read(new File("resources/sword.png")); 
            field = ImageIO.read(new File("resources/field.png"));
            
            eye_left = ImageIO.read(new File("resources/eye_left.png"));
            eye_right = ImageIO.read(new File("resources/eye_right.png"));
            spark = ImageIO.read(new File("resources/spark.png"));
            
            Canvas s = new Canvas();
            
            AreaAveragingScaleFilter aasf = new AreaAveragingScaleFilter((int)128, (int) 128);
            field = s.createImage(new FilteredImageSource(field.getSource(), aasf));      

//            aasf = new AreaAveragingScaleFilter(64, 64);
//            roll = s.createImage(new FilteredImageSource(roll.getSource(), aasf));

            aasf = new AreaAveragingScaleFilter(64, 64);
            eye_left = s.createImage(new FilteredImageSource(eye_left.getSource(), aasf));
            aasf = new AreaAveragingScaleFilter(64, 64);
            eye_right = s.createImage(new FilteredImageSource(eye_right.getSource(), aasf));

            aasf = new AreaAveragingScaleFilter(6, 6);
            spark = s.createImage(new FilteredImageSource(spark.getSource(), aasf));
            
            aasf = new AreaAveragingScaleFilter(30, 100);
            weps[0] = s.createImage(new FilteredImageSource(weps[0].getSource(), aasf));
            
        } catch (IOException ex) {
            Logger.getLogger(Pictures.class.getName()).log(Level.SEVERE, null, ex);
        }
//        createLtoRSprites();
//        createRtoLSprites();
        
    }
    public static Image[] loadAndCut(String name, int sw, int scale) 
    {
    	Image buf = null;
    	try {
			buf = ImageIO.read(new File(name));
		} catch (IOException e) {}
    	
    	Image[] res = new Image[sw];
    	Canvas s = new Canvas();
    	
    	for(int q=0;q<sw;q++)
    	{
    		CropImageFilter filter = new CropImageFilter(q*buf.getWidth(null)/sw, 0, buf.getWidth(null)/sw, buf.getHeight(null));
    		res[q] = s.createImage(new FilteredImageSource(buf.getSource(), filter));
    		
    		AreaAveragingScaleFilter aasf = new AreaAveragingScaleFilter(scale, scale);
    		res[q] = s.createImage(new FilteredImageSource(res[q].getSource(), aasf));
    	}
    	return res;
	}
    
//    private void createLtoRSprites()
//    {
//        Canvas g = new Canvas();
//        AreaAveragingScaleFilter filter = new AreaAveragingScaleFilter(World.mobs.get(0).width, World.mobs.get(0).height);
//        for(int i = 0; i < 7; i++)
//        {
//            try 
//            {
//                imagesLtoR[i] = ImageIO.read(new File("resources/man_" + i + ".png"));
//            } catch (IOException ex) {
//                Logger.getLogger(Pictures.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            imagesLtoR[i] = g.createImage(new FilteredImageSource(imagesLtoR[i].getSource(), filter));
//        }
//    }
//    
//    private void createRtoLSprites()
//    {
//        Canvas g = new Canvas();
//        AreaAveragingScaleFilter filter = new AreaAveragingScaleFilter(World.mobs.get(0).width, World.mobs.get(0).height);
//        for(int i = 0; i < 7; i++)
//        {
//            try {
//                imagesRtoL[i] = ImageIO.read(new File("resources/nam_" + i + ".png"));
//            } catch (IOException ex) {
//                Logger.getLogger(Pictures.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            imagesRtoL[i] = g.createImage(new FilteredImageSource(imagesRtoL[i].getSource(), filter));
//        }
//    }
}