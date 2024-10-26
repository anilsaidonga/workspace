#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void naiveApproach(int * arr, int size) // time: O(n), space: O(n);
{
    int temperaryArray[size] = {0};
    
    for (int i = size - 1, j = 0; i >= 0; i--, j++)
        temperaryArray[i] = arr[j];

    for (int k = 0; k < size; k++)
        arr[k] = temperaryArray[k];
}

void expectedApproach1(int * arr, int size) // Time: O(n), Space: O(1);
{
    // two pointer approach

    int i = 0, j = size - 1;
    while (j > i)
    {
        swap(arr[i], arr[j]);
        j--;
        i++;
    }
}

void expectedApproach2(int * arr, int size)
{
    for (int i = 0; i < size/2; i++)
    {
        swap(arr[i], arr[size - 1 - i]);
    }
}

void reverseArrayUsingRecursion(int * arr, int l, int r) // Time: O(n), Space O(1);
{
    if (l > r)
        return;
    swap(arr[l], arr[r]);
    reverseArrayUsingRecursion(arr, l + 1, r - 1);
}

void inbuiltVectorReverse(vector<int> &v) // Time: O(n), Space O(1);
{
    reverse(v.begin(), v.end());
}

int main(void)
{
    int arr[] = {1, 2, 3, 4, 5};
    vector<int> v = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int size = sizeof(arr)/sizeof(int);
    //naiveApproach(arr, size);
    //expectedApproach2(arr, size);
    //reverseArrayUsingRecursion(arr, 0, size - 1);
    inbuiltVectorReverse(v);
    for (int i = 0; i < v.size(); i++)
        cout << v[i] << endl;
    return 0;
}
