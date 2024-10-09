import java.util.Scanner;
import java.lang.StringBuilder;

public class permutations
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		if (n == 1)
		{
			System.out.println(n);
		}
		else if (n <= 3) 
		{
			System.out.println("NO SOLUTION");
		}
		else 
		{
			StringBuilder sb = new StringBuilder();
			int a = n, b = n - 1;

			while (b > 0)
			{
				sb.append(b).append(" ");
				b = b - 2;
			}

			while (a > 0)
			{
				sb.append(a).append(" ");
				a = a - 2;
			}

			System.out.println(sb);
		}
		sc.close();
	}
}