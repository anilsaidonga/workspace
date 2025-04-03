package matrix;

public class RowWithMax1s {
	
	public static int countOf1s(int[] arr)
	{
		int count = 0;
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == 1)
				count++;
		return count;
	}
	
	/*
	 * brute force approach
	 * 
	 * time complexity: O(m * n)
	 * space complexity: O(m * n)
	 * auxiliary space complexity: O(1)
	 */
	
	public static int rowWithMax1s1(int[][] arr)
	{
		int count = 0, rowIndex = -1;
		for (int i = 0; i < arr.length; i++)
		{
			int row1sCount = countOf1s(arr[i]);
			if (row1sCount > count)
			{
				count = row1sCount;
				rowIndex = i;
			}
		}
		return rowIndex;
	}
	
	/* expected approach
	 * 
	 * time complexity: O(m + n)
	 * space complexity: O(m * n)
	 * auxiliary space complexity: O(1)
	 */
	
	public static int rowWithMax1s2(int[][] arr)
	{
		int m = arr.length, n = arr[0].length, j = n - 1, rowIndex = -1;
		
		for (int i = 0; i < m; i++)
		{
			while (j >= 0)
			{
				if (arr[i][j] == 1)
				{
					rowIndex = i;
					j--;
				}
				else
					break;
			}
		}
		return rowIndex;
	}

	public static void main(String[] args) {
		int[][] arr = {
					   {0, 1, 1, 1},
				       {0, 0, 1, 1},
				       {1, 1, 1, 1},
				       {0, 0, 0, 0}
				       };
		//int[][] arr = {{0, 0}, {0, 0}};
		System.out.println(rowWithMax1s2(arr));
	}

}
