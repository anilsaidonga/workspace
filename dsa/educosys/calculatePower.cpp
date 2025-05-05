#include <iostream>
#include <climits>
#include <chrono> // for high_resolution_clock
using namespace std;

int calculatePower1(int x, int n)
{
	if (n == 0)
		return 1;
	return x * calculatePower1(x, n - 1);
}

int calculatePower2(int x, int n)
{
	if (n == 0) return 1;
	int res = calculatePower2(x, n/2);
	if (n % 2 == 0)
	return res * res;
	return res * res * x;
}

int main(void)
{
	auto start = chrono::high_resolution_clock::now(); // Start timer
	cout << calculatePower1(2, 30) << endl;
	auto end = chrono::high_resolution_clock::now();				  // End timer
	auto duration = chrono::duration_cast<chrono::microseconds>(end - start); // Calculate duration
	cout << "Execution Time: " << duration.count() << " microseconds" << endl;

	start = chrono::high_resolution_clock::now(); // Start timer
	cout << calculatePower2(2, 30) << endl;
	end = chrono::high_resolution_clock::now();				  // End timer
	duration = chrono::duration_cast<chrono::microseconds>(end - start); // Calculate duration
	cout << "Execution Time: " << duration.count() << " microseconds" << endl;
	return 0;
}
