#include <iostream>
#include <vector>
using namespace std;

int main(void)
{
	int n, val, x;
	vector<int> v;
	cin >> n;
	while (n--)
	{
		cin >> val;
		v.push_back(val);
	}
	cin >> x;

	// two pointer approach

	int first = 0, last = v.size() - 1;

	while (v[first] != x)
		first++;

	while (v[last] != x)
		last--;

	cout << "first occurence of " << x << " is at " << (first + 1) << " " << "and last occurence of " << x << " is at " << (last + 1) << endl;

	return 0;
}
