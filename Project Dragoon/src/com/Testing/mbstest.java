package com.Testing;

public class mbstest {

	public static int triangleType(int sideA, int sideB, int sideC)
	{
		int result = 0;
		
		//test for negative/0
		if(sideA <= 0 || sideB <= 0 || sideC <= 0)
		{
			result = 4;
		}
		//test for triangle inequality
		else if(!(
				(sideA + sideB > sideC) &&
				(sideA + sideC > sideB) &&
				(sideB + sideC > sideA)
				))
		{
			result = 4;
		}
		//test for equilateral
		else if(sideA == sideB && sideB == sideC)
		{
			result = 3;
		}
		//test for isosc -- since equilateral is checked for first, you don't need to worry about all three being equal, just any two
		else if(sideA == sideB || sideA == sideC || sideB == sideC)
		{
			result = 2;
		}
		// otherwise it's scalene
		else
			result = 1;
		
		return result;
	}
	
	public static String testTriangleType(int type)
	{
		String result = "";
		
		switch(type)
		{
			case 1:
				result = "Scalene Triangle";
				break;
			case 2:
				result = "Isosceles Triangle";
				break;
			case 3:
				result = "Equilateral Triangle";
				break;
			case 4:
			default:
				result = "Error";
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		//System.out.println(testTriangleType(triangleType(0,0,0)));
		
		int[][] cases = new int[5][3];
		cases[0][0] = 10; cases[0][1] = 10; cases[0][2] = 10;
		cases[1][0] = 20; cases[1][1] = 20; cases[1][2] = 10;
		cases[2][0] = 15; cases[2][1] = 5;  cases[2][2] = 25;
		cases[3][0] = 0;  cases[3][1] = 10; cases[3][2] = 7;
		cases[4][0] = 15; cases[4][1] = 5;  cases[4][2] = 18;
		
		for(int i = 0; i < cases.length; i++)
		{
			int type = triangleType(cases[i][0], cases[i][1], cases[i][2]);
			String result = testTriangleType(type);
			System.out.printf("Triangle %d (%d,%d,%d): %s\n", i+1, cases[i][0], cases[i][1], cases[i][2], result);
		}
		
		System.out.println("\n -- \n");
		
		Queue q = new Queue(10);
		//q.initialize(10);
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);
		//q.enqueue(96);
		//q.enqueue(3);
		//q.enqueue(100);
		q.dequeue();
		q.dequeue();
		q.dequeue();
		q.enqueue(8);
		q.enqueue(9);
		q.enqueue(10);
		q.enqueue(11);
		
		
		q.printInOrder();
		q.printInMemoryOrder();
		
	}
	
}

class Queue
{
	private int size;
	private int[] values;
	private int numItems;
	private int headIndex;
	private int tailIndex;
	
	public Queue(int size)
	{
		initialize(size);
	}
	
	public void initialize(int size)
	{
		this.size = size;
		values = new int[size];
		numItems = 0;
		headIndex = 0;
		tailIndex = 0;
	}
	
	public void enqueue(int value)
	{
		if(!isFull())
		{
			synchronized(this)
			{
				values[tailIndex++] = value;
				numItems++;
				if(tailIndex >= size)
					tailIndex = 0;
			}
		}
		
	}
	
	public int dequeue()
	{
		int result = -1;
		if(!isEmpty())
		{
			synchronized(this)
			{
				result = values[headIndex++];
				if(headIndex >= size)
					headIndex = 0;
				numItems--;
			}
		}
		return result;
	}
	
	public boolean isEmpty()
	{
		return numItems == 0;
	}
	
	public boolean isFull()
	{
		return numItems == size;
	}
	
	public void printInOrder()
	{
		if(isEmpty())
		{
			System.out.println("Queue is empty.");
		}
		else
		{
			int pointer = headIndex;
			if(isFull())
			{
				System.out.print(values[pointer++] + ", ");
				if(pointer >= size)
					pointer = 0;
			}
			while(pointer != tailIndex)
			{
				System.out.print(values[pointer++]);
				if(pointer >= size)
					pointer = 0;
				if(pointer != tailIndex)
					System.out.print(", ");
			}
			System.out.println();
		}
	}
	
	public void printInMemoryOrder()
	{
		if(isEmpty())
			System.out.println("Queue is empty.");
		else
		{
			for(int i = 0; i < size; i++)
			{
				if(tailIndex < headIndex)
				{
					if(i < tailIndex || i >= headIndex)
						System.out.printf("[%d]", values[i]);
					else
						System.out.print("[-]");
				}
				else if(tailIndex > headIndex)
				{
					if(i >= headIndex && i < tailIndex)
						System.out.printf("[%d]", values[i]);
					else
						System.out.print("[-]");
				}
				else
					System.out.printf("[%d]", values[i]);
			}
			System.out.println();
			
		}
	}
}
