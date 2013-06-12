package main;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Pictures 
{
    public static Image roll[] = loadAndCut("resources/roll.png", 6, 256);
    public static Image creator[] = loadAndCut("resources/creator.png", 6, 256);
    public static Image chest[] = loadAndCut("resources/chest.png", 6, 32);
    
    public static Image damageMignon[] = loadAndCut("resources/damageMignon.png", 6, 64);
    public static Image jumpMignon[] = loadAndCut("resources/jumpMignon.png", 6, 64);
    public static Image lightMignon[] = loadAndCut("resources/lightMignon.png", 6, 64);
    public static Image darkMignon[] = loadAndCut("resources/darkMignon.png", 6, 64);
    
    public static Image blood;
    public static Image pause;
    
    public Pictures()
    {
        try 
        {
            pause = ImageIO.read(new File("resources/pause.png"));
            blood = ImageIO.read(new File("resources/blood.png"));
            
            Canvas s = new Canvas();
            AreaAveragingScaleFilter aasf = new AreaAveragingScaleFilter((int)128, (int) 128);
            
            aasf = new AreaAveragingScaleFilter(12, 12);
            blood= s.createImage(new FilteredImageSource(blood.getSource(), aasf));
        } catch (IOException ex) {
            Logger.getLogger(Pictures.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}