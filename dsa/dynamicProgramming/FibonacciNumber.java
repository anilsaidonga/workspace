package dynamicProgramming;

public class FibonacciNumber {
	
	// tabulation (bottom up)
	/*
	 * time complexity: O(n)
	 * space complexity: O(1)
	 */
	
	public static int fibonacciNumber1(int n)
	{
		int prevPrev = 0, prev = 1;
		for (int i = 2; i <= n; i++)
		{
			int curr = prev + prevPrev;
			prevPrev = prev;
			prev = curr;
		}
		return prev;
	}
	
	// memoization (top down)
	/*
	 * time complexity: O(n)
	 * space complexity: O(n) + O(n) = O(n)
	 * 					array	recursion stack
	 */
	
	public static int fibonacciNumber2(int n, int[] arr)
	{
		if (n <= 1) return n;
		if (arr[n] != -1) return arr[n];
		else
			arr[n] = fibonacciNumber2(n - 1, arr) + fibonacciNumber2(n - 2, arr);
		return arr[n];
	}

	public static void main(String[] args) {
		int[] arr = new int[5];
		for (int i = 0; i < arr.length; i++)
			arr[i] = -1;
		System.out.println(fibonacciNumber1(3));
	}

}
