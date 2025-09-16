<<<<<<< Updated upstream
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Try
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("input"));
			String line;
			while ((line = br.readLine()) != null)
			{
				System.out.println(line);
			}
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
=======
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Try
{
    public static void main(String[] args)
    {
        List<Integer> l = Arrays.asList(4, 4, 3, 2, 1);
        Stream<Integer> s1 = l.stream();
        
        Stream<String> s2 = Stream.of("anil", "sai", "donga");

        List<Integer> res = s1.limit(3).sorted().distinct().collect(Collectors.toList());
        System.out.println(res);
    }
>>>>>>> Stashed changes
}