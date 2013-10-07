package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import main.saving.Date;
import panels.ChooseMapPanel;
import panels.LoadingPanel;
import panels.MenuPanel;
import panels.SavingPanel;
import GUI.GUI;
import entity.mob.Creator;

public class Game extends Canvas implements Runnable
{
    public static int basicWIDTH;
    public static int basicHEIGHT;
    
    public static double scale = 1;
    public static double perfectScale = 1;
    
    public static int WIDTH = (int) (basicHEIGHT*scale);
    public static int HEIGHT = (int) (basicWIDTH*scale);
    
    private static void onScaleChanged()
    {
    	int dx = (int) (WIDTH - basicWIDTH*scale);
    	x += dx/2;
    	int dy = (int) (HEIGHT - basicHEIGHT*scale);
    	y += dy/2;
    	
    	HEIGHT = (int) (basicHEIGHT*scale);
    	WIDTH = (int) (basicWIDTH*scale);
    }
    public static void scale(double value)
    {
    	if ( gui.getClass() == GUI.class && ( ( perfectScale >= 1.2 && value >= 0 ) || ( perfectScale <= 0.8 && value <= 0) ) ) return;
    	
    	perfectScale += value/10;
    	onScaleChanged();
    }
    
    
    public static final int SIZE = 1;
    
    public String frames = "";
    public static int x, y;
    private static final double MOUSE_FACTOR = 0;
    private static final double SCROLL_SPEED = 0.2;
    
    public boolean renderState = false;
    
    private static InputHandler inputHandler;
    private static Input input;
    private static GUI gui;    
    private static Creator creator;    
    private static World world;
    
    public static Pictures pic;
    
    public Game(Dimension size) 
    {        
//        WIDTH = (int) size.getWidth();
//        HEIGHT = (int) size.getHeight();
        setPreferredSize(size);        
//        setMaximumSize(size);
//        setMinimumSize(size);
        x = 0; y = 0;
        
        inputHandler = new InputHandler(this);
    }
    
    public void start()
    {
        renderState = true;
        new Thread(this, "Game thread").start();
    }
    
    public void stop()
    {
        renderState = false;
    }

    private void init(World world)
    {
    	input = inputHandler.update(SIZE);
    	this.world = world;
		this.world.findCharacter();
		
		if(world.getCharacter() == null)
		{
			Creator c = new Creator();
			c.init(-c.getWidth()/2, -c.getHeight()/2, this.world);
		}
		
		x = (int) (getGUI().getMobCX()-WIDTH/2);
		y = (int) (getGUI().getMobCY()-HEIGHT/2);
		
		getGUI().updateInput(input);
		
        requestFocus();
    }
    
    static Clip clip;
    
