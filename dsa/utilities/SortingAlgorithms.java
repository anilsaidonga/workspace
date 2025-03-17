package utilities;

import java.util.PriorityQueue;

public class SortingAlgorithms {
	
	/*
	 * time:
	 * ------------------------
	 * best case: Ω(n)
	 * worst case: O(n^2)
	 * 
	 * space: O(1)
	 */
	public static void bubbleSort(int[] arr)
	{
		int swapCounter = -1, n = arr.length;
		while (swapCounter != 0)
		{
			swapCounter = 0;
			for (int i = 0; i < n - 1; i++)
			{
				if (arr[i] > arr[i + 1])
				{
					Utilities.swap(arr, i, i + 1);
					swapCounter++;
				}
			}
		}
	}
	
	/*
	 * time:
	 * -------------------------
	 * best case: Ω(n^2)
	 * worst case: O(n^2)
	 * 
	 * space: O(1)
	 */
	
	public static void selectionSort(int[] arr)
	{
		int n = arr.length;
		for (int i = 0; i < n; i++)
		{
			int minIndex = i;
			for (int j = i + 1; j < n; j++)
			{
				if (arr[j] < arr[minIndex])
				{
					minIndex = j;
				}
			}
			if (i != minIndex)
				Utilities.swap(arr, i, minIndex);
		}
	}
	
	/*
	 * time:
	 * -----------------
	 * best case: Ω(n)
	 * worst case: O(n^2)
	 * 
	 * space: O(1)
	 */
	public static void insertionSort(int[] arr)
	{
		int n = arr.length;
		for (int i = 1; i < n; i++)
		{
			int val = arr[i];
			int j = i - 1;
			
			while (j >= 0 && val < arr[j])
			{
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = val;
		}
	}
	
	/*
	 * time:
	 * --------------
	 * best case: Ω(n*logn)
	 * worst case: O(n*logn)
	 * 
	 * space: O(n)
	 */
	public static void mergeSort(int[] arr)
	{
		sort1(arr, 0, arr.length - 1);
	}

	public static void sort1(int[] arr, int i, int j) {
		if (j > i)
		{
			int mid = (i + j)/2;
			sort1(arr, i, mid);
			sort1(arr, mid + 1, j);
			merge(arr, i, mid, mid + 1, j);
		}
	}

	public static void merge(int[] arr, int s1, int e1, int s2, int e2) {
		int[] tempArr = new int[e2 - s2 + 1 + e1 - s1 + 1];
		int i = 0, startIndex = s1;
		
		while (e1 >= s1 && e2 >= s2)
		{
			if (arr[s1] <= arr[s2])
			{
				tempArr[i] = arr[s1];
				s1++;
				i++;
			}
			else
			{
				tempArr[i] = arr[s2];
				s2++;
				i++;
			}
		}
		
		while (e1 >= s1)
		{
			tempArr[i] = arr[s1];
			s1++;
			i++;
		}
		
		while (e2 >= s2)
		{
			tempArr[i] = arr[s2];
			s2++;
			i++;
		}
		
		for (int j = startIndex, k = 0; j <= e2; j++, k++)
			arr[j] = tempArr[k];
	}
	
	/*
	 * time:
	 * -----------------------
	 * best case: Ω(n*logn)
	 * worst case: O(n^2)
	 * 
	 * space:
	 * -----------------------
	 * best case: Ω(logn)
	 * worst case: O(n)
	 */
	public static void quickSort(int[] arr)
	{
		sort2(arr, 0, arr.length - 1);
	}
	
	public static void sort2(int[] arr, int i, int j)
	{
		if (i < j)
		{
			int pivotIndex = partition(arr, i, j);
			sort2(arr, i, pivotIndex - 1);
			sort2(arr, pivotIndex + 1, j);
		}
	}
	
	public static int partition(int[] arr, int low, int high)
	{
		int pivotIndex = high;
		int i = low - 1;
		
		for (int j = low; j < high; j++)
		{
			if (arr[j] < arr[pivotIndex])
			{
				i++;
				Utilities.swap(arr, i, j);
			}
		}
		Utilities.swap(arr, i + 1, pivotIndex);
		return (i + 1);
	}
	
	/*
	 * time:
	 * ------------------
	 * best case: Ω(n*logn)
	 * average case: Ω(n*logn)
	 * worst case: O(n*logn)
	 * 
	 * space:
	 * ------------------
	 * O(1) - auxiliary space complexity 
	 */
	
	public static void heapSort(int[] arr)
	{
		// PriorityQueue defaults to min heap. you can use Collections.reverseOrder() to make it a max heap
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++)
		{
			pq.add(arr[i]);
		}
		for (int j = 0; j < arr.length; j++)
		{
			arr[j] = pq.poll().intValue();
		}
	}
}
