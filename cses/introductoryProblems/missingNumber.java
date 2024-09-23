import java.util.Scanner;

public class missingNumber
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int sum = n * (n + 1)/2;
		for (int i = 0; i < (n - 1); i++)
		{
			int num = sc.nextInt();
			sum = sum - num;
		}
		System.out.println(sum);
		sc.close();
	}
}
