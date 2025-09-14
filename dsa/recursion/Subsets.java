package recursion;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
	
	public static void helper(int ind, int[] arr, List<Integer> curr, List<List<Integer>> res)
	{
		if (ind == arr.length)
		{
			res.add(new ArrayList<>(curr));
			return;
		}
		
		// include wala case
		curr.add(arr[ind]);
		helper(ind + 1, arr, curr, res);
		curr.remove(curr.size() - 1);
		
		// exclude wala case
		helper(ind + 1, arr, curr, res);
	}

	public static void main(String[] args) {
		int[] arr = {1, 2, 3};
		List<List<Integer>> res = new ArrayList<>();
		List<Integer> curr = new ArrayList<>();
		helper(0, arr, curr, res);
	}

}
