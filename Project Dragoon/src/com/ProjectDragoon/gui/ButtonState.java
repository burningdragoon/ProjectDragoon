package com.ProjectDragoon.gui;

import com.ProjectDragoon.util.enums.ReverseEnumMap;
import com.ProjectDragoon.util.interfaces.EnumConverter;

public enum ButtonState implements EnumConverter<ButtonState> {

	NORMAL(0), HOVER(1), PRESSED(2), ACTIVE(3);
	
	private static ReverseEnumMap<ButtonState> map = new ReverseEnumMap<ButtonState>(ButtonState.class);
	
	private final int value;
	
	private ButtonState(int value)
	{
		this.value = value;
	}
	
	@Override
	public int convert()
	{
		return value;
	}
	
	@Override
	public ButtonState convert(int val)
	{
		return map.get(val);
	}
	
}
