package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import main.Game;
import GUI.CreatorGUI;
import GUI.GUI;

public class LoadingPanel extends JPanel
{
	public LoadingPanel(GUI gui)
	{
		super();
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
		
		final JList<File> list = new JList<File>(model);
		list.setCellRenderer(new ListCellRenderer<File>()
    	{
    		@Override
			public Component getListCellRendererComponent(JList<? extends File> list, File value, int index, boolean isSelected, boolean cellHasFocus) 
    		{
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
			}
		});
		list.addKeyListener(Game.spaceEscCloser);
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
			private void onButtonTyped()
			{
				String name = list.getSelectedValue().getPath();
				Game.load(name);
				Game.removeFlowingFrame();
			}
		});
		add(list, BorderLayout.CENTER);
	}
}
