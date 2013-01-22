package com.ProjectDragoon.maps;

import com.ProjectDragoon.util.enums.ReverseEnumMap;
import com.ProjectDragoon.util.interfaces.EnumConverter;

public enum TileType implements EnumConverter<TileType> {

	NONE(0), SOLID(1);
	
	private static ReverseEnumMap<TileType> map = new ReverseEnumMap<TileType>(TileType.class);
	
	private final int value;
	private TileType(int value)
	{
		this.value = value;
	}
	
	@Override
	public int convert() {
		return value;
	}

	@Override
	public TileType convert(int val) {
		return map.get(val);
	}
}
