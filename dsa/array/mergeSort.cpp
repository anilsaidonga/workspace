#include <iostream>
using namespace std;

void mergeSort(int arr[], int n);
void sort(int arr[], int start, int end);
void merge(int arr[], int s1, int e1, int s2, int e2);

void mergeSort(int arr[], int n)
{
	sort(arr, 0, n - 1);
}
void sort(int arr[], int start, int end)
{
	if (end > start)
	{
		int mid = (start + end) / 2;
		sort(arr, start, mid);
		sort(arr, mid + 1, end);
		merge(arr, start, mid, mid + 1, end);
	}
}
void merge(int arr[], int s1, int e1, int s2, int e2)
{
	int length = e2 - s1 + 1;
	int tempArr[length] = {0};
	int i = s1;

	while (e1 >= s1 && e2 >= s2)
	{
		if (arr[s1] <= arr[s2])
		{
			tempArr[i] = arr[s1];
			s1++;
			i++;
		}
		else
		{
			tempArr[i] = arr[s2];
			s2++;
			i++;
		}
	}
	while (e1 >= s1)
	{
		tempArr[i] = arr[s1];
		s1++;
		i++;
	}
	while (e2 >= s2)
	{
		tempArr[i] = arr[s2];
		s2++;
		i++;
	}
	for (int i = e2, j = 0; j < length; j++, i--)
	{
		arr[i] = tempArr[i];
	}
}


