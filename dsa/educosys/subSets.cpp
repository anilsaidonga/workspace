#include <iostream>
#include <vector>
using namespace std;

void helper(vector<int>& nums, int ind, vector<int>& curr, vector<vector<int>>& res)
{
    if (ind >= nums.size())
    {
        res.push_back(curr);
        return;
    }

    // include wala case
    curr.push_back(nums[ind]);
    helper(nums, ind + 1, curr, res);
    curr.pop_back();

    // exclude wala case
    helper(nums, ind + 1, curr, res);
}

vector<vector<int>> subsets(vector<int>& nums) {
    vector<int> curr;
    vector<vector<int>> res;
    helper(nums, 0, curr, res);
    return res;
}

int main(void) {
    vector<int> nums = {1, 2, 3};
    vector<vector<int>> res = subsets(nums);
    return 0;
}