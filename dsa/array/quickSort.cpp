#include <iostream>
#include <vector>
using namespace std;

void sort(vector<int> &arr, int n);

void quickSort(vector<int> &arr, int start, int end);

int partition(vector<int> &arr, int start, int end);

int main(void)
{
    vector<int> arr = {8, 7, 6, 5, 4, 3, 2, 1};
    int n = arr.size();
    sort(arr, n);
    for (int i = 0; i < n; i++)
        cout << arr[i] << endl;
    return 0;
}

void sort(vector<int> &arr, int n)
{
    quickSort(arr, 0, n - 1);
}

void quickSort(vector<int> &arr, int start, int end)
{
    if (start == end)
    {
        return;
    }
    int pivotLocation = partition(arr, start, end);
    quickSort(arr, start, pivotLocation - 1);
    quickSort(arr, pivotLocation + 1, end);
}

int partition(vector<int> &arr, int start, int end)
{
    int pivotIndex = start;
    while (end >= start)
    {
        while (arr[start] <= arr[pivotIndex] && start <= end)
            start++;
        while (arr[end] > arr[pivotIndex] && start <= end)
            end--;
        if (start < end)
            swap(arr[start], arr[end]);
    }
    if (start > end)
        swap(arr[end], arr[pivotIndex]);
    return end;
}