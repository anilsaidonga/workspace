package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MinAndMaxInArray {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		int n = sc.nextInt();
		Integer[] arr = new Integer[n];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = sc.nextInt();
		}
		
		for (int i : arr)
			System.out.print(i + " ");
		sc.close();
	}
	
	public static Map<Integer, Integer> minAndMaxInArray(Integer[] arr)
	{
		Arrays.sort(arr);
		Map<Integer, Integer> m = new HashMap<>();
		m.put(arr[0], arr[arr.length - 1]);
		return m;
	}

}
