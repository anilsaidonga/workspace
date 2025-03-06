#include <iostream>
#include <algorithm>
using namespace std;

/*
    brute-force approach - using sorting
    time: O(n*logn)
    space: O(logn)
*/
void sort0121(int arr[], int n)
{
    sort(arr, arr + n);
}

/*
    better approach - counting number of 0's, 1's and 2's
    time: O(2*n)
    space: O(1)
*/
void sort0122(int arr[], int n)
{
    int c0 = 0, c1 = 0, c2 = 0, j = 0;
    for (int i = 0; i < n; i++)
    {
        if (arr[i] == 0)
            c0++;
        else if (arr[i] == 1)
            c1++;
        else
            c2++;
    }
    while(c0--)
    {
        arr[j] = 0;
        j++;
    }
    while (c1--)
    {
        arr[j] = 1;
        j++;
    }
    while (c2--)
    {
        arr[j] = 2;
        j++;
    }
}

/*
    expected approach - dutch national flag algorithm
    time: O(n)
    space: O(1)
*/

void sort0123(int arr[], int n)
{
    int low = 0, mid = 0, high = n - 1;
    while (mid <= high)
    {
        if (arr[mid] == 0)
        {
            swap(arr[low], arr[mid]);
            low++;
            mid++;
        }
        else if (arr[mid] == 1)
        {
            mid++;
        }
        else
        {
            swap(arr[mid], arr[high]);
            high--;
        }
    }
}

int main(void)
{
    int n;
    cin >> n;
    int arr[n];
    for (int i = 0; i < n; i++)
    {
        int val;
        do
        {
            cin >> val;
        }
        while(val != 0 && val != 1 && val != 2);
        arr[i] = val;
    }
    sort0123(arr, n);
    return 0;
}