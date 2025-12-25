import java.util.Scanner;

public class MaxProductSubarray
{

	/*
	 * brute force approach
	 * time complexity: O(n * n * n) ( three for loops )
	 * space complexity: O(1) 
	 */
	
	// public static int maxProduct(int[] nums)
	// {
	// 	int maxProduct = Integer.MIN_VALUE, n = nums.length;
	// 	for (int i = 0; i < n; i++)
	// 	{
	// 		for (int j = i; j < n; j++)
	// 		{
	// 			int currProduct = 1;
	// 			for (int k = i; k <= j; k++)
	// 			{
	// 				currProduct *= nums[k];
	// 			}
	// 			maxProduct = Math.max(maxProduct, currProduct);
	// 		}
	// 	}
	// 	return maxProduct;
	// }

	/*
	 * better approach
	 * time complexity: O(n * n) ( two for loops )
	 * space complexity: O(1) 
	 */

	// public static int maxProduct(int[] nums)
	// {
	// 	int maxProduct = Integer.MIN_VALUE, n = nums.length;
	// 	for (int i = 0; i < n; i++)
	// 	{
	// 		int currProduct = 1;
	// 		for (int j = i; j < n; j++)
	// 		{
	// 			currProduct *= nums[j];
	// 			maxProduct = Math.max(maxProduct, currProduct);
	// 		}
	// 	}
	// 	return maxProduct;
	// }

	/*
	 * optimal approach
	 * time complexity: O(n) ( for loop )
	 * space complexity: O(1)
	 */

	public static int maxProduct(int[] nums)
	{
		int maxProduct = Integer.MIN_VALUE, n = nums.length;
		int prefix = 1, suffix = 1;

		for (int i = 0; i < n; i++)
		{
			prefix *= nums[i];
			suffix *= nums[n - i - 1];

			maxProduct = Math.max(maxProduct, Math.max(prefix, suffix));

			if (nums[i] == 0) prefix = 1;
			if (nums[n - i - 1] == 0) suffix = 1;
		}

		return maxProduct;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] nums = new int[n];
		for (int i = 0; i < nums.length; i++) nums[i] = sc.nextInt();
		sc.close();
		System.out.println(maxProduct(nums));
	}
}