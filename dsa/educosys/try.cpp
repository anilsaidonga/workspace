#include <iostream>
#include <vector>
using namespace std;

void helper(int n, int k, int val, vector<int>& curr, vector<vector<int>>& res)
{

	if (curr.size() == k)
	{
		res.push_back(curr);
		return;
	}
	// pick
	curr.push_back(val);
	helper(n, k, val + 1, curr, res);
	curr.pop_back();

	// don't pick
	helper(n, k, val + 1, curr, res);
}
vector<vector<int>> combine(int n, int k) {
	vector<vector<int>> res;
	vector<int> curr;
	helper(n, k, 1, curr, res);
	return res;
}