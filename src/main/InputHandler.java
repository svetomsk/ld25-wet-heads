package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


class InputHandler implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener
{
    public boolean isLMC = false;
    public boolean isWheel = false;
    public boolean isRMC = false;
    
    public int mx, my;
    public boolean onScreen = true;    
    public String typed = "";    
    public boolean [] keys = new boolean[65536];
    
    private Input input = new Input();
    public InputHandler(Game r)
    {
        r.addMouseListener(this);
        r.addMouseMotionListener(this);
        r.addKeyListener(this);
        r.addMouseWheelListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
    	mx = e.getX();
    	my = e.getY();
    	onScreen = true;
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            isLMC = true;
        
        if(e.getButton() == MouseEvent.BUTTON2)
            isWheel = true;
       
        if(e.getButton() == MouseEvent.BUTTON3)
            isRMC = true;

    	mx = e.getX();
    	my = e.getY();
    	onScreen = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            isLMC = false;
        
        if(e.getButton() == MouseEvent.BUTTON2)
            isWheel = false;
       
        if(e.getButton() == MouseEvent.BUTTON3)
            isRMC = false;

    	mx = e.getX();
    	my = e.getY();
    	onScreen = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {        
    	mx = e.getX();
    	my = e.getY();
    	onScreen = true;
    	Game.removeFlowingFrame();
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        onScreen = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
    	mx = e.getX();
    	my = e.getY();
    	onScreen = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
    	mx = e.getX();
    	my = e.getY();
    	onScreen = true;
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        typed = typed + e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        if(keyCode > 0 && keyCode < keys.length)
        {
            keys[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        if(keyCode > 0 && keyCode < keys.length)
        {
            keys[keyCode] = false;
        }
    }
    
    public Input update(int scale)
    {
        input.update(mx/scale, my/scale, isLMC, isRMC, isWheel, onScreen, keys, typed);
        return input;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
    	double rotation = e.getPreciseWheelRotation();
    	Game.scale(rotation);
    }
    
    public void free()
    {
    	isLMC = false;
        isWheel = false;
        isRMC = false;
        
        onScreen = true;
        keys = new boolean[65536];
    }
}
