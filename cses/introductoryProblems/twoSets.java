import java.util.Scanner;
import java.util.ArrayList;

public class twoSets
{
	public static void answer(long n, long sum)
	{
		long a, b;
		ArrayList<Long> l1 = new ArrayList<Long>(), l2 = new ArrayList<Long>();
		if (n % 2 != 0)
		{
			a = 1;
			b = n - 1;
		}
		else
		{
			a = 1;
			b = n;
		}
		while (a < b)
		{
			l1.add(a);
			l1.add(b);
			a++;
			b--;
			if (a < b)
			{
				l2.add(a);
				l2.add(b);
				a++;
				b--;
			}
		}

		if (n % 2 != 0)
		{
			if (l1.size() < l2.size())
				l1.add(n);
			else
				l2.add(n);
		}

		System.out.println(l1.size());
		int s1 = l1.size();
		for (int i = 0; i < s1; i++)
		{
			System.out.print(l1.get(i) + " ");
		}
		System.out.println();
		System.out.println(l2.size());
		int s2 = l2.size();
		for (int j = 0; j < s2; j++)
		{
			System.out.print(l2.get(j) + " ");
		}
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		long sum = n * (n + 1)/2;

		if (sum % 2 != 0)
		{
			System.out.println("NO");
		}
		else
		{
			System.out.println("YES");
			answer(n, sum);
		}
	}
}