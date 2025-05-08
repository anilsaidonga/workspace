// demandbase

#include <iostream>
#include <queue>
using namespace std;

int numOfIslands(vector<vector<int>>& grid)
{
    int count = 0, r = grid.size(), c = grid[0].size();
    vector<pair<int, int>> directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    queue<pair<int, int>> q;

    for (int i = 0; i < r; i++)
    {
        for (int j = 0; j < c; j++)
        {
            if (grid[i][j] == 1)
            {
                count++;
                grid[i][j] = 0;

                q.push({i, j});
                
                while (!q.empty())
                {
                    pair<int, int> curr = q.front();
                    q.pop();

                    int cx = curr.first, cy = curr.second;

                    for (auto eachDirection : directions)
                    {
                        int nx = cx + eachDirection.first, ny = cy + eachDirection.second;
                        if (nx >= 0 && nx < c && ny >= 0 && ny < r && grid[nx][ny] == 1)
                        {
                            q.push({nx, ny});
                            grid[nx][ny] = 0;
                        }
                    }
                }
            }
        }
    }

    return count;
}

int main(void)
{
    vector<vector<int>> grid = {{1, 1, 0, 0},
                                {0, 1, 0, 1},
                                {0, 0, 0, 1},
                                {1, 0, 1, 0}};
    cout << numOfIslands(grid) << endl;
    return 0;
}
