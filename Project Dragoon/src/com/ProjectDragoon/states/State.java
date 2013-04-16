package com.ProjectDragoon.states;

import com.ProjectDragoon.util.enums.ReverseEnumMap;
import com.ProjectDragoon.util.interfaces.EnumConverter;

public enum State implements EnumConverter<State> {

	TEST(0), MENU(1), PLAY(2);
	
	private static ReverseEnumMap<State> map = new ReverseEnumMap<State>(State.class);
	
	private final int value;
	
	private State(int value)
	{
		this.value = value;
	}
	
	@Override
	public int convert()
	{
		return value;
	}
	
	@Override
	public State convert(int val)
	{
		return map.get(val);
	}
	
}
