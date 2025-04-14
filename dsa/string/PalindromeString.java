package string;

public class PalindromeString {
	
	/*
	 * brute force (recursion)
	 * 
	 * time complexity: O(n)
	 * 
	 * space complexity: O(n)
	 * 
	 * auxiliary space complexity: O(n)
	 */
	public static boolean palindromeString1(String s)
	{
		if (s.length() < 1)
			return true;
		else if (s.charAt(0) != s.charAt(s.length() - 1))
			return false;
		else
			return palindromeString1(s.substring(1, s.length() - 1));
	}
	
	/*
	 * bure force (inbuilt string reversal)
	 * 
	 * time complexity: O(n)
	 * 
	 * space complexity: O(n)
	 * 
	 * auxiliary space complexity: O(n)
	 */
	
	public static boolean palindromeString2(String s)
	{
		return s.equals(new StringBuilder(s).reverse().toString());
	}
	
	/*
	 * expected approach (two pointer approach )
	 * 
	 * time complexity: O(n)
	 * 
	 * space complexity: O(n)
	 * 
	 * auxiliary space complexity: O(1)
	 */
	
	public static boolean palindromeString3(String s)
	{
		int l = 0, r = s.length() - 1;
		
		while (l < r)
		{
			if (s.charAt(l) != s.charAt(r))
				return false;
			l++;
			r--;
		}
		
		return true;
		
	}
	
	/*
	 * expected approach ( iterate through half array, and check for it's corresponding element from the end )
	 * 
	 * time complexity: O(n)
	 * 
	 * space complexity: O(n)
	 * 
	 * auxiliary space complexity: O(1)
	 */
	
	public static boolean palindromeString4(String s)
	{
		int i = 0, j = s.length() - 1;
		while (i <= j / 2)
		{
			if (s.charAt(i) == s.charAt(j - i))
			{
				i++;
			}
			else
			{
				return false;
			}
		}
		return true;
	}	
	public static void main(String[] args) {
		String s = "app4";
		System.out.print(palindromeString3(s));
	}

}
