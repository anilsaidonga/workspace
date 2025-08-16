#include <iostream>
#include <vector>
using namespace std;

/*
 * recursion using a curr array
 * time complexity: O(n * n!)
 * space complexity: O(n) + O(n) + O(n) ( extra vector + another extra vector + recursion stack)
 */
void helper1(vector<int>& nums, vector<bool>& used, vector<int>& curr, vector<vector<int>>& res) {
    // base condition
    if (curr.size() == nums.size())
    {
        res.push_back(curr);
        return;
    }

    for (int i = 0; i < nums.size(); i++)
    {
        if (used[i] == true) continue;
        used[i] = true;
        curr.push_back(nums[i]);
        helper1(nums, used, curr, res);
        curr.pop_back();
        used[i] = false;
    }
}

vector<vector<int>> permute1(vector<int>& nums) {
    vector<bool> used(nums.size(), false);
    vector<int> curr;
    vector<vector<int>> res;
    helper1(nums, used, curr, res);
    return res;
}

/*
 * recursion using in-place swapping
 * time complexity: O(n * n!) (we are calculating n permutations and storing each permutation of size n into res)
 * space complexity: O(n) (recursion stack)
 */

void helper2(vector<int>& nums, int ind, vector<vector<int>>& res)
{
    // base condition
    if (ind >= nums.size())
    {
        res.push_back(nums);
        return;
    }

    for (int i = ind; i < nums.size(); i++)
    {
        swap(nums[i], nums[ind]);
        helper2(nums, ind + 1, res);
        swap(nums[i], nums[ind]);
    }
}

vector<vector<int>> permute2(vector<int>& nums) {
    vector<vector<int>> res;
    helper2(nums, 0, res);
    return res;
}

int main(void) {
    vector<int> nums = {1, 2, 3};
    vector<vector<int>> res = permute2(nums);
    return 0;
}