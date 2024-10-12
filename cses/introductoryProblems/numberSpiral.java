import java.util.Scanner;

public class numberSpiral
{

	public static long answer(long x, long y)
	{
		long mx = Math.max(x, y);

		if ((mx % 2) == 0)
		{
			if (y == 1)
				return (mx*mx);
			else if (x < mx)
				return answer(mx, mx) - (mx - x);
			else if (x == mx)
				return (mx * mx) - (y - 1);
		}
		else 
		{
			if (x == 1)
				return (mx * mx);
			else if (y < mx)
				return answer(mx, mx) - (mx - y);
			else if (y == mx)
				return (mx * mx) - (x - 1);
		}
		return 0;
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while (n > 0)
		{
			long x = sc.nextLong();
			long y = sc.nextLong();
			System.out.println(answer(x, y));
			n--;
		}
	}
}