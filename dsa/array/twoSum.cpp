#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
using namespace std;

/*
 * brute force approach        
 * time complextiy: O( n * n )        
 * space complexity: O(1)        
 */

// vector<int> twoSum(vector<int> nums, int target)
// {
//     int n = nums.size();
//     for (int i = 0; i < n; i++)
//     {
//         for (int j = i + 1; j < n; j++)
//         {
//             if (nums[i] + nums[j] == target) return {i, j};
//         }
//     }
//     return {-1, -1};
// }        

/* 
 * better approach using map        
 * time complexity: O ( n * log n ) (for loop + in worst case else block code only works )        
 * space complexity: O(n)        
 */

// vector<int> twoSum(vector<int> nums, int target)
// {
//     int n = nums.size();        
//     // value, index
//     map<int, int> freq;
//     for (int i = 0; i < n; i++)
//     {
//         int moreNeeded = target - nums[i];
//         if (freq.find(moreNeeded) != freq.end())
//         {
//             return {freq[moreNeeded], i};        
//         }
//         else
//         {
//             freq[nums[i]] = i;
//         }
//     }
//     return {-1, -1};        
// }

/* better approach using unordered map        
 * time complexity: O(n) * O(1) (for loop + unordered map has O(1) time complexity in best and average cases
 * space complexity: O(n)        
 * but in worst case, time complexity of unordered_map will be O(n), then time complexity will become O(n) * O(n)        
 */

// vector<int> twoSum(vector<int> nums, int target)
// {
//     int n = nums.size();        
//     // value, index
//     map<int, int> freq;
//     for (int i = 0; i < n; i++)
//     {
//         int moreNeeded = target - nums[i];
//         if (freq.find(moreNeeded) != freq.end())
//         {
//             return {freq[moreNeeded], i};        
//         }
//         else
//         {
//             freq[nums[i]] = i;
//         }
//     }
//     return {-1, -1};        
// }
// 

vector<int> twoSum(vector<int> nums, int target)
{
    int n = nums.size(), left = 0, right = n - 1;        
    //sort(nums.begin(), nums.end());        
    while (left < right)
    {
        if (nums[left] + nums[right] == target) return {left, right};
        else if (nums[left] + nums[right] > target) right--;
        else left++;
    }
    return {-1, -1};        
}



int main(void)
{
    vector<int> nums = {1, 2, 3, 4, 5, 6};
    vector<int> res = twoSum(nums, 9);
    cout << res[0] << " " << res[1] << endl;        
    return 0;
}
