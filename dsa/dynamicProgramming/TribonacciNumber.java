package dynamicProgramming;

public class TribonacciNumber {
	
	/*
	 * tabulation ( bottom up )
	 * time complexity: O(n)
	 * space complexity: O(1)
	 */
	
	public static int tribonacciNumber1(int n)
	{
		int prevPrevPrev = 0, prevPrev = 1, prev = 1;
		for (int i = 3; i <= n; i++)
		{
			int curr = prevPrevPrev + prevPrev + prev;
			prevPrevPrev = prevPrev;
			prevPrev = prev;
			prev = curr;
		}
		return prev;
	}
	
	/*
	 * memoization ( top down )
	 * time complexity: O(n)
	 * space complexity: O(n) + O(n) = O(n)
	 * 					array	recursive stack
	 */
	public static int tribonacciNumber2(int n, int[] arr)
	{
		if (n <= 1) return n;
		if (n == 2) return 1;
		if (arr[n] != -1) return arr[n];
		else
			arr[n] = tribonacciNumber2(n - 1, arr) + tribonacciNumber2(n - 2, arr) + tribonacciNumber2(n - 3, arr);
		return arr[n];
	}

	public static void main(String[] args) {
		int[] arr = new int[4 + 1];
		for (int i = 0; i < arr.length; i++)
			arr[i] = -1;
		System.out.println(tribonacciNumber2(4, arr));
		
	}

}
