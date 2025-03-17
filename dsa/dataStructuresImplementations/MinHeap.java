package dataStructuresImplementations;

import utilities.Utilities;

public class MinHeap {
	
	public static void buildHeap(int[] arr)
	{
		heapify(arr, 0);
	}
	
	public static void heapify(int[] arr, int index)
	{
		int smallestIndex = index;
		int left = 2 * index + 1;
		int right = 2 * index + 2;
		
		if(index < arr.length && arr[left] < arr[smallestIndex])
		{
			smallestIndex = left;
		}
		
		if(index < arr.length && arr[right] < arr[smallestIndex])
		{
			smallestIndex = right;
		}
		
		if (index != smallestIndex)
		{
			Utilities.swap(arr, index, smallestIndex);
			heapify(arr, smallestIndex);
		}
	}

	public static void main(String[] args) {
		int[] arr = {3, 2, 1};
		buildHeap(arr);
	}

}
