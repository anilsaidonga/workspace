#include <iostream>
#include <vector>
using namespace std;

void helper(vector<int>& nums, int ind, vector<vector<int>>& res)
{
    if (ind >= static_cast<int>(nums.size()))
    {
        res.push_back(nums);
        return;
    }
   for (int i = ind; i < static_cast<int>(nums.size()); i++)
    {
        swap(nums[i], nums[ind]);
        helper(nums, ind + 1, res);
        swap(nums[i], nums[ind]);
    }
}

int main(void)
{
    vector<int> nums = {1, 2, 3};
    vector<vector<int>> res;
    helper(nums, 0, res);
    return 0;
}