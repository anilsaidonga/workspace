#include <iostream>
#include <vector>
using namespace std;

/*
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
*/

    void helper(vector<int>& nums, vector<bool>& used, vector<int>& curr, vector<vector<int>>& res) {
        if (curr.size() == nums.size()) {
            res.push_back(curr);  // One valid permutation formed
            return;
        }

        for (int i = 0; i < nums.size(); ++i) {
            if (used[i]) continue;             // Skip used elements

            used[i] = true;                    // Mark element as used
            curr.push_back(nums[i]);          // Choose element

            helper(nums, used, curr, res);     // Recurse

            curr.pop_back();                  // Backtrack
            used[i] = false;                  // Unmark element
        }
    }

    vector<vector<int>> permute(vector<int>& nums) {
        vector<vector<int>> res;
        vector<int> curr;
        vector<bool> used(nums.size(), false);
        helper(nums, used, curr, res);
        return res;
    }

int main(void) {
        vector<int> nums = {1,2};
        vector<vector<int>> res = permute(nums);
        return 0;
    }
