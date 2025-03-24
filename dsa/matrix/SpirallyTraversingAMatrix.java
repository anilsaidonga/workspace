package matrix;

public class SpirallyTraversingAMatrix {
	
	/*
	 * 
	 * boundary traversal
	 * time: O(m*n)
	 * space: O(m*n)
	 * 
	 */
	public static int[] spirallyTraverse(int[][] arr)
	{
		int rows = arr.length, columns = arr[0].length, index = 0;
		int[] result = new int[rows * columns];
		
		int columnStart = 0, columnEnd = columns - 1, rowStart = 0, rowEnd = rows - 1;
		
		while (columnStart <= columnEnd && rowStart <= rowEnd)
		{
			for (int i = columnStart; i <= columnEnd; i++)
			{
				result[index] = arr[rowStart][i];
				index++;
			}
			rowStart++;
			for (int i = rowStart; i <= rowEnd; i++)
			{
				result[index] = arr[i][columnEnd];
				index++;
			}
			columnEnd--;
			for (int i = columnEnd; i >= columnStart; i--)
			{
				result[index] = arr[rowEnd][i];
				index++;
			}
			rowEnd--;
			for (int i = rowEnd; i >= rowStart; i--)
			{
				result[index] = arr[i][columnStart];
				index++;
			}
			columnStart++;
		}
		return result;
	}
	
	public static void main(String[] args)
	{
		int[][] arr = {{1, 2, 3, 4},
						{5, 6, 7, 8},
						{9, 10, 11, 12},
						{13, 14, 15, 16}};
		System.out.println(spirallyTraverse(arr));
	}
}
