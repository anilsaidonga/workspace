#include <iostream>
using namespace std;

const int mod = (int)(1e9 + 7);

int main(void)
{
	long long n, ans = 1;
	cin >> n;
	for (int i = 0; i < n; i++)
	{
		ans *= 2;
		ans %= mod;
	}
	cout << ans << endl;
	return 0;
}