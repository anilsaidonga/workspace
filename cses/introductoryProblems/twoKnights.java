import java.util.Scanner;

public class twoKnights
{

	public static long answer(long k)
	{
		return ((k * k * (k * k - 1)/2) - 2 * (k - 2) * (k - 1) - 2 * (k - 1) * (k - 2));
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		long n = sc.nextLong();
		long k = 1;
		while (k <= n)
		{
			System.out.println(answer(k));
			k++;
		}
	}
}