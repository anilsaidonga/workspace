#include <iostream>
#include <cstring>
#include <algorithm>
using namespace std;

/*
 * brute force (create new array)
 * time complexity: O(n)
 * space complexity: O(n)
 * auxiliary space complexity: O(n)
 */

void reverseString1(char arr[], int size)
{
    char temp[size];
    strcpy(temp, arr);
    
    for (int i = 0; i < size; i++)
        arr[i] = temp[size - i - 1];
}

/*
 * expected approach (two pointer approach)
 * time complexity: O(n)
 * space complexity: O(n)
 * auxiliary space complexity: O(1);
 */

void reverseString2(char arr[], int size)
{
    int l = 0, r = size - 1;
    while (l < r)
    {
        swap(arr[l], arr[r]);
        l++;
        r--;
    }
}

/*
 * expected approach ( iterate through half the array while swapping )
 * time complexity: O(n)
 * space complexity: O(n)
 * auxiliary space complexity: O(1)
 */

void reverseString3(char arr[], int size)
{
    int i = 0;
    while (i < size/2)
    {
        swap(arr[i], arr[size - i - 1]);
        i++;
    }
}

/*
 * recursion based approach
 * time complexity: O(n)
 * space complexity: O(n)
 * auxiliary space complexity: O(n) ( recursion )
 */

void reverseString4(char arr[], int i, int j)
{
    if (i < j)
    {
        swap(arr[i], arr[j]);
        reverseString4(arr, i + 1, j - 1);
    }
}

/*
 * expected approach
 * time complexity: O(n)
 * space complexity: O(n)
 * auxiliary space complexity: O(1)
 */

void reverseString5(char arr[], int size)
{
    reverse(arr, arr + size);
}

int main(void)
{
    char arr[] = {'a', 'p', 'p', 'l', 'e'};
    int size = sizeof(arr)/sizeof(arr[0]);
    reverseString5(arr, size);
    for (int i = 0; i < size; i++)
        cout << arr[i] << endl;
    return 0;
}
