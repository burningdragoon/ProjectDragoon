package com.Testing;

import com.ProjectDragoon.util.enums.ReverseEnumMap;
import com.ProjectDragoon.util.interfaces.EnumConverter;

public enum EnumValues implements EnumConverter<EnumValues> {

	zero(0), one(1), two(2), three(3);
	
	private static ReverseEnumMap<EnumValues> map = new ReverseEnumMap<EnumValues>(EnumValues.class);
	
	private int value;
	
	private EnumValues(int value)
	{
		this.value = value;
	}
	
	@Override
	public int convert() {
		return value;
	}

	@Override
	public EnumValues convert(int val) {
		return map.get(val);
	}

}
