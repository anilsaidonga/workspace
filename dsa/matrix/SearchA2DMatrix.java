package matrix;

public class SearchA2DMatrix {
	
	public static boolean searchA2DMatrix(int[][] arr, int k)
	{
		int rows = arr.length, columns = arr[0].length;
		return binarySearch(arr, 0, (rows * columns) - 1, k);
	}
	
	/*
	 * time complexity: O(log(m*n)
	 * space complexity: O(m*n)
	 * auxiliary space complexity: O(log(m*n))
	 */
	public static boolean binarySearch(int[][] arr, int l, int r, int k)
	{
		int n = arr[0].length;
		if (l <= r)
		{
			int mid = (l + r) / 2, row = mid / n, column = mid % n;
			if (arr[row][column] == k)
				return true;
			if (arr[row][column] > k)
				return binarySearch(arr, l, mid - 1, k);
			if (arr[row][column] < k)
				return binarySearch(arr, mid + 1, r, k);
		}
		return false;
	}
	
	public static void main(String[] args) {
		int[][] arr = {{1,3,5,7},
					   {10,11,16,20},
					   {23,30,34,60}};
		int k = 3;
		System.out.println(searchA2DMatrix(arr, k));
	}

}
