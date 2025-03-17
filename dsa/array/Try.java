package array;

import java.util.Scanner;
import utilities.SortingAlgorithms;

public class Try {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println();
		int n = sc.nextInt();
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
		{
			arr[i] = sc.nextInt();
		}
		SortingAlgorithms.heapSort(arr);
		sc.close();
	}

}
