import java.util.Scanner;

public class bitStrings
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		long ans = 1;
		for (int i = 0; i < n; i++)
		{
			ans = ans * 2;
			ans = ans % (long)(1e9 + 7);
		}
		System.out.println(ans);
		sc.close();
	}
}