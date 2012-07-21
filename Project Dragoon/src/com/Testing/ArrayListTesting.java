package com.Testing;

import java.util.ArrayList;

public class ArrayListTesting {

	public static void main(String[] args)
	{
		ArrayList<String> strings = new ArrayList<String>();
		
		strings.add("Hello");
		strings.add("HERP A DERP");
		strings.add("Moo says the cow");
		
		System.out.println(strings.get(0));
		
		strings.remove(0);
		
		System.out.println(strings.get(0));
	}
}
