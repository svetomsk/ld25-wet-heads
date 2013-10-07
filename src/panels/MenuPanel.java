package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import main.Game;
import GUI.GUI;

public class MenuPanel extends JPanel
{
	public MenuPanel(GUI g, Dimension dim)
	{
    	super();
    	final boolean prevStepState = g.stepState;
    	setPreferredSize(dim);
    	
    	int countOfButtons = 4;
        int bheight = (int) (dim.getHeight()/(2*countOfButtons+1));
        int bwidth = (int) (dim.getWidth()/2);
        
        int gap = (int) (dim.getWidth()-bwidth);
        int range = (int) ((dim.getHeight() - countOfButtons * bheight)/(countOfButtons+1));
        
        setLayout(new FlowLayout(FlowLayout.CENTER, gap, range));
        
        Dimension button = new Dimension(bwidth, bheight);
//        contin.setPreferredSize(button);
//        contin.setMinimumSize(button);
//        contin.setMaximumSize(button);
//        contin.setAlignmentX(JComponent.CENTER_ALIGNMENT);
//        contin.setAlignmentY(JComponent.CENTER_ALIGNMENT);
    	
    	JButton continueb = new JButton("Continue");
    	continueb.setPreferredSize(button);
    	
    	JButton save = new JButton("Save");
    	save.setPreferredSize(button);
    	
    	JButton load = new JButton("Load");
    	load.setPreferredSize(button);

    	JButton quit = new JButton("Quit");
    	quit.setPreferredSize(button);
    	
    	continueb.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Game.getGUI().stepState = prevStepState;
				Game.removeFlowingFrame(Game.FFRAME2);
//				((JFrame)getParent()).dispose();
			}
		});
    	save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
//				((JFrame)getParent()).dispose();
				Game.saveMenu();
				Game.removeFlowingFrame(Game.FFRAME2);
			}
		});
    	load.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Game.loadMenu();
				Game.removeFlowingFrame(Game.FFRAME2);
			}
		});
    	quit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Game.removeFlowingFrame(Game.FFRAME2);
				Game.toMainMenu();
			}
		});
    	
    	add(continueb);
    	add(save);
    	add(load);
    	add(quit);
	}
	
}
