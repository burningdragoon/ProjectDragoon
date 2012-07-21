package com.ProjectDragoon;

import java.awt.event.KeyEvent;

public class KeyValues {

	//KeyValue[] keys;

	private KeyValue ACTION;
	private KeyValue BACK;
	private KeyValue UP;
	private KeyValue DOWN;
	
	public KeyValues()
	{
		ACTION = new KeyValue("A", KeyEvent.VK_A);
		BACK = new KeyValue("Enter", KeyEvent.VK_ENTER);
		
		UP = new KeyValue("Up", KeyEvent.VK_UP);
		DOWN = new KeyValue("Down", KeyEvent.VK_DOWN);
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