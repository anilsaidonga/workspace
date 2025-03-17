#include <iostream>
#include <vector>
#include <queue>
using namespace std;

class sortingAlgorithms
{
public:
	/*
	time:
	--------------------
	best case: Ω(n)
	worst case: O(n^2)

	space: O(1)
	*/
	static void bubbleSort(vector<int>& arr, int n)
	{
		int swapCounter = -1;
		while (swapCounter != 0)
		{
			swapCounter = 0;
			for (int i = 0; i < n - 1; i++)
			{
				if (arr[i] > arr[i + 1])
				{
					swap(arr[i], arr[i + 1]);
					swapCounter++;
				}
			}
		}
	}

	/*
	time:
	----------------
	best case: Ω(n^2)
	worst case: O(n^2)

	space: O(1)
	*/
	static void selectionSort(vector<int>& arr, int n)
	{
		for (int i = 0; i < n; i++)
		{
			int minIndex = i;
			for (int j = i + 1; j < n; j++)
			{
				if (arr[j] < arr[i])
					minIndex = j;
			}
			if (i != minIndex)
				swap(arr[i], arr[minIndex]);
		}
	}

	/*
	time:
	-------------------
	best case: Ω(n)
	worst case: O(n^2)

	space: O(1)
	*/

	static void insertionSort(vector<int>& arr, int n)
	{
		for (int i = 1; i < n; i++)
		{
			// take the sorted value out into a variable
			int val = arr[i];

			// take sorted index
			int j = i - 1;

			while (j >= 0 && arr[j] > val)
			{
				arr[j + 1] = arr[j];
				j--;
			}

			arr[j + 1] = val;
		}
	}

	/*
	time:
	-------------
	best case: Ω(n*logn)
	worst case: O(n*logn)

	space: O(n)
	*/

	static void mergeSort(vector<int> &arr, int n)
	{
		sort1(arr, 0, n - 1);
	}

	static void sort1(vector<int> &arr, int i, int j)
	{
		if (i < j)
		{
			int mid = (i + j)/2;
			sort1(arr, i, mid);
			sort1(arr, mid + 1, j);
			merge(arr, i, mid, mid + 1, j);
		}
	}

	static void merge(vector<int> &arr, int s1, int e1, int s2, int e2)
	{
		vector<int> v;
		int startIndex = s1, j = 0;

		while (e1 >= s1 && e2 >= s2)
		{
			if (arr[s1] <= arr[s2])
			{
				v.push_back(arr[s1]);
				s1++;
			}
			else
			{
				v.push_back(arr[s2]);
				s2++;
			}
		}
		while (e1 >= s1)
		{
			v.push_back(arr[s1]);
			s1++;
		}
		while (e2 >= s2)
		{
			v.push_back(arr[s2]);
			s2++;
		}

		for (int i = startIndex; i < v.size(); i++)
		{
			arr[i] = v[j];
			j++;
		}
	}

	/*
	time:
	--------------------------
	best case: Ω(n*logn)
	worst case: O(n^2)

	space:
	-------------------------
	best case: Ω(logn)
	worst case: O(n)
	*/

	static void quickSort(vector<int> &arr, int n)
	{
		sort2(arr, 0, n - 1);
	}

	static void sort2(vector<int> &arr, int i, int j)
	{
		if (i < j)
		{
			int pivotIndex = partition(arr, i, j);
			sort2(arr, i, pivotIndex - 1);
			sort2(arr, pivotIndex + 1, j);
		}
	}

	static int partition(vector<int> &arr, int low, int high)
	{
			int pivotIndex = high, i = low - 1;

			for (int j = low; j < high; j++)
			{
				if (arr[j] < arr[pivotIndex])
				{
					i++;
					swap(arr[j], arr[i]);
				}
			}

			swap(arr[i + 1], arr[pivotIndex]);
			return (i + 1);
	}

	/*
	time:
	---------------------
	best case: Ω(n*logn)
	average case: Ω(n*logn)
	worst case: O(n*logn)

	space:
	--------------------
	O(1) - auxiliary space complexity
	*/

	static void heapSort(vector<int> &arr, int n)
	{
		// by default priority_queue is max heap, if we pass comparator we can create a min heap
		priority_queue<int, vector<int>, greater<int>> pq;
		for (int i = 0; i < n; i++)
		{
			pq.push(arr[i]);
		}
		arr.clear();
		for (int i = 0; i < n; i++)
		{
			arr.push_back(pq.top());
			pq.pop();
		}
	}
};

int main(void)
{
	int n;
	cin >> n;
	vector<int> arr;
	for (int i = 0; i < n; i++)
	{
		int val;
		cin >> val;
		arr.push_back(val);
	}
	sortingAlgorithms::heapSort(arr, n);
	for (int i = 0; i < n; i++)
		cout << arr[i] << endl;
}