package recursion;

<<<<<<< HEAD
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

=======
public class SubsetSum {
	
//	public static boolean helper(int ind, int[] arr, int sum)
//	{
//		if (sum == 0) return true;
//		
//		if (ind == arr.length) return false;
//		
//		// include wala case
//		if (helper(ind + 1, arr, sum - arr[ind])) return true;
//		
//		// exclude wala case
//		if (helper(ind + 1, arr, sum)) return true;
//		
//		return false;
//	}
	
	public static boolean helper(int ind, int[] arr, int sum, Boolean[][] dp)
	{
		if (sum == 0) return true;
		
		if (ind < 0) return false;
		
		if (dp[ind][sum] != null) return dp[ind][sum];
		
		// include wala case
		Boolean include = helper(ind - 1, arr, sum - arr[ind], dp);
		
		// exclude wala case
		Boolean exclude = helper(ind - 1, arr, sum, dp);
		
		return dp[ind][sum] = (include || exclude);
	}

	public static void main(String[] args) {
		int[] arr = {1, 2, 3};
		Boolean[][] dp = new Boolean[arr.length][5 + 1];
		System.out.println(helper(arr.length - 1, arr, 5, dp));
	}
>>>>>>> 96f69942f36ff05275a24eeb6704b5e8b9a62363
}
