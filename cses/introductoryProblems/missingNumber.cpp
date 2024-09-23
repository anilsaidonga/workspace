#include <iostream>
using namespace std;

int main(void)
{
	int n;
	cin >> n;
	int sum = n * (n + 1)/2;
	for (int i = 0; i < (n - 1); i++)
	{
		int num;
		cin >> num;
		sum = sum - num;
	}
	cout << sum << endl;
	return 0;
}
