#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int countSmallerEqual(vector<int>& row, int x) {
    return upper_bound(row.begin(), row.end(), x) - row.begin();
}

int findMedian(vector<vector<int>>& matrix, int n, int m) {
    int low = matrix[0][0], high = matrix[0][m - 1];
    
    for (int i = 1; i < n; i++) {
        low = min(low, matrix[i][0]);
        high = max(high, matrix[i][m - 1]);
    }

    int desired = (n * m) / 2;

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
    int n = matrix.size(), m = matrix[0].size();
    cout << "Median is " << findMedian(matrix, n, m) << endl;
    return 0;
}
