package com.ProjectDragoon.util.enums;

import java.util.HashMap;
import java.util.Map;

import com.ProjectDragoon.util.interfaces.EnumConverter;

public class ReverseEnumMap<V extends Enum<V> & EnumConverter> {
	
	private Map<Integer, V> map = new HashMap<Integer, V>();
	
	public ReverseEnumMap(Class<V> valueType)
	{
		for(V v : valueType.getEnumConstants())
		{
			map.put(v.convert(), v);
		}
	}
	
	public V get(int num)
	{
		return map.get(num);
	}

}
