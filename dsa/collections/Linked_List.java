package collections;

import java.util.List;
import java.util.LinkedList;

public class Linked_List {

	public static void main(String[] args) {
		List<Integer> linkedList = new LinkedList<>();
		linkedList.add(1);
		System.out.println(linkedList.get(0));
		linkedList.set(0, 22);
		linkedList.remove(0);
		
	}

}
