package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import main.Game;
import main.World;
import GUI.CreatorGUI;
import GUI.GUI;
import entity.mob.Creator;

public class LoadingPanel extends JPanel
{
	private final JList<File> list;
	private GUI gui;
	public LoadingPanel(GUI gui)
	{
		super();
		this.gui = gui;
		setLayout(new BorderLayout());
		
		final File path;
		if(gui instanceof CreatorGUI)
		{
			path = new File("resources/maps");
		}
		else
		{
			path = new File("saves");
		}
		
		final DefaultListModel<File> model = new DefaultListModel<File>();
		File[] arr = path.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.contains(".dat");
			}
		});
		for(int q=0;q<arr.length;q++)
		{
			model.addElement(arr[q]);
		}
		
		list = new JList<File>(model);
		list.setCellRenderer(new ListCellRenderer<File>()
    	{
    		@Override
			public Component getListCellRendererComponent(JList<? extends File> list, File value, int index, boolean isSelected, boolean cellHasFocus) 
    		{
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
			}
		});
		list.addKeyListener(Listeners.spaceEscCloser);
		list.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent arg0){}
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					onButtonTyped();
				}
			}
		});
		list.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 2 && e.getButton() == e.BUTTON1)
				{
					if(model.size() <= 0) return;
					Point p = e.getPoint();
					Rectangle rect = list.getCellBounds(0, model.size()-1);
					if(!rect.contains(p)) return;
					list.setSelectedIndex(list.locationToIndex(p));
					onButtonTyped();
				}
			}
		});
		add(list, BorderLayout.CENTER);
	}
	private void onButtonTyped()
	{
		String name = list.getSelectedValue().getPath();
		Game.load(name);
		if(gui instanceof CreatorGUI)
		{
			World world = Game.getGUI().getWorld();
			new Creator().init(world.getCharacter().getCX()-world.getCharacter().getWidth()/2, world.getCharacter().getCY()-world.getCharacter().getHeight()/2, world);
		}
		Game.removeFlowingFrame();
	}
}
