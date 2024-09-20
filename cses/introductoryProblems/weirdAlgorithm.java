
import java.util.Scanner;

public class weirdAlgorithm
{
    public static void main(String args[])
    {
	Scanner sc = new Scanner(System.in);
	int n = sc.nextInt();

	while (n != 1) 
	{
	    System.out.println(n);
	    if ((n % 2) == 0) 
	    {
		n = n / 2;
	    }
	    else
	    {
		n = (n * 3) + 1;
	    }
	}

	System.out.println(n);

    }
}
