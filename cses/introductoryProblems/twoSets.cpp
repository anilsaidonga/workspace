#include <iostream>
#include <vector>
#include <numeric>
using namespace std;

int main(void)
{
	long long n;
	cin >> n;
	long long sum = n * (n + 1)/2;
	if (sum % 2 != 0)
	{
		cout << "NO" << endl;
	}
	else
	{
		cout << "YES" << endl;
		long long a, b;
		vector<long long> v1, v2;
		if (n % 2 != 0)
			a = 1, b = n - 1;
		else
			a = 1, b = n;
		while (a < b)
		{
			v1.push_back(a);
			v1.push_back(b);
			a++;
			b--;
			if (a < b)
			{
				v2.push_back(a);
				v2.push_back(b);
				a++;
				b--;
			}
			
		}
		if (n % 2 != 0)
		{
			if (v1.size() < v2.size())
				v1.push_back(n);
			else
				v2.push_back(n);
		}
		cout << v1.size() << endl;
		int s1 = v1.size();
		for (int i = 0; i < s1; i++)
		{
			cout << v1[i] << " ";
		}
		cout << "\n";
		cout << v2.size() << endl;
		int s2 = v2.size();
		for (int j = 0; j < s2; j++)
		{
			cout << v2[j] << " ";
		}
		return 0;
	}
}