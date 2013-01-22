package com.ProjectDragoon.util.interfaces;

public interface EnumConverter<E extends Enum<E> & EnumConverter<E>> {
	public int convert();
	public E convert(int val);
}
