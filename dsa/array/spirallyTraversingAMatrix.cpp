#include <iostream>
#include <vector>
using namespace std;

/*
	expected approach - using boundary taversal
	time: O(m * n)
	space: O(m * n)
*/

vector<int> spirallyTraverse(vector<vector<int>> &v)
{
	int rows = v.size(), columns = v[0].size();
	int top = 0, bottom = rows - 1, left = 0, right = columns - 1;
	vector<int> result;

	while (right >= left && top <= bottom)
	{
		for (int i = top; i <= right; i++)
		{
			result.push_back(v[top][i]);
		}
		top++;
		for (int i = top; i <= bottom; i++)
		{
			result.push_back(v[i][right]);
		}
		right--;
		for (int i = right; i >= left; i--)
		{
			result.push_back(v[bottom][i]);
		}
		bottom--;
		for (int i = bottom; i >= top; i--)
		{
			result.push_back(v[i][left]);
		}
		left++;
	}
	return result;
}

int main(void)
{
	int m, n, val;
	cin >> m >> n;
	vector<vector<int>> v;
	vector<int> result;
	for (int i = 0; i < m; i++)
	{
		vector<int> temp;
		for (int j = 0; j < n; j++)
		{
			cin >> val;
			temp.push_back(val);
		}
		v.push_back(temp);
	}
	result = spirallyTraverse(v);
	for (int i = 0; i < (m * n); i++)
	{
		cout << result[i] << endl;
	}
	return 0;
}