package searchAndSort;

/*
 * ArrayList
 * 
 * arrayList.add(val)
 * arrayList.remove(index)
 * 
 * arrayList.get(index)
 * arrayList.set(index, val)
 * 
 * boolean iscontains = arrayList.contains(val)
 * 
 * arrayList.size()
 */

public class FindFirstAndLastOccurences {
	
	/*
	 * time complexity: O(n)
	 * space complexity: O(1)
	 */
	
	public static int[] findFirstAndLastOccurences1(int[] arr, int x)
	{
		// two pointer approach
		int[] result = {-1, -1};
		
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] == x)
			{
				if (result[0] == -1)
					result[0] = i;
				result[1] = i;
			}
		}
		
		return result;
			
	}
	
	/*
	 * time complexity: O(logn)
	 * space complexity: O(1)
	 */
	public static int[] findFirstAndLastOccurences2(int[] arr, int x)
	{
		int[] result = {-1, -1};
		int l = 0, r = arr.length - 1;
		while (l <= r)
		{
			int mid = l + (r - l)/2;
			if (arr[mid] == x)
			{
				result[0] = mid;
				r = mid - 1;
			}
			if (arr[mid] < x)
			{
				l = mid + 1;
			}
			if (arr[mid] > x)
			{
				r = mid - 1;
			}
		}
		
		l = 0;
		r = arr.length - 1;
		while (l <= r)
		{
			int mid = l + (r - l)/2;
			if (arr[mid] == x)
			{
				result[1] = mid;
				l = mid + 1;
			}
			if (arr[mid] < x)
			{
				l = mid + 1;
			}
			if (arr[mid] > x)
			{
				r = mid - 1;
			}
		}
		
		return result;
		
	}

	public static void main(String[] args) {
		int[] arr = {1, 3, 5, 5, 5, 5, 67, 123, 125};
		int x = 5;
		int[] result = findFirstAndLastOccurences2(arr, x);
		for (int i : result)
			System.out.println(i);
	}

}
