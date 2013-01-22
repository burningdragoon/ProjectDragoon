package com.Testing;


public class EnumTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EnumValues enumVal = EnumValues.zero;
		
		EnumValues val0 = EnumValues.zero;
		EnumValues val1 = EnumValues.one;
		
		System.out.println("Enum -> Integer");
		System.out.println(val0 + "(" + val0.convert() + ")");
		System.out.println(val1 + "(" + val1.convert() + ")");
		System.out.println();
		
		
		int int2 = 2;
		int int3 = 3;
		
		System.out.println("Integer -> Enum");
		System.out.println(int2 + "(" + enumVal.convert(int2) + ")");
		System.out.println(int3 + "(" + enumVal.convert(int3) + ")");
		
	}

}