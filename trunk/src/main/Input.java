package main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Input
{
	public static class Key
	{
		public int[] bindings = new int[0];
		public boolean wasDown;
		public boolean down;
		public boolean typed;

		public Key(Input input)
		{
			input.keys.add(this);
		}

		public Key bind(int key)
		{
			int[] newBindings = new int[bindings.length + 1];
			System.arraycopy(bindings, 0, newBindings, 0, bindings.length);
			newBindings[bindings.length] = key;
			bindings = newBindings;
			return this;
		}

		public void tick(boolean[] keysDown)
		{
			wasDown = down;
			down = false;
			for (int i = 0; i < bindings.length; i++)
			{
				if (keysDown[bindings[i]])
					down = true;
			}
			typed = !wasDown && down;
		}
	}

	public int x, y;

	public boolean onScreen;

	public boolean lmb, rmb, wheel;
	public boolean lmbClicked;
	public boolean rmbClicked;
	public boolean wheelClicked;
	public boolean lmbReleased;
	public boolean rmbReleased;
	public boolean wheelReleased;

	public String typed = "";

	private List<Key> keys = new ArrayList<Key>();

	public Key up = new Key(this).bind(KeyEvent.VK_UP).bind(KeyEvent.VK_W);
	public Key down = new Key(this).bind(KeyEvent.VK_DOWN).bind(KeyEvent.VK_S);
	public Key left = new Key(this).bind(KeyEvent.VK_LEFT).bind(KeyEvent.VK_A);
	public Key right = new Key(this).bind(KeyEvent.VK_RIGHT).bind(KeyEvent.VK_D);
	public Key restart = new Key(this).bind(KeyEvent.VK_R); // modified
	public Key quit = new Key(this).bind(KeyEvent.VK_ESCAPE); // modified
	public Key pause = new Key(this).bind(KeyEvent.VK_PAUSE).bind(KeyEvent.VK_P); // modilied
	public Key space = new Key(this).bind(KeyEvent.VK_SPACE);
	public Key q = new Key(this).bind(KeyEvent.VK_Q);
	public Key heal = new Key(this).bind(KeyEvent.VK_H).bind(KeyEvent.VK_E);
	public Key quicksave = new Key(this).bind(KeyEvent.VK_F5);
	public Key quickload = new Key(this).bind(KeyEvent.VK_F9);

	public Input()
	{
	}

	public void update(int x, int y, boolean b0, boolean b1, boolean b2,
			boolean onScreen, boolean[] keysDown, String typed)
	{
		lmbClicked = !this.lmb && b0;
		rmbClicked = !this.rmb && b1;
		wheelClicked = !this.wheel && b2;

		lmbReleased = this.lmb && !b0;
		rmbReleased = this.rmb && !b1;
		wheelReleased = this.wheel && !b2;

		this.x = x;
		this.y = y;
		this.lmb = b0;
		this.rmb = b1;
		this.wheel = b2;
		this.onScreen = onScreen;
		this.typed = "";

		for (Key key : keys)
		{
			key.tick(keysDown);
		}
	}
}
