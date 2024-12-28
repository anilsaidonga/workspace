#include <iostream>
#include <climits>
#include "mergeSort.cpp"
using namespace std;

// brute force approach
/*
	
* sort the array and return the first and last elements as min and max

* Time : O(nlogn) as we using sorting algorithm

* Space : O(n) because we are using merge sort algorithm, that is sorting taking extra space.
		  if we use sort function defined in algorithm header then we can get O(1), as it is using in place sorting.

*/

pair<int, int> getMinMax1(int arr[], int n)
{
	mergeSort(arr, n);
	return {arr[0], arr[n - 1]};
}

// expected approach
pair<int, int> getMinMax2(int arr[], int n) // Time : O(n), Space : O(1)
{
	pair<int, int> p;
	int min = INT_MAX, max = INT_MIN;

	for (int i = 0; i < n; i++)
	{
		if (arr[i] < min)
			min = arr[i];
		if (arr[i] > max)
			max = arr[i];
	}
	
	p.first = min;
	p.second = max;

	return p;
	return {min, max};

}

int main(void)
{
	int n;
	cin >> n;
	int arr[n];

	for (int i = 0; i < n; i++)
	{
		int val;
		cin >> val;
		arr[i] = val;
	}

	pair<int, int> answer;

	answer = getMinMax1(arr, n);

	cout << "minimum : " << answer.first << endl;
	
	cout << "maximum : " << answer.second << endl;

	return 0;
}
