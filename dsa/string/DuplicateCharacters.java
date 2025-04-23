package string;

import java.util.Arrays;

public class DuplicateCharacters {
	
	/*
	 * brute force approach
	 * 
	 * time complexity: O(n * logn)
	 * 
	 * space complexity: O(n)
	 * 
	 * auxiliary space complexity: O(n)
	 */
	
	public static void duplicateCharacters1(String s)
	{
		char[] sCharArray = s.toCharArray();
		Arrays.sort(sCharArray);
		
		for (int i = 0; i < sCharArray.length; i++)
		{
			int count = 1;
			while (i < sCharArray.length - 1 && sCharArray[i] == sCharArray[i + 1])
			{
				count++;
				i++;
			}
			if (count > 1)
			{
				System.out.println(sCharArray[i] + " : " + count);
			}
		}
	}

	public static void main(String[] args) {
		String s = "a";
		duplicateCharacters1(s); // aabb
	}

}
