package com.ProjectDragoon.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputHandler implements KeyListener {

	public boolean[] keys;
	
	//private Timer keyTimer;
	//private int keyDelay;
	
	public KeyInputHandler()
	{
		keys = new boolean[200];
		
		// Default handler to _no_ key reading delay
		//keyTimer = null;
		//keyDelay = 0;
	}
	
	/*
	public KeyInputHandler(int keyDelay)
	{
		keys = new boolean[200];
		keyTimer = new Timer();
		this.keyDelay = keyDelay;
	}
	*/
	
	/*
	public void AddKeyTimer(int keyDelay)
	{
		if(keyTimer == null)
		{
			keyTimer = new Timer();
		}
		this.keyDelay = keyDelay;
	}
	*/
	
	public void toggle(KeyEvent ke, boolean pressed)
	{
		int keyCode = ke.getKeyCode();
		keys[keyCode] = pressed;
	}
	
	public boolean keyPressed(int keyCode)
	{
		//boolean pressed = false;
		//if (keyTimer == null)
		//{
		//	pressed = keys[keyCode];
		//}
		//else
		//{
		//	pressed = keys[keyCode] && keyTimer.stopwatch(keyDelay);
		//}
		//return pressed;
		return keys[keyCode];
	}
	
	/* ---- KeyListener Methods ---- */

	public void keyPressed(KeyEvent e) {
		//if(!keys[e.getKeyCode()])
		//	System.out.println("Key Pressed: " + e.getKeyCode());
		toggle(e, true);
	}

	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

	public void keyTyped(KeyEvent e) {
	}
	
	/* ---- */

}
