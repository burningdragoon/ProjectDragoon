package com.ProjectDragoon.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.ProjectDragoon.MyMath;

public class MouseInputHandler implements MouseListener, MouseMotionListener {

	private static final int MOUSE_BUTTONS = 3;
	
	public static final int MOUSE_BUTTON_1 = 0;
	public static final int MOUSE_BUTTON_2 = 1;
	public static final int MOUSE_BUTTON_3 = 2;
	
	// The Mouse coordinates... duh
	private int mouseX;
	private int mouseY;
	
	// Click Event variables
	// clickTime is the point in time when the mouse's "click" event last fired.
	// clickLength is how long the "clicked" state remains active 
	private long clickLength;
	private long[] clickTimes;
	private boolean[] clickActive;
	
	// A toggle between the mouse button values 
	private boolean[] isPressed;
	
	public MouseInputHandler()
	{
		mouseX = 0;
		mouseY = 0;
		
		clickLength = 100L;
		clickTimes = new long[MOUSE_BUTTONS];
		clickActive = new boolean[MOUSE_BUTTONS];
		
		isPressed = new boolean[MOUSE_BUTTONS];
	}
	
	/*
	 * Selectors & Mutators
	 */
	
	public int getX() { return mouseX; }
	public int getY() { return mouseY; }
	
	/**
	 * 0: Button 1 / MouseEvent.BUTTON1. Most likely left mouse button
	 * 1: Button 2 / MouseEvent.BUTTON2. Most likely right mouse button
	 * 2: Button 3 / MouseEvent.BUTTON3. Most likely center mouse button / scroll wheel button
	 * @param button
	 * @return
	 */
	public boolean isPressed(int button) 
	{ 
		if (button < 0 || button >= MOUSE_BUTTONS)
			return false;
		else
			return isPressed[button]; 
	}
	
	/**
	 * 0: Button 1 / MouseEvent.BUTTON1. Most likely left mouse button
	 * 1: Button 2 / MouseEvent.BUTTON2. Most likely right mouse button
	 * 2: Button 3 / MouseEvent.BUTTON3. Most likely center mouse button / scroll wheel button
	 * @param button
	 * @return
	 */
	public long getClickTime(int button)
	{
		if(button < 0 || button >= MOUSE_BUTTONS)
			return -1L;
		else
			return clickTimes[button];
	}
	
	/* -- End S&M -- */
	
	/*
	 * Other...
	 */
	
	/**
	 * Is the mouse's click event active.
	 * @param button
	 * @return true if the current system time is within the click window
	 */
	public boolean isClicked(int button)
	{
		if (button < 0 || button >= MOUSE_BUTTONS)
			return false;
		else
		{
			boolean clicked = clickActive[button] && MyMath.nanoToMilli(System.nanoTime()) - clickTimes[button] <= clickLength;
			
			if(clicked)
				clickActive[button] = false;
			
			return clicked;
		}
	}
	
	/* -- OTHER - */
	
	/*
	 * Mouse Event Helper methods
	 */
	
	private void toggleMousePress(int button, boolean pressed)
	{
		switch(button)
		{
			case MouseEvent.BUTTON1:
				isPressed[MOUSE_BUTTON_1] = pressed;
				break;
			
			case MouseEvent.BUTTON2:
				isPressed[MOUSE_BUTTON_2] = pressed;
				break;
			
			case MouseEvent.BUTTON3:
				isPressed[MOUSE_BUTTON_3] = pressed;
				break;
			
			case MouseEvent.NOBUTTON:
			default:
				break;
		}
	}
	
	private void toggleMousePress(MouseEvent e, boolean pressed)
	{
		toggleMousePress(e.getButton(), pressed);
	}
	
	/* -- End Mouse Event Helpers -- */
	
	/* --- Mouse Listener Methods --- */
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int buttonIndex = -1;
		switch(e.getButton())
		{
			case MouseEvent.BUTTON1:
				buttonIndex = MOUSE_BUTTON_1;
				//clickTimes[MOUSE_BUTTON_1] = MyMath.nanoToMilli(System.nanoTime());
				//clickActive[MOUSE_BUTTON_1] = true;
				break;
			
			case MouseEvent.BUTTON2:
				buttonIndex = MOUSE_BUTTON_2;
				//clickTimes[MOUSE_BUTTON_2] = System.nanoTime();
				break;
			
			case MouseEvent.BUTTON3:
				buttonIndex = MOUSE_BUTTON_3;
				//clickTimes[MOUSE_BUTTON_3] = System.nanoTime();
				break;
			
			case MouseEvent.NOBUTTON:
			default:
				break;
		}
		
		clickTimes[buttonIndex] = MyMath.nanoToMilli(System.nanoTime());
		clickActive[buttonIndex] = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		toggleMousePress(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{	
		toggleMousePress(e, false);
	}
	
	/* --- End Mouse Listener --- */

	/* --- Mouse Motion Listener Methods --- */
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	/* --- End Mouse Motion --- */
	
}
