#include <iostream>
#include <vector>
using namespace std;

/*
 * tabulation (bottom up)
 * time complexity: O(2^n)
 * space complexity: O(n)
 */

int helper1(vector<int>& v, int ind)
{
	if (ind >= static_cast<int>(v.size())) return 0;
	int include = v[ind] + helper1(v, ind + 2);
	int exclude = helper1(v, ind + 1);
	return max(include, exclude);
}

int rob1(vector<int>& v)
{
	return helper1(v, 0);
}

/*
 * memoization (top down)
 * time complexity: O(2^n)
 * space complexity: O(n)
 */

int helper2(vector<int>& v, int ind)
{
	if (ind < 0) return 0;
	int include = v[ind] + helper2(v, ind - 2);
	int exclude = helper2(v, ind - 1);
	return max(include, exclude);
}

int rob2(vector<int>& v)
{
	return helper2(v, v.size() - 1);
}

int main(void)
{
	vector<int> v = {2, 7, 9, 3, 1};
	cout << rob2(v) << endl;
	return 0;
}
