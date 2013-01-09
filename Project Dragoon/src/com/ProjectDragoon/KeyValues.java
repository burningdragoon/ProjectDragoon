package com.ProjectDragoon;

import java.awt.event.KeyEvent;

public class KeyValues {

	//KeyValue[] keys;

	private KeyValue ACTION;
	private KeyValue BACK;
	private KeyValue UP;
	private KeyValue DOWN;
	private KeyValue RIGHT;
	private KeyValue LEFT;
	private KeyValue RESET;
	
	public KeyValues()
	{
		//ACTION = new KeyValue("A", KeyEvent.VK_A);
		ACTION = new KeyValue("A", KeyEvent.VK_A);
		BACK = new KeyValue("Enter", KeyEvent.VK_ENTER);
		
		UP = new KeyValue("Up", KeyEvent.VK_UP);
		DOWN = new KeyValue("Down", KeyEvent.VK_DOWN);
		RIGHT = new KeyValue("Right", KeyEvent.VK_RIGHT);
		LEFT = new KeyValue("Left", KeyEvent.VK_LEFT);
		
		RESET = new KeyValue("Reset", KeyEvent.VK_BACK_SPACE);
	}
	
	public int ActionKey()
	{
		return ACTION.keycode;
	}
	public String ActionName()
	{
		return ACTION.name;
	}
	
	public int BackKey()
	{
		return BACK.keycode;
	}
	public String BackName()
	{
		return BACK.name;
	}
	
	public int UpKey()
	{
		return UP.keycode;
	}
	public String UpName()
	{
		return UP.name;
	}
	
	public int DownKey()
	{
		return DOWN.keycode;
	}
	public String DownName()
	{
		return DOWN.name;
	}
	
	public int RightKey()
	{
		return RIGHT.keycode;
	}
	public String RightName()
	{
		return RIGHT.name;
	}
	
	public int LeftKey()
	{
		return LEFT.keycode;
	}
	public String LeftName()
	{
		return LEFT.name;
	}
	
	public int ResetKey()
	{
		return RESET.keycode;
	}
	public String ResetName()
	{
		return RESET.name;
	}
	
/* -------- */
	class KeyValue
	{
		public String name;
		public int keycode;
		
		public KeyValue(String n, int k)
		{
			name = n;
			keycode = k;
		}
	}
}