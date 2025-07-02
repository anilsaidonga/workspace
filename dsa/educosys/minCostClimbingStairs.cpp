#include <iostream>
#include <vector>
using namespace std;

/*
 * recursion
 * time complexity: O(2^n) calling helper twice within helper
 * space complexity: O(n) recursive stack
 * /

// int helper(vector<int> &cost, int ind) {
//     if (ind >= cost.size()) {
//         return 0;
//     }
//     return cost[ind] + min(helper(cost, ind + 1), helper(cost, ind +2));
// }
//
// int minCostClimbingStairs(vector<int> &cost) {
//     return min(helper(cost, 0), helper(cost, 1));
// }

/*
 * recursion with memoization ( top down approach )
 * time complexity: O(n)
 * space complexity: O(n) + O(n) ( dp vector + recursive stack )
 */

// int helper(vector<int>& cost, int ind, vector<int>& dp) {
//     if (ind >= cost.size()) {
//         return 0;
//     }
//     if (dp[ind] != -1) return dp[ind];
//
//     return dp[ind] = cost[ind] + min(helper(cost, ind + 1, dp), helper(cost, ind + 2, dp));
// }
//
// int minCostClimbingStairs(vector<int> &cost) {
//     vector<int> dp(cost.size() + 1, -1);
//     return min(helper(cost, 0, dp), helper(cost, 1, dp));
// }

/*
 * tabulation ( bottom up approach )
 * time complexity: O(n)
 * space complexity: O(n) ( dp vector )
 */

// int minCostClimbingStairs(vector<int>& cost) {
//     vector<int> dp(cost.size() + 1, -1);
//     dp[0] = 0, dp[1] = 0;
//     for (int i = 2; i <= cost.size(); i++) {
//         dp[i] = min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
//     }
//     return dp[cost.size()];
// }

/*
 * tabulation with space optimization
 * time complexity: O(n) (for loop)
 * space complexity: O(1) ( only variables )
 */

int minCostClimbingStairs(vector<int>& cost) {
    int prevPrev = 0, prev = 0, curr = 0;
    for (int i = 2; i <= cost.size(); i++) {
        curr = min(prev + cost[i - 1], prevPrev + cost[i - 2]);
        prevPrev = prev;
        prev = curr;
    }
    return prev;
}

int main(void) {
    vector<int> cost = {10, 15, 20};
    cout << minCostClimbingStairs(cost) << endl;
    return 0;
}
