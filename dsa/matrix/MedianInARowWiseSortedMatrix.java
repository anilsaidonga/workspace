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
		return 0;
	}

	public static void main(String[] args) {
		int[][] arr = {{1,3,5,7},
				   	   {10,11,16,20},
				   	   {23,30,34,60}};
		System.out.println(medianInARowWiseSortedMatrix1(arr));
	}

}

