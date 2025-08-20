package greedy;

import java.util.Arrays;

public class MinimumNumberOfPlatformsRequiredForARailway {
	
	/*
	 * brute force approach
	 * time complexity: O(n ^ 2) ( double for loop )
	 * space complexity: O(1)        
	 */
	
	public static int minimumNumberOfPlatformsRequiredForARailway(int[] arr, int[] dep)
	{
		int n = arr.length;
		
		int res = 1;
		
		for (int i = 0; i < n; i++)
		{
			int count = 1;
			for (int j = i + 1; j < n; j++)
			{
				if (intersect(arr[i], dep[i], arr[j], dep[j]))
					count++;
			}
			res = Math.max(res,  count);
		}
		
		return res;
	}
	
	private static boolean intersect(int i, int j, int k, int l) {
		/*
		 * (i, j) (k, l)
		 * consider case for not overlapping
		 * case 1 : (i, j) (k, l)
		 * condition : j < k
		 * case 2 : (k, l) (i, j)
		 * condition : l < i
		 * invert conditions for not overlapping so thate you will get condition for overlapping        
		 * so j >= k and l >= i
		 * so condition is j >= k && l >= i, we have to take both conditions because one condition alone cannot tell whether they are overlapping or not        
		 */
		if(j >= k && l >= i)
			return true;
		return false;
	}        
	
	/*
	 * optimal approach
	 * time complexity: O(nlogn) + O(nlogn) + O(n + n) ( sort arr + sort dep + two pointers approach )        
	 * space complexity: O(1)        
	 */
	
//	public static int minimumNumberOfPlatformsRequiredForARailway(int[] arr, int[] dep)
//	{
//		int n = arr.length;
//		
//		Arrays.sort(arr);
//		Arrays.sort(dep);
//		
//		int res = 0, i = 0, j = 0, count = 0;
//		
//		while (i < n && j < n)
//		{
//			if (arr[i] <= dep[j])
//			{
//				count++;
//				i++;
//			}
//			else
//			{
//				count--;
//				j++;
//			}
//			res = Math.max(res,  count);
//		}
//		
//		return res;
//	}

	public static void main(String[] args) {
		
		int[] arr = {900,945,955,1100,1500,1800};
		int[] dep = {920,1200,1130,1150,1900,2000};
		
//		int[] arr = {1020, 1200};
//		int[] dep = {1050, 1230};
		
		System.out.println(minimumNumberOfPlatformsRequiredForARailway(arr, dep));
	}

}
