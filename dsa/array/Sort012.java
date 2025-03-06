package array;

import java.util.Arrays;
import java.util.Scanner;

import utilities.utilities;

public class Sort012 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
		{
			do
			{
				arr[i] = sc.nextInt();
			}
			while (arr[i] != 0 && arr[i] != 1 && arr[i] != 2);
		}
		sort0123(arr);
		for (int i : arr)
			System.out.print(i + " ");
		sc.close();
	}

	/*
	 * brute-force approach - using sorting
	 * time: O(n*logn)
	 * space: O(logn)
	 */
	public static void sort0121(int[] arr)
	{
		Arrays.sort(arr);
	}
	
	/*
	 * better approach - counting number of 0's, 1's and 2's
	 * time: O(2*n)
	 * space: O(1)
	 */
	public static void sort0122(int[] arr)
	{
		int c0 = 0, c1 = 0, c2 = 0, j = 0;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] == 0)
				c0++;
			else if (arr[i] == 1)
				c1++;
			else
				c2++;
		}
		while (c0 > 0)
		{
			arr[j] = 0;
			j++;
			c0--;
		}
		while (c1 > 0)
		{
			arr[j] = 1;
			j++;
			c1--;
		}
		while (c2 > 0)
		{
			arr[j] = 2;
			j++;
			c2--;
		}
	}
	
	/*
	 * expected approach - dutch national flag algorithm
	 * time: O(n)
	 * space: O(1)
	 */
	
	public static void sort0123(int[] arr)
	{
		int low = 0, mid = 0, high = arr.length - 1;
		while (mid <= high)
		{
			if (arr[mid] == 0)
			{
				utilities.swap(arr, low, mid);
				low++;
				mid++;
			}
			else if (arr[mid] == 1)
			{
				mid++;
			}
			else
			{
				utilities.swap(arr, mid, high);
				high--;
			}
		}
	}

}
