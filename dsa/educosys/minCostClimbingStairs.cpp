#include <iostream>
#include <vector>
using namespace std;

/*
 * tabulation (bottom up)
 * time complexity: O(2^n)
 * space complexity: O(n)
 */

int helper1(vector<int>& cost, int ind)
{
	if (ind >= static_cast<int>(cost.size())) return 0;
	return cost[ind] + min(helper1(cost, ind + 1), helper1(cost, ind + 2));
}

int minCostClimbingStairs1(vector<int>& cost)
{
	return min(helper1(cost, 0), helper1(cost, 1));
}

/*
 * memoization (top down)
 * time complexity: O(2^n)
 * space complexity: O(n)
 */

int helper2(vector<int>& cost, int ind)
{
	if (ind < 0) return 0;
	return cost[ind] + min(helper2(cost, ind - 1), helper2(cost, ind - 2));
}

int minCostClimbingStairs2(vector<int>& cost)
{
	return min(helper2(cost, cost.size() - 1), helper2(cost, cost.size() - 2));
}

int main(void)
{
	vector<int> cost = {1,100,1,1,1,100,1,1,100,1};
	cout << minCostClimbingStairs2(cost) << endl;
}
