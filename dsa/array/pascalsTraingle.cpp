#include <iostream>
#include <vector>
using namespace std;

vector<vector<int>> generate(int numRows);

/*
 * type 1: will be asked to find the value at row, col    
 * iteration    
 * time complexity: O(r) (we are running loop r times)    
 * space complexity: O(1) (we are using constant space)    
 */

int ncr(int n, int r)    // short cut for finding ncr    
{
    int res = 1;
    for (int i = 0; i < r; i++)
    {
        res = res * (n - i);    
        res = res / (i + 1);    
    }

    return res;
}

int findValue(int row, int col)    
{
    return ncr(row, col);    
}

/*    
 * type 2: will be asked to give the entire row, given the row number    
 * iteration    
 * time complexity: O(n * r)    
 * space complexity: O(1)    
 */

vector<int> pascalsRow(int n)
{
    vector<int> res;
    for (int i = 1; i <= n; i++)    
    {
        res.push_back(ncr(n - 1, i - 1));    
    }   
    return res;    
}


int main(void)
{
    int res1 = findValue(2, 2);
    vector<int> res2 = pascalsRow(4);
    for (int i : res2)    cout << i << " ";    
    return 0;
}

/*
    1
  1   1
1   2   1
1 3   3 1

*/

