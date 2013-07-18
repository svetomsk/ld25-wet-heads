package panels;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;

public class Listeners
{
	public static KeyListener spaceEscCloser = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
		}
		@Override
		public void keyReleased(KeyEvent e)
		{
		}
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == e.VK_SPACE || e.getKeyCode() == e.VK_ESCAPE)
			{
				Game.removeFlowingFrame();
			}
		}
	};
}
