#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

/*
 * brute force
 * time complexity: O ((m * n) * log(m * n))
 * space complexity: O (m * n)
 * auxiliary space complexity: O(m * n)
*/

int medianInARowWiseSortedMatrix1(vector<vector<int>>& matrix)
{
    int m = matrix.size(), n = matrix[0].size();
    vector<int> t;

    for (int i = 0; i < m; i++)
    {
        for (int j = 0; j < n; j++)
        {
            t.push_back(matrix[i][j]);
        }
    }
    sort(t.begin(), t.end());
    return t[t.size() / 2];
}

int countSmallerEqual(vector<int>& row, int x) {
    return upper_bound(row.begin(), row.end(), x) - row.begin();
}

/*
 * expected approach
 * time complexity: O(log(m - n) * (m * log n))
 * space complexity: O(m * n)
 * auxiliary space complexity: O(1)
*/

int medianInARowWiseSortedMatrix2(vector<vector<int>>& matrix) {
    int m = matrix.size(), n = matrix[0].size();
    int low = matrix[0][0], high = matrix[0][m - 1];
    
    for (int i = 1; i < m; i++) {
        low = min(low, matrix[i][0]);
        high = max(high, matrix[i][n - 1]);
    }

    int desired = (m * n) / 2;

    while (low < high) {
        int mid = low + (high - low) / 2;
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            count += countSmallerEqual(matrix[i], mid);
        }
        
        if (count <= desired)
            low = mid + 1;
        else
            high = mid;
    }

    return low;
}

int main() {
    vector<vector<int>> matrix = {
        {1, 3, 5},
        {2, 6, 9},
        {3, 6, 9}
    };
    cout << "Median is " << medianInARowWiseSortedMatrix1(matrix) << endl;
    return 0;
}
