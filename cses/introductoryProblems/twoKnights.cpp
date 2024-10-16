#include <iostream>
using namespace std;

long long answer(long long n)
{
	return ((n * n) * (n * n - 1)/2) - (2 * (n - 2) * (n - 1) + 2 * (n - 1) * (n - 2));
}

int main(void)
{
	long long n;
	cin >> n;
	long long k = 1;
	while (k <= n)
	{
		cout << answer(k) << "\n";
		k++;
	}
	return 0;
}