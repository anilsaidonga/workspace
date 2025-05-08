#include <iostream>
#include <vector>
using namespace std;

/*
 * idea is to have pivot sorted, meaning all elements less should be on it's left and 
 * all elements greater should be on it's right, and we return the pivot index
 */

int partition(vector<int> &arr, int l, int r)
{
    int pivot = arr[r];
    int i = l - 1;

    for (int j = l; j < r; j++)
    {
        if (arr[j] < pivot)
        {
            i++;
            swap(arr[i], arr[j]);
        }
    }

    i++;
    swap(arr[i], arr[r]);
    return i;
}

/*
 * time complexity: O(n^2) Ω(nlogn)
 * space complexity: O(n) Ω(logn)
*/

void quickSort(vector<int> &arr, int l, int r)
{
    if (l <= r)
    {
        int pivotIndex = partition(arr, l, r);
        quickSort(arr, l, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, r);
    }
}

int main(void)
{
    vector<int> arr = {5, 4, 3, 2, 1};
    quickSort(arr, 0, arr.size() - 1);
    return 0;
}