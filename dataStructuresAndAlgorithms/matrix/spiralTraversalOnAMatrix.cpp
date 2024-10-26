#include <iostream>
using namespace std;

int main(void)
{
    int rows, columns, n;
    cin >> rows >> columns;

    int arr[rows][columns] = {0};
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < columns; j++)
        {
            cin >> n;
            arr[i][j] = n;
        }
    }
    for (int k = 0; k < rows; k++)
    {
        for (int l = 0; l < columns; l++)
        {
            cout << arr[k][l] << " "; 
        }
        cout << "\n";
    }
    return 0;
};
