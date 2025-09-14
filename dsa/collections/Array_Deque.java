package collections;

import java.util.ArrayDeque;

public class Array_Deque {

	public static void main(String[] args) {
		
		ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
		arrayDeque.addFirst(1);
		arrayDeque.addLast(2);
		System.out.println(arrayDeque.getFirst());
		System.out.println(arrayDeque.getLast());
		arrayDeque.removeFirst();
		arrayDeque.removeLast();
	}

}
