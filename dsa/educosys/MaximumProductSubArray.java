package educosys;

public class MaximumProductSubArray {

    public static int maxProduct(int[] nums) {
        int n = nums.length;
        int maxProduct = Integer.MIN_VALUE;
        int prefix = 1, suffix = 1;
        for (int i = 0; i < n; i++) {
            prefix *= nums[i];
            suffix *= nums[n - i - 1];
            maxProduct = Math.max(maxProduct, Math.max(prefix, suffix));
            if (nums[i] == 0) prefix = 1;
            if (nums[i] == 0) suffix = 1;
        }
        return maxProduct;
    }

    public static void main(String[] args) {
        int[] nums = {-2, 0, -1};
        System.out.println(maxProduct(nums));
    }
}