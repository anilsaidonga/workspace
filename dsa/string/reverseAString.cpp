#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void reverseAString1(vector<char> &v) // Time: O(n), Space: O(1)
{
	reverse(v.begin(), v.end());
}

void reverseAString2(vector<char> &v) // Time : O(n), Space: O(1)
{
	int start = 0, end = v.size() - 1;
	while (end > start)
	{
		swap(v[start], v[end]);
		start++;
		end--;
	}
}

int main(void)
{
	int n;
	cin >> n;
	vector<char> v;
	while (n > 0)
	{
		char c;
		cin >> c;
		v.push_back(c);
		n--;
	}
	reverseAString2(v);
	for (int i = 0; i < (int) v.size(); i++)
		cout << v[i];
	return 0;
}
