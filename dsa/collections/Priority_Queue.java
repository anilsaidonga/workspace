package collections;

import java.util.PriorityQueue;
import java.util.Comparator;

public class Priority_Queue {
	
	public static class IntegerComparator implements Comparator<Integer>
	{
		public int compare(Integer a, Integer b)
		{
			if (b > a) return 1;
			else if (b < a) return -1;
			else return 0;
		}
	}

	public static void main(String[] args) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new IntegerComparator());
		
		// obj.add();
		// obj.remove();
	}

}
