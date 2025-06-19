package educosys;

import java.util.List;
import java.util.ArrayList;

public class Combinations {

    public static void helper(int ind, int n, int k, List<Integer> curr, List<List<Integer>> res)
    {
        if (curr.size() == k)
        {
            res.add(new ArrayList<>(curr));
            return;
        }

        if (ind > n ) return;

        // include wala case
        curr.add(ind);
        helper(ind + 1, n, k, curr, res);
        curr.remove(curr.size() - 1);

        // exclude wala case
        helper(ind + 1, n, k, curr, res);
    }

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        helper(1, n, k, curr, res);
        return res;
    }

    public static void main(String[] args)
    {
        List<List<Integer>> res = combine(3, 2);
        for (List<Integer> list : res)
        {
            for (Integer val : list)
            {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    
}
