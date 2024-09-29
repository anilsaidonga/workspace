#include <iostream>
using namespace std;

int main(void)
{
	long n;
	cin >> n;
	long sum = n * (n + 1)/2;
	for (int i = 0; i < (n - 1); i++)
	{
		long num;
		cin >> num;
		sum = sum - num;
	}
	cout << sum << endl;
	return 0;
}
