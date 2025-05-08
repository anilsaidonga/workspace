#include <iostream>
#include <vector>
using namespace std;

int binarySearch(vector<int> &arr, int l, int r, int val)
{
    while (l <= r)
    {
        int mid = l + (r - l)/2;
        if (arr[mid] == val)
        {
            return mid;
        }
        if (arr[mid] < val)
        {
            l = mid + 1;
        }
        if (arr[mid] > val)
        {
            r = mid - 1;
        }
    }
    return -1;
}

int getMinIndexInRotatedAndSortedArray(vector<int> &arr)
{
    int l = 0, r = arr.size() - 1;
    while (l < r)
    {
        int mid = l + (r - l)/2;
        if (arr[mid] < arr[r])
            r = mid;
        if (arr[mid] > arr[r])
            l = mid + 1;
    }
    return l;
}

/*
 * time complexity: O(logn)
 * space complexity: O(1)
 */

int searchInRotatedAndSortedArray(vector<int> &arr, int val)
{
    int minIndex = getMinIndexInRotatedAndSortedArray(arr);
    
    if (arr[minIndex] == val)
        return minIndex;
    
    if (minIndex == 0)
        return binarySearch(arr, 0, arr.size() - 1, val);
    
    if (arr[0] <= val)
        return binarySearch(arr, 0, minIndex - 1, val);
    else
        return binarySearch(arr, minIndex + 1, arr.size() - 1, val);
}

int main(void)
{
    vector<int> arr = {4,5,6,7,0,1,2};
    cout << searchInRotatedAndSortedArray(arr, 4) << endl;
    return 0;
}
