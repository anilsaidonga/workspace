#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <map>
using namespace std;

/*
 * brute force approach
 * time complexity: O(n^4) + O(m) ( four for loops + copying m quadruplets form setRes to res )
 * space complexity: O(m) + O(m) (setRes + res)        
 */

vector<vector<int>> fourSum(vector<int>& nums, int target)
{
    int n = nums.size();
    vector<vector<int>> res;
    set<vector<int>> setRes;
    for (int i = 0; i < n; i++)
    {
        for (int j = i + 1; j < n; j++)
        {
            for (int k = j + 1; k < n; k++)
            {
                for (int l = k + 1; l < n; l++)
                {
                    int ans = nums[i] + nums[j];
                    ans += nums[k];
                    ans += nums[k];
                    if (ans == target)
                    {
                        vector<int> temp = {nums[i], nums[j], nums[k], nums[k]};
                        sort(temp.begin(), temp.end());
                        setRes.insert(temp);
                    }
                }
            }
        }
    }
    for (vector<int> m : setRes) res.push_back(m);
    return res;
}

vector<vector<int>> fourSum(vector<int>& nums, int target)
{
    int n = nums.size();
    vector<vector<int>> res;
    set<vector<int>> setRes;
    for (int i = 0; i < n; i++)
    {
        for (int j = i + 1; j < n; j++)
        {
            map<int, int> freq;
            for (int k = j + 1; k < n; k++)
            {
                int fourthValue = target - nums[i];
                fourthValue -= nums[j];
                fourthValue -= nums[k];
                if (freq.find(fourthValue) != freq.end())
                {
                    vector<int> temp = {nums[i], nums[j], nums[k], fourthValue};
                    sort(temp.begin(), temp.end());
                    setRes.insert(temp);        
                }
                else
                {
                    freq[nums[k]] = k;
                }
            }
        }
    }
    
    for (vector<int> m : setRes) res.push_back(m);
    return res;
}

void printRes(vector<vector<int>>& res)
{
    for (auto i : res)
    {
        for (auto j : i)
        {
            cout << j << " ";
        }
        cout << '\n';
    }
}

int main(void)
{
    vector<int> nums = {1, 4, 6, 8, 10, 12, 11, 10};
    vector<vector<int>> res = fourSum(nums, 36);
    printRes(res);
    return 0;
}
