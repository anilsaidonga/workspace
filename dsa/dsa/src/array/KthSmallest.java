package array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class KthSmallest {
	
	// brute force
	/*
	 * time : O(nlogn)
	 * 
	 * space: O(n)
	 * 
	 */
	public static int kthSmallest1(int[] arr, int k)
	{
		Arrays.sort(arr);
		return (arr[k - 1]);
	}
	
	// expected approach 1
	/*
	 * time: O(nlogk)
	 * 
	 * space: O(k)
	 * 
	 */
	public static int kthSmallest2(int[] arr, int k)
	{
		// max heap
		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
		Integer[] array = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++)
			array[i] = arr[i];
		for (int j = 0; j < arr.length; j++)
		{
			if (pq.size() < k)
			{
				pq.add(array[j]);
			}
			else
			{
				if (j <= arr.length - 1 && array[j] < pq.peek())
				{
					pq.remove();
					pq.add(array[j]);
				}
			}
		}
		return pq.peek().intValue();
	}
	
	public static void main(String[] args)
	{
		
		int n, k, i;
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		
		int[] arr = new int[n];
		
		i = 0;
		
		while (i < n)
		{
			arr[i] = sc.nextInt();
			i++;
		}
		
		k = sc.nextInt();
		
		System.out.println(kthSmallest2(arr, k));
		
		sc.close();
		
	}
}
