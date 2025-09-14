package recursion;

import java.util.*;

public class SubsetSum {
	
	/*
	 * brute force approach
	 * time complexity: O( 2 ^ n * n ) ( there are 2 ^ n sub sets * and you copy every sub set of size n )
	 * space complexity: O(n) + O( 2 ^ n * n ) ( there is recursion depth of n + and there are 2 ^ n sub sets )
	 */
	
	public static boolean helper(int[] arr, int ind, List<Integer> curr, int sum)
	{
		if (ind >= arr.length)
		{
			int subSetSum = 0;
			for (Integer i : curr) subSetSum += i;
			return subSetSum == sum;
		}
		
		// include wala case
		curr.add(arr[ind]);
		if (helper(arr, ind + 1, curr, sum)) return true;
		curr.remove(curr.size() - 1);
		
		// exclude wala case        
		if (helper(arr, ind + 1, curr, sum)) return true;
		
		return false;
	}

	public static void main(String[] args) {
		//int[] arr = {3, 34, 4, 12, 5, 2};	
		int[] arr = {1, 2, 3};
		List<Integer> curr = new ArrayList<>();
		List<List<Integer>> res = new ArrayList<>();
		System.out.println(helper(arr, 0, curr, 6));
	}

}
