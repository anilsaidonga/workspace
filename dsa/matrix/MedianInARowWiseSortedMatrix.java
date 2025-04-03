package matrix;

import java.util.Arrays;

public class MedianInARowWiseSortedMatrix {
	
	/*
	 * brute force approach
	 * time: O((m * n) * log (m * n))
	 * space complexity: O(m * n)
	 * auxiliary space complexity: O(m * n)
	 */
	
	public static int medianInARowWiseSortedMatrix1(int[][] arr)
	{
		int[] temp = new int[arr.length * arr[0].length];
		int index = 0;
		for (int i = 0; i < arr.length; i++)
		{
			for (int j = 0; j < arr[0].length; j++)
			{
				temp[index] = arr[i][j];
				index++;
			}
		}
		Arrays.sort(temp);
		if ((temp.length) % 2 == 0)
		{
			int i = ((temp.length / 2) - 1), j = i + 1;
			return ((temp[i] + temp[j]) / 2);
		}
		return (temp[((temp.length - 1) / 2)]);
	}
	
	/*
	 * time complexity: O( log(max - min) * ( m * log n ))
	 * 				  binary search range * for counting n colums of m rows
	 * space complexity: O (m * n)
	 * auxiliary space complexity: O(1)
	 */
	public static int medianInARowWiseSortedMatrix2(int[][] arr)
	{
		/*
		 * Step 1: find the minimum and maximum present in the matrix, which will be used as range of binary search.
		 * Step 2: run a loop while min num is less than max num
		 * inside the loop
   		 * Step 3: find mid position, mid = min num + (max num – min num) / 2;
   		 * Step 4: now for each row, count how many numbers are less than mid,
   		 * Step 5: if count is less than half of the number update min num = mid +1
           		   else max num = mid
				   loop end
		           return min.
		 */
		int m = arr.length, n = arr[0].length, low = 0, high = 0;
		// find low and high
		for (int i = 0; i < n; i++)
		{
			low = Math.min(arr[i][0], low);
			high = Math.max(arr[i][n - 1], high);
		}
		
		int desired = ( n * m ) / 2;
		
		while (low < high)
		{
			int mid = low + (high - low) / 2;
			
			int count = 0;
			for (int i = 0; i < n; i++)
			{
				count = count + lessThanEqualsCount(arr[i], mid);
			}
			
			if (count <= desired)
				low = mid + 1;
			else
				high = mid;
		}
		return low;
	}
	
	public static int lessThanEqualsCount(int[] arr, int val)
	{
		int index = Arrays.binarySearch(arr, val);
		if (index >= 0 )
			return index + 1;
		else
			return -(index + 1);
	}

	public static void main(String[] args) {
		int[][] arr = {{1, 2, 3},
				   	   {4, 5, 6},
				   	   {7, 8, 9}};
		System.out.println(medianInARowWiseSortedMatrix2(arr));
	}

}

