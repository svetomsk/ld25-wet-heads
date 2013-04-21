package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import main.saving.Date;
import GUI.GUI;
import entity.mob.Creator;

public class Game extends Canvas implements Runnable
{
    public static int WIDTH;
    public static int HEIGHT;   
    public static final int SIZE = 1;
    
    public String frames = "";
    public static int x, y;
    private static final double MOUSE_FACTOR = 0;
    private static final double SCROLL_SPEED = 0.2;
    
    public boolean renderState = false;
    
    private static InputHandler inputHandler;
    private static Input input;
    private static GUI gui;    
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

    private void init()
    {
    	input = inputHandler.update(SIZE);
    	
    	world = new World();
    	world.createLevel();
    	world.parseInputForEntities();
    	
        requestFocus();
    }
    private void init(String name)
    {
    	input = inputHandler.update(SIZE);
    	
    	try 
    	{
			world = Date.load(name);
		}
    	catch (IOException | ReflectiveOperationException e) 
		{
//			init();
		}
		world.findCharacter();
		
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
    	
    	double vx = getGUI().getMobX() - w; 
//    			world.character.getX()+world.character.getWidth()/2 - w;
    	double vy = getGUI().getMobY(); 
//    			world.character.getY()+world.character.getHeight()/2;
    	vy -= h ;
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
            
            world.draw(g);
            gui.draw(g);
            PrintString.drawPrinting(g);
            
//            g.setColor(new Color((float)(1-world.character.hp), (float)world.character.hp, (float)0.0));
//            g.fillRect(32, 32, (int)(world.character.hp * WIDTH/3), 8);
            
            g.setColor(Color.GRAY);
            g.drawString(frames, WIDTH - frames.length() * 12, 12);

            g.dispose();
            bs.show();
        }
    }
    
    public static void setGUI(GUI gui)
    {
    	Game.gui = gui;
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
    public static void save(String name)
	{
		try
		{
			Date.save(world, name);
			PrintString.println("Saved successfully");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void load(String name)
	{
		try
		{
			world = Date.load(name);
			world.findCharacter();
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
    private static JFrame frame, flowingFrame;
    private static JPanel menu, main, death, end, chooseMap, tools;
    private static Game gameComponents;
    
    private static void createToolsPanel()
    {
    	tools = new JPanel();
    	tools.setLayout(new BorderLayout());
    	
    	JPanel lists = new JPanel();
    	lists.setLayout(new BoxLayout(lists, BoxLayout.X_AXIS));
    	tools.add(lists, BorderLayout.CENTER);
    	
    	//TODO (for flowing frame);
    	
//    	JPanel other = new JPanel();
    }
    private static void createChooseMapPanel()
    {
    	chooseMap = new JPanel();
    	chooseMap.setPreferredSize(new Dimension(640, 400));
    	chooseMap.setLayout(new BorderLayout());
    	
    	File[] links = new File("resources/maps").listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.contains(".dat");
			}
		});
    	DefaultListModel model = new DefaultListModel();
    	for(int q=0;q<links.length;q++)
    	{
    		model.addElement(links[q].getName());
    	}
    	final JList mapsList = new JList(model);
    	mapsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	chooseMap.add(mapsList);
    	
    	JPanel south = new JPanel();
    	chooseMap.add(south, BorderLayout.SOUTH);
    	
    	JTextField newlevelname = new JTextField();
    	newlevelname.setPreferredSize(new Dimension(256, 16));
    	south.add(newlevelname, BorderLayout.CENTER);
    	
    	JButton ok = new JButton("Ok");
    	ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(mapsList.getSelectedValue() == null) return;
				
				frame.remove(menu);
				gameComponents = new Game(Toolkit.getDefaultToolkit().getScreenSize());
				frame.add(gameComponents);
				gameComponents.init("resources/maps/"+mapsList.getSelectedValue().toString());
				gameComponents.start();
				frame.setVisible(true);
				
				new Creator().init(world.getCharacter().getX()-world.getCharacter().getWidth()/2, world.getCharacter().getY()-world.getCharacter().getHeight()/2, world);
				flowingFrame.dispose();
			}
		});
    	south.add(ok, BorderLayout.WEST);
    }
    private static void createMenuPanel()
    {
        menu = new JPanel();
        menu.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        int bheight = 70;
        int bwidth = 3*WIDTH/5;
        int range = (Toolkit.getDefaultToolkit().getScreenSize().height - 4 * bheight)/5;
        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 100, range));
//        JButton contin = new JButton("Continue");
        JButton start = new JButton("Start");
        JButton about = new JButton("About");
        JButton editor = new JButton("Editor");
        JButton exit = new JButton("Exit");
        
        Dimension button = new Dimension(bwidth, bheight);
        
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
        
        start.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent ae)
           {
               frame.remove(menu);
               gameComponents = new Game(Toolkit.getDefaultToolkit().getScreenSize());               
               frame.add(gameComponents);
               gameComponents.init();
               gameComponents.start();
               frame.setVisible(true);               
           }
        });
        editor.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent arg0)
        	{
        		flowingFrame = new JFrame("Maps");
                flowingFrame.setBounds(WIDTH/4, HEIGHT/4, WIDTH/2, HEIGHT/2);
        		
                createChooseMapPanel();
        		flowingFrame.add(chooseMap);
        		
        		flowingFrame.setAlwaysOnTop(true);
//        		frame.setEnabled(false);
        		
        		flowingFrame.setVisible(true);
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
        menu.add(start);
        menu.add(editor);
        menu.add(about);
        menu.add(exit);
    }
    
    public static void addMenu()
    {
        if(frame.getComponents().length > 0)
        {
            frame.remove(gameComponents);
            frame.remove(main);       
            frame.remove(death);
            gameComponents.stop();
        }
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
				frame.remove(menu);
			    gameComponents = new Game(Toolkit.getDefaultToolkit().getScreenSize());               
			    frame.add(gameComponents);
			    gameComponents.init();
			    quickLoad();
			    gameComponents.start();
			    frame.setVisible(true);
			}
		});
        
        playAgain.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
               frame.remove(menu);
               gameComponents = new Game(Toolkit.getDefaultToolkit().getScreenSize());               
               frame.add(gameComponents);
               gameComponents.init();
               gameComponents.start();
               frame.setVisible(true);    
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
               frame.remove(menu);
               gameComponents = new Game(Toolkit.getDefaultToolkit().getScreenSize());               
               frame.add(gameComponents);
               gameComponents.init();
               gameComponents.start();
               frame.setVisible(true);    
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
    public static void main(String [] args)
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	WIDTH = (int) screenSize.getWidth();
    	HEIGHT = (int) screenSize.getHeight();
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        gameComponents = new Game(screenSize);
        createMainPanel();
        createDeathPanel();
        createEndPanel();
//        createChooseMapPanel();
        createMenuPanel();
        frame.add(menu);
        
        frame.setUndecorated(true);
        frame.setBounds(0, 0, screenSize.width, screenSize.height);
        frame.setVisible(true);
    }

	public static GUI getGUI()
	{
		return gui;
	}
}