package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import main.Game;
import GUI.CreatorGUI;
import GUI.GUI;

public class SavingPanel extends JPanel
{
	public SavingPanel(GUI gui)
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
		
		JList<File> list = new JList<File>(model);
		list.setCellRenderer(new ListCellRenderer<File>()
    	{
    		@Override
			public Component getListCellRendererComponent(JList<? extends File> list, File value, int index, boolean isSelected, boolean cellHasFocus) 
    		{
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
			}
		});
		list.addKeyListener(Listeners.spaceEscCloser);
		add(list, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		add(south, BorderLayout.SOUTH);
		
		final JTextField tfield = new JTextField();
		tfield.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
//				if(e.getKeyCode() == KeyEvent.VK_ENTER)
//				{
//					onButtonTyped();
//				}
			}
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
				String name;
				try
				{
					name = tfield.getText();
				}
				catch(NullPointerException ex){return;}
				
				name += ".dat";
				
				for(int q=0;q<model.getSize();q++)
				{
					if(model.get(q).getName().equals(name))
					{
						Object[] options = { "Да", "Нет!" };
						int n = JOptionPane.showOptionDialog(Game.flowingFrame, 
								"Файл с таким названием уже существует. Заменить?",
								"Подтверждение", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						if(n == 1) return;
						break;
					}
				}
				name = path.getPath()+"/"+name;
				
				Game.save(name);
				Game.removeFlowingFrame();
			}
		});
		south.add(tfield);
		
	}
}
