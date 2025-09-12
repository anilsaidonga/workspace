package recursion;

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
}
