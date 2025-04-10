package string;

public class PalindromeString {
	
	public static boolean palindromeString(String s)
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
		String s = "appa";
		System.out.print(palindromeString(s));
	}

}
