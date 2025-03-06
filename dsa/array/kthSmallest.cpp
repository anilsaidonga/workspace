#include <iostream>
#include <algorithm>
#include <queue>
using namespace std;

/*
    brute force approach
    time: O(n*logn)
    space: O(logn)
*/

int kthSmallest1(int arr[], int n, int k)
{
    sort(arr, arr + n);
    return arr[k - 1];
}

/*
    optimized approach
    time: O(k*logk) + O((n - k)*logk)
          O(k*logk) + O(n*logk) - O(k*logk)
          O(n*logk)
    space: O(k)
    same for best, average and worst
*/

int kthSmallest2(int arr[], int n, int k)
{
    priority_queue<int> pq;
    for (int i = 0; i < k; i++)
        pq.push(arr[i]);
    for (int j = k; j < n; j++)
    {
        if (arr[j] < pq.top())
        {
            pq.pop();
            pq.push(arr[j]);
        }
    }
    return pq.top();
}

int partition(int arr[], int low, int high)
{
    int pivotIndex = low;
    int i = low + 1;

    for (int j = i; j <= high; j++)
    {
        if (arr[j] <= arr[pivotIndex])
        {
            swap(arr[i], arr[j]);
            i++;
        }
    }

    swap(arr[pivotIndex], arr[i - 1]);
    return i - 1;
}

int quickSelect(int arr[], int low, int high, int k)
{
    if (low <= high)
    {
        int pivotIndex = partition(arr, low, high);
        if (pivotIndex == k)
        {
            return arr[k];
        }
        if (pivotIndex > k)
        {
            return quickSelect(arr, low, pivotIndex - 1, k);
        }
        if (pivotIndex < k)
        {
            return quickSelect(arr, pivotIndex + 1, high, k);
        }
    }
    return -1;
}

/*
    expected approach 2
    time:
        average case: O(nlogn)
        worst case: O(n^2)
    
    space:
        average case: O(logn)
        worst case: O(n)
*/

int kthSmallest3(int arr[], int n, int k)
{
    return quickSelect(arr, 0, n - 1, k - 1);
}

int main(void)
{
    int n, k;
    cin >> n;
    int arr[n];
    for (int i = 0; i < n; i++)
    {
        int val;
        cin >> val;
        arr[i] = val;
    }
    cin >> k;
    cout << kthSmallest2(arr, n, k) << endl;
    return 0;
}