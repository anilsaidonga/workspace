package array;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import utilities.Utilities;

public class KthSmallest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = sc.nextInt();
		int k = sc.nextInt();
		System.out.println(k + "th smallest: " + kthSmallest2(arr, k));
		sc.close();
	}
	
	/*
	 * brute-force approach
	 * 
	 * time:	
	 * -------------------------------------------------- 
	 *     primitive arrays (int[], double[], etc,....)
	 *     ----------------------------------------------
	 *     best case: O(n*logn)
	 *     average case: O(n*logn)
	 *     worst case: O(n^2)
	 * 
	 *     object arrays (Integer[], String[], etc,....)
	 *     ----------------------------------------------
	 *     best case: O(n)
	 *     average case: O(n*logn)
	 *     worst case: O(n*logn)
	 * 
	 * space:
	 * ---------------------------
	 *     best case: O(logn)
	 *     average case: O(logn)
	 *     worst case: O(n)
	 */
	
	public static int kthSmallest1(int[] arr, int k)
	{
		Arrays.sort(arr);
		return arr[k - 1];
	}
	
	/*
	 * brute optimized approach
	 * 
	 * time:
	 * --------------
	 * O(k*logk) + O((n-k)*logk)
	 * O(k*logk) + O(n*logk) - O(k*logk)
	 * O(n*logk)
	 * best case: O(n*logk)
	 * average case: O(n*logk)
	 * worst case: O(n*logk)
	 * 
	 * space: 
	 * --------------
	 * best case: O(k)
	 * average case: O(k)
	 * worst case: O(k)
	 */
	
	public static int kthSmallest2(int[] arr, int k)
	{
		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		Integer[] array = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			array[i] = arr[i];
		}
		
		for (int i = 0; i < k; i++)
		{
			pq.add(array[i]);
		}
		
		for (int i = k; i < array.length; i++)
		{
			if (array[i] < pq.peek())
			{
				pq.poll();
				pq.add(array[i]);
			}
		}
		
		return array[k - 1].intValue();
	}
	
	/*
	 * expected approach
	 * 
	 * time:
	 * ------------------
	 * best case: O(n)
	 * average case: O(n)
	 * worst case: O(n^2)
	 * 
	 * space:
	 * ------------------
	 * best case: O(logn)
	 * average case: O(logn)
	 * worst case: O(n)
	 */

	public static int kthSmallest3(int[] arr, int k)
	{
		return quickSelect(arr, 0, arr.length - 1, k - 1);
	}
	
	public static int quickSelect(int[] arr, int low, int high, int k)
	{
		if (low <= high)
		{
			int pivotIndex = partition(arr, low, high);
			if (pivotIndex == k)
				return arr[k];
			if (pivotIndex > k)
				return quickSelect(arr, low, pivotIndex - 1, k);
			if (pivotIndex < k)
				return quickSelect(arr, pivotIndex + 1, high, k);
		}
		return -1;
	}
	
	public static int partition(int[] arr, int low, int high)
	{
	    int pivot = arr[low];
	    int i = low + 1; // Start scanning from the element after the pivot

	    // Move through the array and swap smaller elements to the left
	    for (int j = low + 1; j <= high; j++) {
	        if (arr[j] <= pivot) {
	            // Swap elements smaller than or equal to the pivot to the left side
	            Utilities.swap(arr, i, j);
	            i++;
	        }
	        // We don't need to swap elements greater than the pivot, they are naturally left on the right side
	    }

	    // Finally, place the pivot element in its correct position
	    Utilities.swap(arr, low, i - 1);
	    return i - 1; // Return the index of the pivot
	}

}
