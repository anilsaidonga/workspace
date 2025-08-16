#include <iostream>
#include <vector>
using namespace std;

bool isValid(int i, int j, int n, vector<string>& board, vector<int>& diagnalUp, vector<int>& diagnalLeft, vector<int>& diagnalDown)
{
    // check for diagnal up
    if (diagnalUp[(n - 1) + (j - i)] == 1) return false;

    // check for diagnal left
    if (diagnalLeft[j]) return false;

    // check for diagnal down
    if (diagnalDown[i + j] == 1) return false;

    return true;
}
void solve(int col, int n, vector<string>& board, vector<vector<string>>& res, vector<int>& diagnalUp, vector<int>& diagnalLeft, vector<int>& diagnalDown)
{
    if (col == n)
    {
        res.push_back(board);
        return;
    }
    for (int i = 0; i < n; i++)
    {
        if (isValid(i, col, n, board, diagnalUp, diagnalLeft, diagnalDown))
        {
            diagnalUp[(n - 1) + (col - i)] = 1;
            diagnalLeft[col] = 1;
            diagnalDown[i + col] = 1;
            board[i][col] = 'Q';
            solve(col + 1, n, board, res, diagnalUp, diagnalLeft, diagnalDown);
            board[i][col] = '.';
            diagnalUp[(n - 1) + (col - i)] = 0;
            diagnalLeft[col] = 0;
            diagnalDown[i + col] = 0;
        }
    }
}
vector<vector<string>> solveNQueens(int n) {
    vector<vector<string>> res;
    vector<string> board;
    string row(n, '.');
    vector<int> diagnalUp((2 * n) - 1, 0);
    vector<int> diagnalLeft(n, 0);
    vector<int> diagnalDown((2 * n) - 1, 0);
    for (int i = 0; i < n; i++)
    {
        board.push_back(row);
    }
    solve(0, n, board, res, diagnalUp, diagnalLeft, diagnalDown);
    return res;
}

int main(void)
{
    vector<vector<string>> ans = solveNQueens(4);
    return 0;
}