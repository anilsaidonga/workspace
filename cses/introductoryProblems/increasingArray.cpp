#include <iostream>
using namespace std;

int main(void)
{
    long n, moves = 0, a = 0;
    cin >> n;
    while (n--)
    {
        long b;
        cin >> b;
        while (b < a)
        {
            moves++;
            b++;
        }
        a = b;
    }
    cout << moves << endl;
    return 0;
}
