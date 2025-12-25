package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
	
	/*
	 *  brute force approach
	 *  time complexity: O(2^target * k) ( in worst case same element can be picked up target times, and there are k subsets as such)
	 *  space complexity: O(n) + O(x * k) ( recursion stack depth + x number of subSets * each subSet of length k )
	 */
	
//	public static void helper(int ind, int[] candidates, int target, List<Integer> curr, List<List<Integer>> res)
//	{
//		if (target == 0)
//		{
//			List<Integer> temp = new ArrayList<>(curr);
//			res.add(temp);
//			return;
//		}
//		
//		if (ind < 0 || target < 0) return;
//		
//		// include wala case
//		curr.add(candidates[ind]);
//		helper(ind , candidates, target - candidates[ind], curr, res);
//		curr.remove(curr.size() - 1);
//		
//		// exclude wala case
//		helper(ind - 1, candidates, target, curr, res);
//	}
//	
//    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
//        List<List<Integer>> res = new ArrayList<>();
//        List<Integer> curr = new ArrayList<>();
//        helper(candidates.length - 1, candidates, target, curr, res);
//        return res;
//    }
	
	/*
	 *  brute force approach
	 *  time complexity: O(2^target * k) ( in worst case same element can be picked up target times, and there are k subsets as such)
	 *  space complexity: O(n) + O(x * k) ( recursion stack depth + x number of subSets * each subSet of length k )
	 *  key take away: both time and space complexities for brute force and better approach are similar, but in reality sorting and pruning will be much faster 
	 *  				as it won't explore impossible paths.
	 */
	
//	public static void helper(int ind, int[] candidates, int target, List<Integer> curr, List<List<Integer>> res)
//	{
//		if (target == 0)
//		{
//			List<Integer> temp = new ArrayList<>(curr);
//			res.add(temp);
//			return;
//		}
//		
//		if (ind > candidates.length || target < 0) return;
//		
//		for (int i = ind; i < candidates.length; i++)
//		{
//			if (candidates[i] > target) break;
//			
//			// include wala case
//			curr.add(candidates[i]);
//			helper(i , candidates, target - candidates[i], curr, res);
//			curr.remove(curr.size() - 1);
//	
//		}
//	
//	}
	
	public static void helper(int ind, int[] candidates, int target, List<Integer> curr, List<List<Integer>> res)
	{
		if (target == 0)
		{
			List<Integer> temp = new ArrayList<>(curr);
			res.add(temp);
			return;
		}
		
		if (ind < 0 || target < 0) return;
		
		for (int i = ind; i >= 0; i--)
		{
			if (candidates[i] > target) break;
			
			// include wala case
			curr.add(candidates[i]);
			helper(i , candidates, target - candidates[i], curr, res);
			curr.remove(curr.size() - 1);
	
		}
	
	}
	
	public static void sortInReverseOrder(int[] arr)
	{
		int i = 0, j = arr.length - 1;
		while(i > j)
		{
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
			i++;
			j--;
		}
	}
	
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        Arrays.sort(candidates);
        sortInReverseOrder(candidates);
        helper(candidates.length - 1, candidates, target, curr, res);
        return res;
    }

	public static void main(String[] args) {
		int[] candidates = {2, 3, 6, 7};
		int target = 7;
		System.out.println(combinationSum(candidates, target));
	}

}
