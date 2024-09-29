import java.util.Scanner;

public class missingNumber
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		long sum = n * (n + 1)/2;
		for (long i = 0; i < (n - 1); i++)
		{
			long num = sc.nextLong();
			sum = sum - num;
		}
		System.out.println(sum);
		sc.close();
	}
}
