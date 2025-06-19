#include <iostream>
#include <vector>
using namespace std;

/*
 * time complexity: 
 * O(n!/k!(n - k)!) * n)

 * space complexity:
 * O(n!/k!(n - k)!) * n)
 */
void helper(int val, int n, int k, vector<int>& curr, vector<vector<int>>& res)
{
    if (curr.size() == k)
    {
        res.push_back(curr);
        return;
    }

    for (int i = val; i <= n; i++)
    {
        curr.push_back(i);
        helper(i + 1, n, k, curr, res);
        curr.pop_back();
    }
}

vector<vector<int>> combine(int n, int k) {
    vector<vector<int>> res;
    vector<int> curr;
    helper(1, n, k, curr, res);
    return res;
}

int main(void)
{
    auto res = combine(3, 2);
    for (int i = 0; i < res.size(); i++)
    {
        for (int j = 0; j < res[i].size(); j++)
        {
            cout << res[i][j] << " ";
        }
        cout << "\n";
    }
}
