#include <iostream>
#include <vector>
using namespace std;

/*
 * find min in a sorted rotated array using binary search
 * time complexity: O(logn)
 * space complexity: O(1)
 * 
 * initialize l and r
 * compare mid value with the right most value and adjust boundaries
 * run while loop till l < r
 * if mid value < right most value:
 *      r = mid
 * if mid value > right most value:
 *      l = mid + 1
 * return l
 */

int findMinInSortedAndRotatedArray(vector<int> &arr)
{
    int l = 0, r = arr.size() - 1;
    while (l < r)
    {
        // if arr[l] < arr[r], then array is already sorted
        if (arr[l] < arr[r])
            return l;

        int mid = l + (r - l)/2;
        if (arr[mid] < arr[r])
        {
            r = mid;
        }
        if (arr[mid] > arr[r])
        {
            l = mid + 1;
        }

    }
    return l;
}

int main(void)
{
    vector<int> arr = {5, 6, 1, 2, 3, 4};
    cout << findMinInSortedAndRotatedArray(arr) << endl;
    return 0;
}
