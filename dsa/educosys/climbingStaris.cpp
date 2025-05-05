#include <iostream>
#include <vector>
using namespace std;

/*
 * tabulation (bottom up)
 * time complexity: O(n)
 * space complexity: O(1)
 */

int climbStairs1(int n)
{
	if (n <= 2) return n;
	int prevPrev = 1, prev = 2;
	for (int i = 2; i < n; i++)
	{
		int curr = prev + prevPrev;
		prevPrev = prev;
		prev = curr;
	}
	return prev;
}

/* memoization (top down)
 * time complexity: O(n)
 * space complexity: O(n) + O(n) = O(n)
 * 					array	recursive calls
*/

int climbStairs2(int n, vector<int>& arr)
{
	if (n <= 2) return n;
	if (arr[n] != -1) return arr[n];
	else
		arr[n] = climbStairs2(n - 1, arr) + climbStairs2(n - 2, arr);
	return arr[n];
}

int main(void)
{
	vector<int> arr(3 + 1, -1);
	cout << climbStairs2(3, arr) << endl;
	return 0;
}
