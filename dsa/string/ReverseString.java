package string;

import utilities.Utilities;
import java.util.ArrayList;
import java.util.Collections;

public class ReverseString {
	
	/*
	 * naive approach
	 * time complexity: O(n)
	 * space complexity: O(n)
	 * auxiliary space complexity: O(n)
	 */
	
	public static void reverseString1(char[] arr)
	{
		char[] temp = arr.clone();
		int n = temp.length;
		for (int i = 0; i < n; i++)
		{
			arr[i] = temp[n - i - 1];
		}
	}
	
	/*
	 * expected approach
	 * time complexity: O(n)
	 * space complexity: O(n)
	 * auxiliary space complexity: O(1)
	 */
	public static void reverseString2(char[] arr)
	{
		int l = 0, r = arr.length - 1;
		while (l < r)
		{
			Utilities.swap(arr, l, r);
			l++;
			r--;
		}
	}
	
	/*
	 * expected approach
	 * time complexity: O(n)
	 * space complexity: O(n)
	 * auxiliary space complexity: O(1)
	 */
	
	public static void reverseString3(char[] arr)
	{
		int n = arr.length, i = 0;
		while (i < n / 2)
		{
			Utilities.swap(arr, i, arr.length - i - 1);
			i++;
		}
	}
	
	/*
	 * expected approach
	 * time complexity: O(n)
	 * space complexity: O(n)
	 * auxiliary space complexity: O(n)
	 */
	public static void reverseString4(char[] arr, int i, int j)
	{
		if (i < j)
		{
			Utilities.swap(arr, i, j);
			reverseString4(arr, i + 1, j - 1);
		}
	}
	
	/*
	 * expected approach
	 * time complexity: O(n)
	 * space complexity: O(n)
	 * auxiliary space complexity: O(1)
	 */
	
	public static void reverseString5(char[] arr)
	{
		ArrayList<Character> l = new ArrayList<Character>();
		for (int i = 0; i < arr.length; i++)
			l.add(arr[i]);
		Collections.reverse(l);
		for (int i = 0; i < arr.length; i++)
			arr[i] = l.get(i).charValue();
	}
	
	public static void main(String[] args) {
		char[] arr = {'h', 'e', 'l', 'l', 'o'};
		reverseString5(arr);
		for (char i : arr)
		{
			System.out.print(i);
		}
	}

}
