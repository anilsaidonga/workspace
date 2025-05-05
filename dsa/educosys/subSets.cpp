#include <iostream>
#include <vector>
using namespace std;

/*
 * bottom up approach (tabulation)
 * time complexity: O(2^n)
 * space complexity: O(n)
 */
void helper1(vector<int>& nums, int ind, vector<int>& curr, vector<vector<int>>& res)
{
	if (ind >= static_cast<int>(nums.size()))
	{
		res.push_back(curr);
		return;
	}
	// include
	curr.push_back(nums[ind]);
	helper1(nums, ind + 1, curr, res);
	curr.pop_back();

	// exclude
	helper1(nums, ind + 1, curr, res);
}

/*
 * top down approach (memoization)
 * time complexity: O(2^n)
 * space complexity: O(n)
 */

void helper2(vector<int>& nums, int ind, vector<int>& curr, vector<vector<int>>& res)
{
	if (ind < 0)
	{
		res.push_back(curr);
		return;
	}
	// include
	curr.push_back(nums[ind]);
	helper2(nums, ind - 1, curr, res);
	curr.pop_back();

	// exclude
	helper2(nums, ind - 1, curr, res);
}

int main(void)
{
	vector<int> nums = {0};
	vector<vector<int>> res;
	vector<int> curr;
	helper2(nums, static_cast<int>(nums.size() - 1), curr, res);
	for (int i = 0; i < static_cast<int>(res.size()); i++)
	{
		for (int j = 0; j < static_cast<int>(res[i].size()); j++)
		{
			cout << res[i][j] << " ";
		}
		cout << "\n";
	}
	
}
