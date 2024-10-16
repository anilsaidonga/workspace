/*#include<iostream>
#include<algorithm>
using namespace std;

long long answer(long long x, long long y)
{
	long long mx = max(x, y);
	if ((mx % 2) == 0)
	{
		if (y == 1)
			return (mx * mx);
		else if (x < mx)
			return (answer(mx, mx) - (mx - x));
		else if (x == mx)
			return ((mx * mx) - (y - 1));
	}
	else
	{
		if (x == 1)
			return (mx * mx);
		else if (y < mx)
			return (answer(mx, mx) - (mx - y));
		else if (y == mx)
			return ((mx * mx) - (x - 1));
	}
	return 0;
}

int main(void)
{
	int n;
	cin >> n;
	while (n--)
	{
		long long x = 0, y = 0;
		cin >> x >> y;
		cout << answer(x, y) << "\n";
	}
	return 0;
}*/

#include <iostream>
#include <algorithm>
using namespace std;

int answer(int x, int y)
{
	int mx = max(x, y);
	if ((mx % 2) == 0)
	{
		if (x == 1)
			return (mx * mx);
		else if (y < mx)
			return (answer(mx, mx) - (mx - y));
		else if (y == mx)
			return (mx * mx) - (x - 1);
	}
	else
	{
		if (y == 1)
			return (mx * mx);
		else if (x < mx)
			return (answer(mx, mx) - (mx - x));
		else if (x == mx)
			return (mx * mx) - (y - 1);
	}
	return 0;
}

int main(void)
{
	int n;
	cin >> n;
	while (n--)
	{
		int x, y;
		cin >> x >> y;
		cout << answer(x, y) << endl;
	}
	return 0;
}