#include <iostream>
#include <cmath>
using namespace std;

int main(void)
{
	long long n, count = 0;
	cin >> n;
	for (int i = 5; n >= i; i = i * 5)
	{
		count = count + floor(n/i);
	}
	cout << count << endl;
	return 0;
}