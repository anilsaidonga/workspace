import java.util.PriorityQueue;
import java.util.Queue;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

public class Try
{
	public static void main(String[] args)
	{
		Queue<Integer> myList = new PriorityQueue<>(Collections.reverseOrder());
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList.add(4);

		Iterator<Integer> iterator = myList.iterator();

		while (iterator.hasNext())
		{
			System.out.println(iterator.next());
		}

		iterator = myList.iterator();

		while (iterator.hasNext())
		{
			iterator.next();
			iterator.remove();
		}

		System.out.println(myList.size());

	}
}


https://docs.google.com/forms/d/e/1FAIpQLScXmruS2kA2eif0ATU0yopLziQqpfCjezFHqVjLGA0UYTWvLg/viewform?pli=1