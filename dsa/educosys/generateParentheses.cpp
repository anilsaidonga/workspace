#include <iostream>
#include <vector>
using namespace std;

/*
 * bottom up approach
 * time complexity: O(2^2n) ↔ O (2^n)
 * space complexity: O(2n) ↔ O(n)
 */

void helper1(int n, int o, int c, string curr, vector<string>& res)
{
	if (c == n) 
	{
		res.push_back(curr);
		return;
	}
	// pick open parentheses
	if (o < n)
	{
		helper1(n, o + 1, c, curr + '(', res);
	}
	// pick closed parentheses
	if (o > c)
	{
		helper1(n, o, c + 1, curr + ')', res);
	}
}

/* 
 * top down approach
 * time complexity: O(2^2n) ↔ O(2^n)
 * space complexity: O(2n) ↔ O(n)
 */

vector<string> generateParenthesis1(int n)
{
	vector<string> res;
	helper1(n, 0, 0, "", res);
	return res;
}

void helper2(int n, int o, int c, string curr, vector<string>& res)
{
	if (c == 0)
	{
		res.push_back(curr);
		return;
	}
	if (o > 0)
	{
		helper2(n, o - 1, c, curr + '(', res);
	}
	if (o < c)
	{
		helper2(n, o, c - 1, curr + ')', res);
	}
}

vector<string> generateParenthesis2(int n)
{
	vector<string> res;
	helper2(n, n, n, "", res);
	return res;
}

int main(void)
{
	vector<string> ans = generateParenthesis2(3);
	for (int i = 0; i < static_cast<int>(ans.size()); i++)
		cout << ans[i] << endl;
	return 0;
}
