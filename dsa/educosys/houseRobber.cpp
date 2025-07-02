#include <iostream>
#include <vector>
using namespace std;

/*
 * recursion
 * time complexity: O(2^n)
 * space complexity: O(n)
 */

// int helper(vector<int>& cost, int ind)
// {
//     if (ind >= cost.size())
//     {
//         return 0;
//     }
//
//     // include wala case
//     int inc = cost[ind] + helper(cost, ind + 2);
//
//     // exclude wala case
//     int exc = helper(cost, ind + 1);
//
//     return max(inc, exc);
// }
//
// int rob(vector<int>& cost)
// {
//     return helper(cost, 0);
// }
//
// int main(void)
// {
//     vector<int> cost = {10, 15, 25};
//     cout << rob(cost) << endl;
//     return 0;
// }

int helper(vector<int>& cost, int ind, vector<int>& dp) {

    if (ind >= cost.size()) return 0;

    if (dp[ind] != -1) return dp[ind];

    // include wala case
    int inc = cost[ind] + helper(cost, ind + 2, dp);

    // exclude wala case
    int exc = helper(cost, ind + 1, dp);

    return dp[ind] = max(inc, exc);
}

int rob(vector<int>& cost) {
    vector<int> dp(cost.size(), -1);
    return helper(cost, 0, dp);
}

int main(void) {
    vector<int> cost = {10, 15, 25};
    cout << rob(cost) << endl;
    return 0;
}
