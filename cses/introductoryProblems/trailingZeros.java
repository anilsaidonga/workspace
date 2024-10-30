import java.util.Scanner;

public class trailingZeros
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();

		int count = 0;

		for (int i = 5; n >= i; i = i * 5)
		{
			count = count + (int)(Math.floor(n/i));
		}

		System.out.println(count);
		sc.close();
	}
}