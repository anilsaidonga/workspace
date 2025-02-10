package array;

import java.util.Scanner;

public class Execute {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("n: ");
		
		int n = sc.nextInt();
		
		int[] arr = new int[n];
		
		for (int i = 0; i < n; i++)
		{
			System.out.print("element " + (i + 1) + ": ");
			arr[i] = sc.nextInt();
		}
		
		MergeSort.mergeSort(arr, 0, arr.length - 1);
		
		for (int j : arr)
			System.out.println(j);
		
		sc.close();
	}

}
