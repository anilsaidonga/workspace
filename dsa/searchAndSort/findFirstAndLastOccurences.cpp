#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

/*
 * time complexity: O(n)
 * space complexity: O(1)
 */

int* findFirstAndLastOccurences1(int arr[], int n, int x)
{
    int * result = new int[2];
    result[0] = -1;
    result[1] = -1;
    for (int i = 0; i < n; i++)
    {
        if (arr[i] == x)
        {
            if (result[0] == -1)
            {
                result[0] = i;
            }
            result[1] = i;
        }
    }
    return result;
}

/*
 * time complexity: O(logn)
 * space complexity: O(1)
 */

int* findFirstAndLastOccurences2(int arr[], int n, int x)
{
    int * result = new int[2];
    result[0] = -1;
    result[1] = -1;
    int l = 0, r = n - 1;
    while (l <= r)
    {
        int mid = l + (r - l)/2;
        if (arr[mid] == x)
        {
            result[0] = mid;
            r = mid - 1;
        }
        if (arr[mid] < x)
        {
            l = mid + 1;
        }
        if (arr[mid] > x)
        {
            r = mid - 1;
        }
    }

    l = 0, r = n - 1;
    while (l <= r)
    {
        int mid = l + (r - l)/2;
        if (arr[mid] == x)
        {
            result[1] = mid;
            l = mid + 1;
        }
        if (arr[mid] < x)
        {
            l = mid + 1;
        }
        if (arr[mid] > x)
        {
            r = mid - 1;
        }
    }
    return result;
}

/*
 * time complexity: O(logn)
 * space complexity: O(1)
 */

vector<int> findFirstAndLastOccurences3(vector<int> &arr,int x)
{
    vector<int> result = {-1, -1};
    // lower_bound points to the position that is >= x, else points to vector.end()
    auto start = lower_bound(arr.begin(), arr.end(), x);
    // upper_bound points to the position that is > x, else points to vector.end()
    auto end = upper_bound(arr.begin(), arr.end(), x);

    if (start != arr.end() && * start == x)
        result[0] = start - arr.begin();
    if (end != arr.end() && * (end - 1) == x)
        result[1] = end - arr.begin() - 1;

    return result;
}

int main(void)
{
    vector<int> arr = {1, 2, 2, 5};
    int n = sizeof(arr)/sizeof(arr[0]), x = 2;
    vector<int> result = findFirstAndLastOccurences3(arr, x);
    for (int i = 0; i < 2; i++)
        cout << result[i] << endl;
}
