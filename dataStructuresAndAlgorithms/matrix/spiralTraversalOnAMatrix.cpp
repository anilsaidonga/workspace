#include <iostream>
#include <vector>
using namespace std;

void spiralTraversal(vector<vector<int>> &arr, int rows, int columns)
{

	int direction = 0, rowBegin = 0, rowEnd = rows - 1, columnBegin = 0, columnEnd = columns - 1;

	while (rowEnd >= rowBegin && columnEnd >= columnBegin)
	{

		if (direction == 0)
		{
			for (int i = columnBegin; i <= columnEnd; i++)
			{
				cout << arr[rowBegin][i] << " ";
			}
			direction = 1;
			rowBegin++;
		}

		if (direction == 1)
		{
			for (int i = rowBegin; i <= rowEnd; i++)
			{
				cout << arr[i][columnEnd] << " ";
			}
			direction = 2;
			columnEnd--;
		}

		if (direction == 2)
		{
			for (int i = columnEnd; i >= columnBegin; i--)
			{
				cout << arr[rowEnd][i] << " ";
			}
			direction = 3;
			rowEnd--;
		}

		if (direction == 3)
		{
			for (int i = rowEnd; i >= rowBegin; i--)
			{
				cout << arr[i][rowBegin] << " ";
			}
			direction = 0;
			columnBegin++;
		}
	}
}

int main(void)
{
	int rows, columns, n;
	cin >> rows >> columns;

	vector<vector<int>> arr;

	for (int i = 0; i < rows; i++)
	{
		vector<int> temp;
		for (int j = 0; j < columns; j++)
		{
			cin >> n;
			temp.push_back(n);
		}
		arr.push_back(temp);
		temp.clear();
	}

	spiralTraversal(arr, rows, columns);

	return 0;
};
