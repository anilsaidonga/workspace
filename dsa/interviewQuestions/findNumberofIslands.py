# demandbase

from collections import deque

def num_islands_bfs(grid):
    if not grid:
        return 0

    rows, cols = len(grid), len(grid[0])
    count = 0

    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]  # up, down, left, right

    for r in range(rows):
        for c in range(cols):
            if grid[r][c] == 1:
                count += 1
                grid[r][c] = 0  # mark visited
                queue = deque([(r, c)])  # start a queue

                while queue:
                    x, y = queue.popleft()
                    for dx, dy in directions:
                        nx, ny = x + dx, y + dy
                        if 0 <= nx < rows and 0 <= ny < cols and grid[nx][ny] == 1:
                            queue.append((nx, ny))
                            grid[nx][ny] = 0  # mark as visited

    return count

grid = [
    [1, 1, 0, 0],
    [0, 1, 0, 1],
    [0, 0, 0, 1],
    [1, 0, 0, 0]
]

print(num_islands_bfs(grid))  # Output: 3

print('Hello!')