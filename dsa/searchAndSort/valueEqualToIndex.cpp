#include <iostream>
#include <vector>
using namespace std;

vector<int> valueToIndex(vector<int> &arr)
{
    vector<int> result;
    for (int i = 0; i < arr.size(); i++)
    {
        if (arr[i] == i + 1)
            result.push_back(i + 1);
    }
    return result;
}

int main(void)
{
    vector<int> arr = {1, 2, 4, 4};
    vector<int> result = valueToIndex(arr);
    for (int i : result)
        cout << i << endl;
    return 0;
}
