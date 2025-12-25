import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamVsParallelStream
{
	public static void main(String[] args)
	{
		List<Integer> l1 = Arrays.asList(1, 2, 3, 4);
		l1.stream().forEach((i) -> System.out.println(Thread.currentThread().getName() + ": " + i));

		List<Integer> l2 = Arrays.asList(2, 3);
		l2.parallelStream().forEach((i) -> System.out.println(Thread.currentThread().getName() + ": " + i));
	}
