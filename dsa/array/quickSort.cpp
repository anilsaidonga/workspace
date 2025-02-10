#include <iostream>
#include <vector>
using namespace std;

void sortThis(vector<int> &v, int i, int j);

void quickSort(vector<int> &v, int i, int j);

int partition (vector<int> &v, int i, int j);

int main(void)
{
	vector<int> v = {5, 4, 3, 2, 1};
	int n = v.size();
	sortThis(v, 0, n - 1);
	for (int i = 0; i < n; i++)
		cout << v[i] << " ";
	return 0;
}

void sortThis(vector<int> &v, int i, int j)
{
	quickSort(v, i , j);
}

void quickSort(vector<int> &v, int i, int j)
{
	int pivotLocation = partition(v, i, j);
	quickSort(v, i, pivotLocation - 1);
	quickSort(v, pivotLocation + 1, j);
}

int partition (vector<int> &v, int i, int j)
{
	int pivotLocation = v[i];
	while (i <= j)
	{
		while (v[i] <= v[pivotLocation])
			i++;
		while (v[j] > v[pivotLocation])
			j--;
		if (i < j)
			swap(v[i],v[j]);
	}
	if (i > j)
	{
		swap(v[j], v[pivotLocation]);
	}
	return j;
}
