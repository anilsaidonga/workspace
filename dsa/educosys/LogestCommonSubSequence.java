package educosys;

public class LogestCommonSubSequence {
	
	/*
	 * time complexity: O(2^(m + n) since we are calling helper2 twice within it
	 * space complexity: O(m + n) height of recursion tree
	 */
	
	public static int helper2(String text1, String text2, int ind1, int ind2)
	{
		// base condition
		if (ind1 >= text1.length() || ind2 >= text2.length()) return 0; 
		
		// if characters at ind1 and ind2 are same
		if (text1.charAt(ind1) == text2.charAt(ind2)) return 1 + helper2(text1, text2, ind1 + 1, ind2 + 1);
		
		// if not same
		return Math.max(helper2(text1, text2, ind1 + 1, ind2), helper2(text1, text2, ind1, ind2 + 1));
	}
	
	public static int longestCommonSubsequence2(String text1, String text2)
	{
		return helper2(text1, text2, 0, 0);
	}
	
	/*
	 * time complexity: O(m*n) since we are calculating only once
	 * space complexity: O(m*n) + O(m + n) (space for 2d array + height of recursive stack)
	 */

	public static int helper3(String text1, String text2, int ind1, int ind2, int[][] dp) {
		// base condition
		if (ind1 >= text1.length() || ind2 >= text2.length())
			return 0;
		// check if it is pre-computed
		if (dp[ind1][ind2] != -1)
			return dp[ind1][ind2];
		// same character
		if (text1.charAt(ind1) == text2.charAt(ind2))
			return dp[ind1][ind2] = 1 + helper3(text1, text2, ind1 + 1, ind2 + 1, dp);
		// different character
		return dp[ind1][ind2] = Math.max(helper3(text1, text2, ind1, ind2 + 1, dp),
				helper3(text1, text2, ind1 + 1, ind2, dp));
	}

	public static int longestCommonSubsequence3(String text1, String text2) {
		int m = text1.length(), n = text2.length();
		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				dp[i][j] = -1;
			}
		return helper3(text1, text2, 0, 0, dp);
	}
	
	public static int longestCommonSubsequence4(String text1, String text2) {
		int m = text1.length(), n = text2.length();
		int[][] dp = new int[m + 1][n + 1];
		
		for (int i = 0; i <= m; i++)
			for (int j = 0; j <= n; j++)
				dp[i][j] = 0;
		
		for (int i = 1; i <= m; i++)
		{
			for (int j = 1; j <= n; j++)
			{
				if (text1.charAt(i - 1) == text2.charAt(j - 1))
					dp[i][j] = 1 + dp[i - 1][j - 1];
				else
				{
					dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
				}
			}
		}
		return dp[m][n];
	}


	public static void main(String[] args) {
		System.out.println(longestCommonSubsequence4("abcde", "ace"));
	}

}
