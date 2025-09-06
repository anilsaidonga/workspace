package algorithms;

public class Try {

	public static void main(String[] args) {
		int[] arr = {1, 2, 3};
		int n = arr.length;
		for (int i = 0; i < n; i++)
		{
			for (int j = i; j < n; j++)
			{
				System.out.print(arr[j] + " ");
			}
			System.out.println();
		}
	}

}
