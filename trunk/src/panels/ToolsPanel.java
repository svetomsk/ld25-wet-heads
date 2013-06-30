package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import main.PrintString;
import main.saving.IDManager;
import tool.ClassName;

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
    	
    	blocks = new JList<ClassName>(ClassName.blocks);
    	blocks.setCellRenderer(new ListCellRenderer<ClassName>()
    	{
    		@Override
			public Component getListCellRendererComponent(JList<? extends ClassName> list, ClassName value, int index, boolean isSelected, boolean cellHasFocus) 
    		{
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
			}
		});
    	
    	
    	blocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	blocks.addMouseListener(new Adapter(blocks, this));
    	lists.add(blocks);
    	
    	entities = new JList<>(IDManager.getClasses());
    	entities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	entities.addMouseListener(new Adapter(entities, this));
    	lists.add(entities);
    	
    	tools = new JList<>(new String[]{"TEST TOOL 1", "TEST TOOL 2", "TEST TOOL 3"});
    	tools.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	tools.addMouseListener(new Adapter(tools, this));
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
	
	public void setLmbstr(String lmbstr) 
	{
		this.lmbstr = lmbstr;
	}
	public void setRmbstr(String rmbstr) 
	{
		this.rmbstr = rmbstr;
	}
	public void setWheelstr(String wheelstr) 
	{
		this.wheelstr = wheelstr;
	}
}
class Adapter extends MouseAdapter
{
	private JList list;
	private ToolsPanel panel;
	
	public Adapter(JList list, ToolsPanel panel) 
	{
		this.list = list;
		this.panel = panel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int button = e.getButton();
		list.setSelectedIndex(list.locationToIndex(e.getPoint()));
		String str = (String) list.getSelectedValue();
		
		if(button == MouseEvent.BUTTON1)
		{
			panel.setLmbstr(str);
			PrintString.println("B1  -  "+str);
		}
		else if(button == MouseEvent.BUTTON2)
		{
			panel.setRmbstr(str);
			PrintString.println("B2  -  "+str);
		}
		else if(button == MouseEvent.BUTTON3)
		{
			panel.setWheelstr(str);
			PrintString.println("B3  -  "+str);
		}
	}
}
