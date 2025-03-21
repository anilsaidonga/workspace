package algorithms;

public class KadanesAlgorithm {
	
	/*
	 * time: O(n)
	 * 
	 * space: O(1)
	 */
	
	public static int maximumSubSubarray(int[] arr)
	{
		int maxSum = arr[0], currentSum = arr[0];
		
		for (int i = 1; i < arr.length; i++)
		{
			//currentSum = Math.max(arr[i], arr[i] + currentSum);
			if (arr[i] > (arr[i] + currentSum))
			{
				currentSum = arr[i];
			}
			else
			{
				currentSum = arr[i] + currentSum;
			}
			//maxSum = Math.max(currentSum, maxSum);
			if (currentSum > maxSum)
			{
				maxSum = currentSum;
			}
		}
		
		return maxSum;
	}
}
