import java.util.Scanner;

public class repetitions
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();

		int count = 1;

		for (int i = 0; i < s.length(); i++)
		{
			int j = i + 1, tempCount = 1;
			for ( ; j != s.length() && s.charAt(i) == s.charAt(j); j++)
			{
				tempCount++;
			}
			count = Math.max(count, tempCount);
			i = j - 1;
		}

		System.out.println(count);
	}
}