#include <bits/stdc++.h>
using namespace std;

int main(void)
{
	int n;
	cin >> n;

	/* pattern 1

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
		{
			cout << "* "; 
		}
		cout << "\n";
	}*/

	/* pattern 2

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < (i + 1); j++)
		{
			cout << "* ";
		}
		cout << "\n";
	}*/

	/* pattern 3

	for (int i = 0; i < n; i++)
	{
		for (int j = 1; j <= (i + 1); j++)
		{
			cout << j << " ";
		}
		cout << "\n";
	}*/

	/* pattern 4

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j <= i; j++)
		{
			cout << (i + 1) << " ";
		}
		cout << "\n";
	}*/

	/* pattern 5

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < (n - i); j++)
		{
			cout << "* ";
		}
		cout << "\n";
	}*/

	/* pattern 6

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < (n - i); j++)
		{
			cout << (j + 1) << " ";
		}
		cout << "\n";
	}*/

	/*pattern 7

	for (int i = 0; i < n; i++)
	{
		int stars = 2 * (i + 1) - 1;
		int spaces = n - (i + 1);
		while (spaces--)
		{
			cout << "  ";
		}
		while (stars--)
		{
			cout << "* ";
		}
		cout << "\n";
	}*/

	/*pattern 8

	for (int i = n; i > 0; i--)
	{
		int stars = (2 * i) - 1;
		int spaces = n - i;
		while (spaces--)
		{
			cout << "  ";
		}
		while (stars--)
		{
			cout << "* ";
		}
		cout << "\n";
	}*/

	return 0;
}