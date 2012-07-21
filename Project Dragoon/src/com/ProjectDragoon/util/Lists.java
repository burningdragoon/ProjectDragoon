package com.ProjectDragoon.util;

/**
 * A set of List/Array functions
 * @author Alex
 *
 */
public class Lists {

	/**
	 * Sorts two lists. The second list is sorted based on the first list.
	 * @param list1
	 * @param list2
	 * @param dir
	 */
	public static void DoubleSort(int[] list1, int[] list2, SortDirection dir)
	{
		if (list1.length == list2.length)
		{
			boolean sorted = false;
			while(!sorted)
			{
				boolean swapped = false;
				for(int i = 1; i < list1.length; i++)
				{
					int A1 = list1[i-1];
					int A2 = list1[i];
					int B1 = list2[i-1];
					int B2 = list2[i];
					
					switch(dir)
					{
						case DESCENDING:
							if(A1 < A2)
							{
								swapped = true;
								list1[i-1] = A2;
								list1[i] = A1;
								list2[i-1] = B2;
								list2[i] = B1;
							}
							break;
						case ASCENDING:
							if(A1 > A2)
							{
								swapped = true;
								list1[i-1] = A2;
								list1[i] = A1;
								list2[i-1] = B2;
								list2[i] = B1;
							}
							break;
					}
				}
				if(!swapped)
					sorted = true;
			}
		}
	}
	

public enum SortDirection
{
	ASCENDING,
	DESCENDING
}
}