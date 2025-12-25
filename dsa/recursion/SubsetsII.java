package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubsetsII {
	
	/*
	 * brute force approach
	 * time complexity: O(2^n * n) + O(2^n * nlogn) + O(2^n * n)
	 * 					(curr to temp) + (sorting) + (add all subSets to setRes)
	 * space complexity: O(n) + O(2^n * n)
	 * 					(recursion depth) + (at any time 2^n * n space is used)
	 */
	
//	private static void helper(int ind, int[] nums, List<Integer> curr, Set<List<Integer>> setRes) {
//		if (ind < 0)
//		{
//			List<Integer> temp = new ArrayList<>(curr);
//			Collections.sort(temp);
//			setRes.add(temp);
//			return;
//		}
//		
//		// include wala case
//		curr.add(nums[ind]);
//		helper(ind - 1, nums, curr, setRes);
//		curr.remove(curr.size() - 1);
//		
//		// exclude wala case
//		helper(ind - 1, nums, curr, setRes);
//	}	
//	
//    public static List<List<Integer>> subsetsWithDup(int[] nums) {
//        List<List<Integer>> res = new ArrayList<>();
//        Set<List<Integer>> setRes = new HashSet<>();
//        List<Integer> curr = new ArrayList<>();
//        helper(nums.length - 1, nums, curr, setRes);
//        for (List<Integer> i : setRes) res.add(i);
//        return res;
//    }
	
	/*
	 * better approach
	 * time complexity: O(nlogn) + O(2^n * n) (sorting + generating all subSets of size n)
	 * space complexity: O(n) + O(2^n * n) (recursion depth + res)
	 */
	
	private static void helper(int ind, int[] nums, List<Integer> curr, List<List<Integer>> res) {
		res.add(new ArrayList<>(curr));
		
		for (int i = ind; i >= 0; i--)
		{
			// pruning
			if (i < ind && nums[i] == nums[i + 1]) continue;
			
			// include wala case
			curr.add(nums[i]);
			helper(i - 1, nums, curr, res);
			curr.remove(curr.size() - 1);
		}
	}		

	public static List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		List<Integer> curr = new ArrayList<>();
		Arrays.sort(nums);
		helper(nums.length - 1, nums, curr, res);
		return res;
	}
	
    
	public static void main(String[] args) {
		int[] nums = {1, 2, 2};
		System.out.println(subsetsWithDup(nums));
	}

}
