#include <iostream>
#include <vector>
using namespace std;

bool isValid(int r, int c, int n, vector<string>& board)
{
    // check for left elements within the row
    for (int col = 0; col < c; col++)
    {
        if (board[r][col] == 'Q') return false;
    }

    // check for left top diagnal elements
    for (int row = r, col = c; row >= 0 && col >= 0; row--, col--)
    {
        if (board[row][col] == 'Q')
            return false;
    }

    // check for left down diagnal elements
    for (int row = r, col = c; row < n && col >= 0; row++, col--)
    {
        if (board[row][col] == 'Q')
            return false;
    }
    return true;
}
void helper(int c, int n, vector<string>& board, vector<vector<string>>& res)
{
    if (c == n)
    {
        res.push_back(board);
        return;
    }

    for (int r = 0; r < n; r++)
    {
        if (isValid(r, c, n, board))
        {
            board[r][c] = 'Q';
            helper(c + 1, n, board, res);
            board[r][c] = '.';
        }
    }

}
vector<vector<string>> solveNQueens(int n) {
    vector<vector<string>> res;
    vector<string> board(n, "....");
    helper( 0, n, board, res);
    return res;
}

int main(void)
{
    vector<vector<string>> ans = solveNQueens(5);
    return 0;
}