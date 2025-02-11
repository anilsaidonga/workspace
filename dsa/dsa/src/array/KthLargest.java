package array;

import utilities.utilities;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class KthLargest {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] arr = new int[n];
		
		for (int i = 0; i < n; i++)
			arr[i] = sc.nextInt();
		
		int k = sc.nextInt();
		
		System.out.println(kthLargest1(arr, k));
		
		sc.close();
	}

	// brute force
	/*
	 * time: O(nlogn)
	 * 
	 * space: O(n);
	 * 
	 */
	public static int kthLargest1(int[] arr, int k) {
		Arrays.sort(arr);
		reverseArray(arr);
		return (arr[k - 1]);
	}

	public static void reverseArray(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n/2; i++)
			utilities.swap(arr, i, (n - 1) - i);
	}
	
	// expected 
	/*
	 * 
	 * time: O(nlogk)
	 * 
	 * space: O(k)
	 * 
	 */
	public static int kthLargest2(int[] arr, int k)
	{
		// min-heap
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		
		Integer[] array = new Integer[arr.length];
		
		for (int i = 0; i < arr.length; i++)
			array[i] = arr[i];
		
		for (int j = 0; j < arr.length; j++)
		{
			if (pq.size() < k)
				pq.add(array[j]);
			else
			{
				if (j < arr.length - 1 && array[j] > pq.peek())
				{
					pq.remove();
					pq.add(array[j]);
				}
			}
		}
		return pq.peek().intValue();
	}

}
