package array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import utilities.utilities;

public class ReverseTheArray {
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println();
		int n = sc.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = sc.nextInt();
		}
		reverseTheArray5(arr);
		for (int i : arr)
			System.out.print(i + " ");
		sc.close();
	}

	/*
	 * brute force
	 * time: O(n)
	 * space: O(n)
	 */
	public static void reverseTheArray1(int[] arr) {
		int n = arr.length;
		int[] tempArr = new int[n];
		for (int i = n - 1; i >= 0; i--)
			tempArr[n - 1 - i] = arr[i];
		for (int j = 0; j < n; j++)
			arr[j] = tempArr[j];
	}
	
	/*
	 * expected approach 1
	 * time: O(n)
	 * space: O(1)
	 */
	public static void reverseTheArray2(int[] arr)
	{
		int i = 0, j = arr.length - 1;
		while (i > j)
			utilities.swap(arr, i, j);
	}
	
	/*
	 * expected approach 2
	 * time: O(n)
	 * space: O(1)
	 */
	
	public static void reverseTheArray3(int[] arr)
	{
		int mid = (arr.length - 1) / 2, i = 0;
		while (i < mid)
		{
			utilities.swap(arr, i, arr.length - 1 - i);
			i++;
		}
	}
	
	/*
	 * expected approach 3
	 * time: O(n)
	 * space: O(1)
	 */
	public static void reverseTheArray4(int[] arr, int i, int j)
	{
		if (i >= j)
			return;
		utilities.swap(arr, i, j);
		reverseTheArray4(arr, i + 1, j - 1);
	}
	
	/* expected approach 4
	 * time: O(n)
	 * space: O(1)
	 */
	public static void reverseTheArray5(int[] arr)
	{
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < arr.length; i++)
			l.add(arr[i]);
		Collections.reverse(l);
		for (int j = 0; j < arr.length; j++)
			arr[j] = l.get(j).intValue();
	}
}
