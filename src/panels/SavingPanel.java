package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import GUI.CreatorGUI;
import GUI.GUI;

public class SavingPanel extends JPanel
{
	public SavingPanel(GUI gui)
	{
		super();
		setLayout(new BorderLayout());
		
		File path;
		if(gui instanceof CreatorGUI)
		{
			path = new File("resources/maps");
		}
		else
		{
			path = new File("saves");
		}
		
		DefaultListModel<File> model = new DefaultListModel<File>();
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
		add(list, BorderLayout.NORTH);
		
	}
}
