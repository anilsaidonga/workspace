import java.util.Scanner;

public class increasingArray
{

    public static long diff(long num1, long num2)
    {
        return Math.abs((long) num1 - num2);
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong(), moves = 0, a = 0;
        while (n > 0)
        {
            long b = sc.nextLong();
            if (b < a)
            {
                long diff = diff(a, b);
                moves = moves + diff;
                b = b + diff;
            }
            a = b;
            n--;
        }
        System.out.println(moves);
        sc.close();
    }
}
