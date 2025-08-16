#include <iostream>
#include <vector>
using namespace std;

void helper(int ind, int n, int k, vector<int>& curr, vector<vector<int>>& res)
{
    // base condition
    if (curr.size() == k)
    {
        res.push_back(curr);
        return;
    }

    if (ind > n) return;

    // include wala case
    curr.push_back(ind);
    helper(ind + 1, n, k, curr, res);
    curr.pop_back();

    // exclude wala case
    helper(ind + 1, n, k, curr, res);
}
vector<vector<int>> combine(int n, int k) {
    vector<int> curr;
    vector<vector<int>> res;
    helper(1, n, k, curr, res);
    return res;
}

int main(void) {
    vector<vector<int>> res = combine(3, 2);
    return 0;
}
