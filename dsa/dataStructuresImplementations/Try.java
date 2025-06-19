package dataStructuresImplementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Deque;

public class Try {
	
	public static class car
	{
		String carName;
		String carType;
		
		car(String name, String type)
		{
			this.carName = name;
			this.carType = type;
		}
	}

	public static void main(String[] args) {
		
		car[] arr = new car[3];
		
		arr[0] = new car("dzire", "mini-sedan");
		arr[1] = new car("a", "b");
		arr[2] = new car("c", "d");
		
		Arrays.sort(arr, (car obj1, car obj2) -> obj1.carName.compareTo(obj2.carName));
		
		for (car obj : arr)
			System.out.println(obj.carName + " " + obj.carType);
		
	}

}
