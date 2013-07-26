package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import main.Game;
import main.PrintString;
import tool.BlocksTool;
import tool.ClassName;
import tool.EntitiesDeletingTool;
import tool.EntitiesEditorTool;
import tool.EntitiesTool;
import tool.Tool;
import GUI.CreatorGUI;

public class ToolsPanel extends JPanel
{
	private JList<ClassName> blocks, entities;
	private JList<Tool> tools;
	
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
    	blocks.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int button = e.getButton();
				blocks.setSelectedIndex(blocks.locationToIndex(e.getPoint()));
				ClassName cln = (ClassName) blocks.getSelectedValue();
				Class bcl = cln.getClassN();
				BlocksTool btool = new BlocksTool(bcl);
				
				if(button == MouseEvent.BUTTON1)
				{
					((CreatorGUI) Game.getGUI()).setLMB(btool);
					PrintString.println("LMB  -  "+cln.getName());
				}
				else if(button == MouseEvent.BUTTON2)
				{
					((CreatorGUI) Game.getGUI()).setWheel(btool);
					PrintString.println("WHEEL  -  "+cln.getName());
				}
				else if(button == MouseEvent.BUTTON3)
				{
					((CreatorGUI) Game.getGUI()).setRMB(btool);
					PrintString.println("RMB  -  "+cln.getName());
				}
			}
		});
    	lists.add(blocks);
    	
    	entities = new JList<ClassName>(ClassName.entities);
    	entities.setCellRenderer(new ListCellRenderer<ClassName>()
    	{
    		@Override
			public Component getListCellRendererComponent(JList<? extends ClassName> list, ClassName value, int index, boolean isSelected, boolean cellHasFocus) 
    		{
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
			}
		});
    	entities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	entities.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int button = e.getButton();
				entities.setSelectedIndex(entities.locationToIndex(e.getPoint()));
				ClassName cln = (ClassName) entities.getSelectedValue();
				Class ecl = cln.getClassN();
				EntitiesTool etool = new EntitiesTool(ecl);
				
				if(button == MouseEvent.BUTTON1)
				{
					((CreatorGUI) Game.getGUI()).setLMB(etool);
					PrintString.println("LMB  -  "+cln.getName());
				}
				else if(button == MouseEvent.BUTTON2)
				{
					((CreatorGUI) Game.getGUI()).setWheel(etool);
					PrintString.println("WHEEL  -  "+cln.getName());
				}
				else if(button == MouseEvent.BUTTON3)
				{
					((CreatorGUI) Game.getGUI()).setRMB(etool);
					PrintString.println("RMB  -  "+cln.getName());
				}
			}
		});
    	lists.add(entities);
    	
    	tools = new JList<Tool>(new Tool[]{
    			new EntitiesDeletingTool(),
    			new EntitiesEditorTool() 
    			});
    	tools.setCellRenderer(new ListCellRenderer<Tool>()
    	{
    		@Override
			public Component getListCellRendererComponent(JList<? extends Tool> list, Tool value, int index, boolean isSelected, boolean cellHasFocus) 
    		{
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index, isSelected, cellHasFocus);
			}
		});
    	tools.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	tools.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int button = e.getButton();
				tools.setSelectedIndex(tools.locationToIndex(e.getPoint()));
				Tool tool = tools.getSelectedValue(); 
				
				if(button == MouseEvent.BUTTON1)
				{
					((CreatorGUI) Game.getGUI()).setLMB(tool);
					PrintString.println("LMB  -  "+tool.getName());
				}
				else if(button == MouseEvent.BUTTON2)
				{
					((CreatorGUI) Game.getGUI()).setWheel(tool);
					PrintString.println("WHEEL  -  "+tool.getName());
				}
				else if(button == MouseEvent.BUTTON3)
				{
					((CreatorGUI) Game.getGUI()).setRMB(tool);
					PrintString.println("RMB  -  "+tool.getName());
				}
			}
		});
    	lists.add(tools);
    	
		blocks.addKeyListener(Listeners.spaceEscCloser);
		entities.addKeyListener(Listeners.spaceEscCloser);
		tools.addKeyListener(Listeners.spaceEscCloser);
		addKeyListener(Listeners.spaceEscCloser);
	}
}
