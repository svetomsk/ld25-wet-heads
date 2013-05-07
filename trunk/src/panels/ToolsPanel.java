package panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import main.PrintString;
import main.saving.IDManager;

public class ToolsPanel extends JPanel
{
	private String lmbstr;
	private String wheelstr;
	private String rmbstr;
	
	private JList blocks, entities, tools;
	
	public ToolsPanel()
	{
    	super();
    	setLayout(new BorderLayout());
    	
    	JPanel lists = new JPanel();
    	lists.setLayout(new BoxLayout(lists, BoxLayout.X_AXIS));
    	add(lists, BorderLayout.CENTER);
    	
    	
    	blocks = new JList<>(IDManager.getBlockClasses());
    	blocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	blocks.addMouseListener(new MouseAdapter() 
    	{
    		@Override
    		public void mouseClicked(MouseEvent e) 
    		{
    			int button = e.getButton();
    			blocks.setSelectedIndex(blocks.locationToIndex(e.getPoint()));
    			String str = (String) blocks.getSelectedValue();
    			
    			if(button == MouseEvent.BUTTON1)
    			{
    				lmbstr = str;
    				PrintString.println("Blocks, b1, "+str);
    			}
    			else if(button == MouseEvent.BUTTON2)
    			{
    				rmbstr = str;
    				PrintString.println("Blocks, b2, "+str);
    			}
    			else if(button == MouseEvent.BUTTON3)
    			{
    				wheelstr = str;
    				PrintString.println("Blocks, b3, "+str);
    			}
    		}
    	});
    	lists.add(blocks);
    	
    	
    	entities = new JList<>(IDManager.getClasses());
    	entities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	entities.addMouseListener(new MouseAdapter() 
    	{
    		@Override
    		public void mouseClicked(MouseEvent e) 
    		{
    			int button = e.getButton();
    			entities.setSelectedIndex(entities.locationToIndex(e.getPoint()));
    			String str = (String) entities.getSelectedValue();
    			
    			if(button == MouseEvent.BUTTON1)
    			{
    				lmbstr = str;
    				PrintString.println("Entities, b1, "+str);
    			}
    			else if(button == MouseEvent.BUTTON2)
    			{
    				rmbstr = str;
    				PrintString.println("Entities, b2, "+str);
    			}
    			else if(button == MouseEvent.BUTTON3)
    			{
    				wheelstr = str;
    				PrintString.println("Entities, b3, "+str);
    			}
    		}
    	});
    	lists.add(entities);
    	
    	
    	tools = new JList<>(new String[]{"TEST TOOL 1", "TEST TOOL 2", "TEST TOOL 3"});
    	tools.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	tools.addMouseListener(new MouseAdapter() 
    	{
    		@Override
    		public void mouseClicked(MouseEvent e) 
    		{
    			int button = e.getButton();
    			tools.setSelectedIndex(tools.locationToIndex(e.getPoint()));
    			String str = (String) tools.getSelectedValue();
    			
    			if(button == MouseEvent.BUTTON1)
    			{
    				lmbstr = str;
    				PrintString.println("Entities, b1, "+str);
    			}
    			else if(button == MouseEvent.BUTTON2)
    			{
    				rmbstr = str;
    				PrintString.println("Entities, b2, "+str);
    			}
    			else if(button == MouseEvent.BUTTON3)
    			{
    				wheelstr = str;
    				PrintString.println("Entities, b3, "+str);
    			}
    		}
    	});
    	lists.add(tools);
    	
    	
    	JButton done = new JButton("DONE");
    	done.addActionListener(new ActionListener() 
    	{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				
			}
		});
    	add(done, BorderLayout.SOUTH);
	}
}
