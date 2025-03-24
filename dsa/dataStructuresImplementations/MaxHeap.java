package dataStructuresImplementations;

import utilities.Utilities;

public class MaxHeap {
	int[] arr;
	int size, lastElementIndex;
	
	MaxHeap(int size)
	{
		this.size = size;
		arr = new int[size];
		lastElementIndex = -1;
	}
	
	public void add(int val)
	{
		arr[++lastElementIndex] = val;
		heapifyUp(lastElementIndex);
	}
	
	public void remove()
	{
		arr[0] = arr[lastElementIndex];
		lastElementIndex--;
		heapifyDown(0);
	}
	
	public void heapifyUp(int index)
	{
		int parentIndex = (index - 1)/2;
		if (parentIndex >= 0)
		{
			if (arr[index] > arr[parentIndex])
			{
				Utilities.swap(arr, index, parentIndex);
				heapifyUp(parentIndex);
			}
		}
	}
	
	public void heapifyDown(int index)
	{
		int left = 2 * index + 1, right = 2 * index + 2, maxIndex = index;
		if (left <= lastElementIndex && arr[maxIndex] < arr[left])
			maxIndex = left;
		if (right <= lastElementIndex && arr[maxIndex] < arr[right])
			maxIndex = right;
		if (maxIndex != index)
		{
			Utilities.swap(arr, maxIndex, index);
			heapifyDown(maxIndex);
		}
	}
}
