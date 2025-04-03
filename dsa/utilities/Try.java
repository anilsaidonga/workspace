package utilities;

public class Try {

	public static void main(String[] args) {
		int[] arr = {0, 0, 1, 1, 1, 1};
		System.out.println(Utilities.lastOccurence(arr, 1) - Utilities.firstOccurence(arr, 1) + 1);
	}

}
