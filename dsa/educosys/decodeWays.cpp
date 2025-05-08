#include <iostream>
using namespace std;

int helper(string s, int ind)
{
    if (ind == s.size())
    {
        return 1;
    }

    if (ind > s.size())
        return 0;

    if (s[ind] == '0') return 0;
    // consider single digit
    int ways = helper(s, ind + 1);

    // consider double digit
    if (s[ind] == '1' || (s[ind] == '2' && s[ind + 1] >= '0' && s[ind + 1] <= '6'))
        ways += helper(s, ind + 2);

    return ways;
}

int numDecodings(string s)
{
    return helper(s, 0);
}

int main(void)
{
    string s = "26";
    cout << numDecodings(s) << endl;
    return 0;
}
