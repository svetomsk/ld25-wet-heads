package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class PrintString
{
    private static ArrayList<String> printing = new ArrayList<String>();
    private static ArrayList<Integer> printingTimers = new ArrayList<Integer>();
    public static void println(String str)
    {
    	printing.add(str);
    	printingTimers.add(180);
    }
    public static void printingTimersTick()
    {
    	for(int q=0;q<printingTimers.size();q++)
    	{
//    		Integer i = printingTimers.get(q);
    		printingTimers.set(q, printingTimers.get(q)-1);
    		if(printingTimers.get(q) <= 0)
    		{
    			printingTimers.remove(q);
    			q--;
    		}
    	}
    }
    public static void drawPrinting(Graphics2D g)
    {
    	g.setColor(Color.BLACK);
    	for(int q=0;q<printingTimers.size();q++)
    	{
    		g.drawString(printing.get(printing.size()-1-q), 16, 16+q*16);
    	}
    }
}
