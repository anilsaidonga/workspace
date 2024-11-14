#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void reverseAString(vector<char> &v) // Time: O(n), Space: O(1)
{
	reverse(v.begin(), v.end());
}

int main(void)
{
	vector<char> v;
	int n;
	cin >> n;
	while (n--)
	{
		char c;
		cin >> c;
		v.push_back(c);
	}

	reverseAString(v);
	for (int i = 0; i < (int) v.size(); i++)
		cout << v[i];
}
