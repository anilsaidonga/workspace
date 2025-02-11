package array;

/*
 * time complexity
 * 
 * worst case - O(nlogn)
 * 
 * best case - O(nlogn)
 * 
 * space complexity - O(n) extra space for storing sorted array is required
 * 
 */

public class MergeSort {
	
	public static void mergeSort(int[] arr, int begin, int end)
	{
		if (begin < end)
		{
			int i = (begin + end) / 2;
			mergeSort(arr, begin, i);
			mergeSort(arr, i + 1, end);
			merge(arr, begin, i, i + 1, end);
		}
	}
	
	public static void merge(int[] arr, int s1, int e1, int s2, int e2)
	{
		int arrSize = (e2 - s2 + 1) + (e1 - s1 + 1);
		int[] newArr = new int[arrSize];
		int i = 0, arrStartIndex = s1;
		
		while (s1 <= e1 && s2 <= e2)
		{
			if (arr[s1] <= arr[s2])
			{
				newArr[i] = arr[s1];
				i++;
				s1++;
			}
			else
			{
				newArr[i] = arr[s2];
				i++;
				s2++;
			}
		}
		
		while (s1 <= e1)
		{
			newArr[i] = arr[s1];
			i++;
			s1++;
		}
		
		while (s2 <= e2)
		{
			newArr[i] = arr[s2];
			i++;
			s2++;
		}
		
		i = 0;
		while (i < arrSize)
		{
			arr[arrStartIndex] = newArr[i];
			arrStartIndex++;
			i++;
		}
	}
}
