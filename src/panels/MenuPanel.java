package panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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
    	
        int bheight = (int) (dim.getHeight()/9);
        int bwidth = (int) (dim.getWidth()/3);
        int range = bheight;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, range));
    	
    	JButton continueb = new JButton("Continue");
    	JButton save = new JButton("Save");
    	JButton load = new JButton("Load");
    	JButton quit = new JButton("Quit");
    	
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
