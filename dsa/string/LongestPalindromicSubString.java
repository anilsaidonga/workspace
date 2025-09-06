package string;

public class LongestPalindromicSubString {
	
	public static boolean isPalindrome(String s, int i, int j)
	{
		while (i < j)
		{
			if (s.charAt(i) != s.charAt(j)) return false;
			i++;
			j--;
		}
		return true;
	}
	
	/*
	 * brute force approach        
	 * time complexity: O(n^3) ( two for loops + isPalindrome will take another O(n) )
	 * space complexity: O(1)
	 */
	
	public static String longestPalindrome(String s)
	{
		String res = "";
		int l = 0;
		for (int i = 0; i < s.length(); i++)
		{
			for (int j = i; j < s.length(); j++)
			{
				
				if (isPalindrome(s, i, j))
				{
					if (j - i + 1 > l)
					{
						l = j - i + 1;
						res = new String(s.substring(i, j + 1));
					}
				}
			}
		}
		return res;
	}
	
	/*
	 * optimal approach
	 * Pick a center (either a single letter or gap between letters).
	 * Expand outward as long as both sides are equal.
	 * This gives you the longest palindrome around that center.
	 * Do this for every possible center → find the best.
	 * 
	 * time complexity: O(n^2) ( single for loop + expanding at every index will take another O(n) )        
	 * space complexity: O(1)        
	 */
	
	public static void main(String[] args) {
		System.out.println(longestPalindrome("babad"));
	}

}
