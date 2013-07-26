package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import main.Game;
import main.Island;
import main.World;
import main.saving.Date;
import entity.mob.Creator;

public class ChooseMapPanel extends JPanel
{
	private final JList mapsList;
	private JPanel south;
	private JTextField newlevelname;
	private JButton newWorld;
	
	public ChooseMapPanel()
	{
    	super();
    	setPreferredSize(new Dimension(640, 400));
    	setLayout(new BorderLayout());
    	
    	File[] links = new File("resources/maps").listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.contains(".dat");
			}
		});
    	
    	final DefaultListModel model = new DefaultListModel();
    	for(int q=0;q<links.length;q++)
    	{
    		model.addElement(links[q].getName());
    	}
    	
    	mapsList = new JList(model);
    	mapsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	mapsList.addKeyListener(Listeners.spaceEscCloser);
    	mapsList.addMouseListener(new MouseListener()
    	{
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2 && e.getButton() == e.BUTTON1)
				{
					if(model.size() <= 0) return;
					Point p = e.getPoint();
					Rectangle rect = mapsList.getCellBounds(0, model.size()-1);
					if(!rect.contains(p)) return;
					mapsList.setSelectedIndex(mapsList.locationToIndex(p));
					ok();
				}
			}
		});
    	mapsList.addKeyListener(new KeyListener()
    	{
			@Override
			public void keyTyped(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == e.VK_ENTER) ok();
			}
		});
    	add(mapsList);
    	
    	south = new JPanel();
    	add(south, BorderLayout.SOUTH);
    	
    	newWorld = new JButton("New world");
    	newWorld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				World world = new World();
				new Island(world);
//				new Creator().init(0, 0, world);
				
				Game.removeFlowingFrame();
				
				Game.startGame(world);
			}
		});
    	south.add(newWorld, BorderLayout.WEST);
	}
	private void ok()
	{
		World world = null;
		if(mapsList.getSelectedValue() == null) return;

		try 
		{
			world = Date.load("resources/maps/"+mapsList.getSelectedValue().toString());
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ReflectiveOperationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game.startGame(world);
		new Creator().init(world.getCharacter().getCX()-world.getCharacter().getWidth()/2, world.getCharacter().getCY()-world.getCharacter().getHeight()/2, world);
		Game.removeFlowingFrame();
	}
	
}
