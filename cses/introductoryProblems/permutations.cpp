#include <iostream>
using namespace std;

int main(void)
{
	int n;
	cin >> n;
	if (n == 1)
	{
		cout << n << endl;
	}
	else if (n <= 3)
	{
		cout << "NO SOLUTION" << endl;
	}
	else
	{
		int a = n, b = n - 1;
		while (b > 0)
		{
			cout << b << " ";
			b = b - 2;
		}
		while (a > 0)
		{
			cout << a << " ";
			a = a - 2;
		}
	}
	return 0;
}