#include <bits/stdc++.h>
using namespace std;

int main(void)
{
    set<int> setRes;
    setRes.insert(1);
    setRes.insert(2);
    auto it = setRes.begin();
    it++;
    cout << * it << endl;
}
