package utilities;

public class Utilities {
	
	public static void swap(int[] arr, int i, int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public static int firstOccurence(int[] arr, int target)
	{
		int left = 0, right = arr.length - 1, result = -1;
		
		while (left <= right)
		{
			int mid = left + (right - left) / 2;
			if (arr[mid] == target)
			{
				result = mid;
				right = mid - 1;
			}
			else if (arr[mid] < target)
			{
				left = mid + 1;
			}
			else
			{
				right = mid - 1;
			}
		}
		
		return result;
	}
	
	public static int lastOccurence(int[] arr, int target)
	{
		int left = 0, right = arr.length - 1, result = -1;
		
		while (left <= right)
		{
			int mid = left + (right - left) / 2;
			if (arr[mid] == target)
			{
				result = mid;
				left = mid + 1;
			}
			else if (arr[mid] > target)
			{
				right = mid - 1;
			}
			else
			{
				left = mid + 1;
			}
		}
		
		return result;
	}
	
}
