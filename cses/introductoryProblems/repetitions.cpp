#include <iostream>
using namespace std;

int main(void)
{
	string s;
	cin >> s;

	int count = 1;
	int length = s.size();

	for (int i = 0; i < length; i++)
	{
		int j = i + 1, tempCount = 1;
		for ( ; s[i] == s[j]; j++)
		{
			tempCount++;
		}
		count = max(count, tempCount);
		i = j - 1;
	}
	cout << count << endl;
}