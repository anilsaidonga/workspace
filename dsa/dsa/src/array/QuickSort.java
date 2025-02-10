package array;

import utilities.utilities;

/* 
 * time complexity
 * 
 * worst case - O(n)^2
 * 
 * best case - O(nlogn)
 * 
 */

public class QuickSort {

	public static int partition(int[] arr, int begin, int end) {
		if (begin >= end)
			return begin;
		int pivot = begin;
		while (begin < end) {
			while (begin <= end && arr[begin] <= arr[pivot])
				begin++;
			while (end >= begin && arr[end] > arr[pivot])
				end--;
			if (begin < end)
				utilities.swap(arr, begin, end);
		}
		if (begin > end) {
			utilities.swap(arr, end, pivot);
			return end;
		}
		return -1;
	}

	public static void quickSort(int[] arr, int begin, int end) {
		if (begin >= end)
			return;
		while (begin < end) {
			int loc = partition(arr, begin, end);
			quickSort(arr, begin, loc - 1);
			quickSort(arr, loc + 1, end);
			return;
		}
	}

}
