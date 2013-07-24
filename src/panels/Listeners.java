package panels;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Game;

public class Listeners
{
	public static KeyListener spaceEscCloser = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == e.VK_SPACE || e.getKeyCode() == e.VK_ESCAPE)
			{
				Game.removeFlowingFrame();
			}
		}
	};
	public static MouseListener mouseExitedCloser = new MouseListener()
	{
		@Override
		public void mouseReleased(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e)
		{
			Game.removeFlowingFrame();
		}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseClicked(MouseEvent e){}
	};
	public static KeyListener escCloser = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == e.VK_ESCAPE)
			{
				Game.removeFlowingFrame();
			}
		}
	};
}