    private static void doPlay(final String url) {
        try {
            stopPlay();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("resources/music.wav"));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            stopPlay();
            System.err.println(e.getMessage());
        }
    }
     
    private static void stopPlay() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
    
    private static long nextTime;
    @Override
    public void run() 
    {
//    	init();
    	int fps = 60;
    	int maxSkipFrames = 10;
    	
    	int nsPerFrame = 1000000000 / fps;
    	
    	nextTime = System.nanoTime();
    	long lastTimeFrame = System.currentTimeMillis();
    	int loop = 0;
    	int frames = 0;
    	int physFrames = 0;
    	
//    	if(clip == null)
//    	doPlay("asldf");
    	
        while(renderState)
        {
        	loop = 0;
        	while(loop<maxSkipFrames && System.nanoTime()>nextTime+nsPerFrame)
        	{
	            input = inputHandler.update(SIZE);
	            
	            gui.tickGlobal();
	            PrintString.printingTimersTick();
	            if(gui.stepState) world.step();
	            
	            focus();
	        	nextTime += nsPerFrame;
	            loop++;
	            physFrames++;
        	}
        	
        	scale += (perfectScale - scale)/40;
            onScaleChanged();
            
            swap();
            frames++;
            if(System.currentTimeMillis() > lastTimeFrame + 1000)
            {
                    lastTimeFrame+=1000;
                    this.frames = "PFPS = "+physFrames + "\n FPS = " + frames;
                    frames=0;
                    physFrames = 0;
            }
        }
    }
    
    private void focus()
    {
    	double h = HEIGHT / 2;
    	double w = WIDTH  / 2;
    	
    	double vx = getGUI().getMobCX() - w; 
//    			world.character.getX()+world.character.getWidth()/2 - w;
    	double vy = getGUI().getMobCY() - h; 
//    			world.character.getY()+world.character.getHeight()/2;
    	
//    	if(Math.abs(world.character.vx)>100)
//    	{
//    		vx -= Math.signum(world.character.vx)>0 ? w:2*w;
//    	} else vx -= 1.5 * w;
    	
    	double mx = inputHandler.mx - w;
    	double my = inputHandler.my - h;
    	vx += mx * MOUSE_FACTOR;
    	vy += my * MOUSE_FACTOR;    	
    	vx -= x;
    	vy -= y;
    	x += vx * SCROLL_SPEED;
    	y += vy * SCROLL_SPEED;
    }
    public void swap()
    {
        if(renderState)
        {
            BufferStrategy bs = getBufferStrategy();
            if(bs == null)
            {
                createBufferStrategy(2);
                return;            
            }
            Graphics2D g =(Graphics2D) bs.getDrawGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.scale(1/scale, 1/scale);
            
            world.draw(g);
            
//            g.setColor(new Color((float)(1-world.character.hp), (float)world.character.hp, (float)0.0));
//            g.fillRect(32, 32, (int)(world.character.hp * WIDTH/3), 8);
            
            g.scale(scale, scale);
            
            gui.draw(g);
            PrintString.drawPrinting(g);
            
            g.setColor(Color.BLACK);
            g.drawString(frames, basicWIDTH - frames.length() * 12, 12);

            g.dispose();
            bs.show();
        }
    }
    
    public static void setGUI(GUI gui)
    {
    	Game.gui = gui;
    }
    public static void setCreator(Creator creator)
    {
    	Game.creator = creator;
    }
    public static Input getInput()
    {
    	return input;
    }
    public static void quickSave()
	{
		save("quicksave.dat");
	}
    public static void quickLoad()
	{
		load("quicksave.dat");
	}
    public static void save(String filename)
	{
		try
		{
			Date.save(world, filename);
			PrintString.println("Saved successfully");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void loadc(String filename)
    {
    	load(filename);
    	creator.replace(creator.getX(), creator.getY(), world);
    }
    public static void load(String filename)
	{
		try
		{
			world = Date.load(filename);
			setGUI(world.getCharacter().getGUI());
			gui.stepState = false;
			nextTime = System.nanoTime();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectiveOperationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static JFrame frame, flowingFrame, flowingFrame2;
    public static int FFRAME1 = 1, FFRAME2 = 2; 
    private static JPanel menu, main, death, end;
    private static Game gameComponents;
    
    public static void removeFlowingFrame()
    {
    	removeFlowingFrame(FFRAME1);
    }
    public static void removeFlowingFrame(int param)
    {
    	if(param == FFRAME1)
    	{
    		if(flowingFrame != null)
    		flowingFrame.dispose();
    	}
    	else if(param == FFRAME2)
    	{
    		if(flowingFrame2 != null)
    		flowingFrame2.dispose();
    	}
    }
    public static void throwFlowingFrame(JPanel content)
    {
    	throwFlowingFrame(content, basicWIDTH/4, basicHEIGHT/4, basicWIDTH/2, basicHEIGHT/2, FFRAME1);
    }
    public static void throwFlowingFrame(JPanel content, int param)
    {
    	throwFlowingFrame(content, basicWIDTH/4, basicHEIGHT/4, basicWIDTH/2, basicHEIGHT/2, param);
    }
    public static void throwFlowingFrame(JPanel content, int x, int y, int w, int h, int param)
    {
    	try{ removeFlowingFrame(param); } catch(NullPointerException ex){}
    	
    	JFrame flowingFrame = new JFrame();
    	if(content != null) flowingFrame.add(content);
    	flowingFrame.setAlwaysOnTop(true);
    	flowingFrame.setUndecorated(true);
    	
    	if(w == -1 || h == -1)
    	{
    		flowingFrame.setLocation(x, y);
    		flowingFrame.pack();
    	}
    	else
    	{
    		flowingFrame.setBounds(x, y, w, h);
    	}
    	
    	flowingFrame.setVisible(true);
    	if(inputHandler != null) inputHandler.free();
    	
    	if(param == FFRAME1)
    	{
    		Game.flowingFrame = flowingFrame;
    	}
    	else if(param == FFRAME2)
    	{
    		Game.flowingFrame2 = flowingFrame;
    	}
    }
    private static void createMenuPanel()
    {
        menu = new JPanel();
        menu.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        int bheight = 70;
        int bwidth = 3*WIDTH/5;
        int range = (Toolkit.getDefaultToolkit().getScreenSize().height - 5 * bheight)/6;
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 100, range));
        JButton contin = new JButton("Continue");
        JButton start = new JButton("Start");
        JButton about = new JButton("About");
        JButton editor = new JButton("Editor");
        JButton exit = new JButton("Exit");
        
        Dimension button = new Dimension(bwidth, bheight);
        
        contin.setPreferredSize(button);
        contin.setMinimumSize(button);
        contin.setMaximumSize(button);
        contin.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        contin.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        
        contin.setEnabled(new File("autosave.dat").exists());
        
        start.setPreferredSize(button);
        start.setMinimumSize(button);
        start.setMaximumSize(button);
        start.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        start.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        
        editor.setPreferredSize(button);
        editor.setMinimumSize(button);
        editor.setMaximumSize(button);
        editor.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        editor.setAlignmentY(JComponent.CENTER_ALIGNMENT);
                
        about.setPreferredSize(button);
        about.setMinimumSize(button);
        about.setMaximumSize(button);
        about.setAlignmentX(JComponent.CENTER_ALIGNMENT);        
        about.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        
        exit.setPreferredSize(button);
        exit.setMinimumSize(button);
        exit.setMaximumSize(button);
        exit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        exit.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        
        contin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
         	   startGame("autosave.dat");
            }
         });
        start.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent ae)
           {
        	   startGame("resources/maps/level_1.dat");
           }
        });
        editor.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent arg0)
        	{
                throwFlowingFrame(new ChooseMapPanel());
        	}
        });
        about.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae)
           {
               // smth about game
           }
        });
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                System.exit(0);
            }
        });
        menu.add(contin);
        menu.add(start);
        menu.add(editor);
        menu.add(about);
        menu.add(exit);
    }
    
    public static void gameMenu()
    {
//    	Dimension size = new Dimension(basicWIDTH/3, basicHEIGHT/3);
    	throwFlowingFrame(null, FFRAME2);
    	
    	Dimension dim = new Dimension((int)flowingFrame2.getBounds().getWidth(),
    			(int)flowingFrame2.getBounds().getHeight());
    	
    	flowingFrame2.add(new MenuPanel(getGUI(), dim));
    	flowingFrame2.setVisible(true);
        getGUI().stepState = false;
    }
    public static void saveMenu()
    {
    	throwFlowingFrame(new SavingPanel(getGUI()));
    	getGUI().stepState = false;
    }
    public static void loadMenu()
    {    	
    	Game.throwFlowingFrame(new LoadingPanel(getGUI()));
    	getGUI().stepState = false;
    }
    public static void toMainMenu()
    {
    	removeFlowingFrame(FFRAME1);
    	removeFlowingFrame(FFRAME2);
        if(frame.getComponents().length > 0)
        {
            frame.remove(gameComponents);
            frame.remove(main);       
            frame.remove(death);
            gameComponents.stop();
            
            if(creator == null)
            {
            	save("autosave.dat");
            }
            creator = null;
            Game.x = 0;
            Game.y = 0;
            Game.scale = 1;
            Game.perfectScale = 1;
        }
        createMenuPanel();
        frame.add(menu); 
        frame.update(frame.getGraphics());
        frame.setVisible(true);
    }
    
    private static void createMainPanel()
    {
        main = new JPanel();
        main.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        main.add(gameComponents);
    }
    
    private static void createDeathPanel()
    {
        death = new JPanel();
        death.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        int between = (Toolkit.getDefaultToolkit().getScreenSize().height - 3 * 60) / 4;
        death.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, between));
        death.setBackground(Color.BLACK);
        
        Dimension button = new Dimension(500, 60);
        
        JButton quickLoad= new JButton("Quick load");
        quickLoad.setPreferredSize(button);
        
        JButton playAgain = new JButton("Play again");
        playAgain.setPreferredSize(button);
        
        JButton mainMenu = new JButton("Main menu");
        mainMenu.setPreferredSize(button);
        
        quickLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				startGame("quicksave.dat");
			}
		});
        
        playAgain.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	startGame("resources/maps/level_1.dat");
            }
        });
        
        mainMenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(frame.getComponents().length > 0)
                {
                    frame.remove(death);
                }
                frame.add(menu);   
                frame.update(frame.getGraphics());
                frame.setVisible(true);
            }
        });
        death.add(quickLoad);
        death.add(playAgain);
        death.add(mainMenu);
    }
    
    private static void createEndPanel()
    {
        end = new JPanel();
        end.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        int between = (Toolkit.getDefaultToolkit().getScreenSize().height - 3 * 60) / 4;
        end.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, between));
        end.setBackground(Color.BLACK);
        
        Dimension button = new Dimension(500, 60);
        
        JButton endText = new JButton("The end. (actually no, but so far..)");
        endText.setPreferredSize(button);
        
        JButton playAgain = new JButton("Play again");
        playAgain.setPreferredSize(button);
        
        JButton mainMenu = new JButton("Main menu");
        mainMenu.setPreferredSize(button);
        
        playAgain.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	startGame("resources/maps/level_1.dat");
            }
        });
        
        mainMenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(frame.getComponents().length > 0)
                {
                    frame.remove(end);
                }
                frame.add(menu);   
                frame.update(frame.getGraphics());
                frame.setVisible(true);
            }
        });
        end.add(endText);
        end.add(playAgain);
        end.add(mainMenu);
    }
    
    public static void showDeath()
    {
        frame.remove(main);
        frame.remove(gameComponents);
        gameComponents.stop();
        
        frame.add(death);
        frame.setVisible(true);
    }
    public static void showEnd()
    {
        frame.remove(main);
        frame.remove(gameComponents);
        gameComponents.stop();
        
        frame.add(end);
        frame.setVisible(true);
    }
    public static void startGame(String path)
    {
    	try 
    	{
			startGame(Date.load(path));
		}
    	catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectiveOperationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void startGame(World world)
    {
    	frame.remove(menu);
		gameComponents = new Game(Toolkit.getDefaultToolkit().getScreenSize());
		frame.add(gameComponents);
		gameComponents.init(world);
		gameComponents.start();
		getGUI().stepState = false;
		frame.setVisible(true);
    }
    public static void main(String [] args)
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	scale = 1;
    	WIDTH = basicWIDTH = (int) screenSize.getWidth();
    	HEIGHT = basicHEIGHT = (int) screenSize.getHeight();
    	
        frame = new JFrame("Game");
        
        OverlayLayout ol = new OverlayLayout(frame.getContentPane());
        frame.getContentPane().setLayout(ol);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        gameComponents = new Game(screenSize);
        createMainPanel();
        createDeathPanel();
        createEndPanel();
//        createChooseMapPanel();
        createMenuPanel();
        frame.add(menu);
        
        frame.setUndecorated(true);
        frame.setAutoRequestFocus(true);
        frame.setBounds(0, 0, screenSize.width, screenSize.height);
        frame.setVisible(true);
    }

    public static Creator getCreator()
    {
    	return creator;
    }
	public static GUI getGUI()
	{
		return gui;
	}
}