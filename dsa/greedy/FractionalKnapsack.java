package greedy;

import java.util.*;

class FractionalKnapsack
{
	
	/*
	 * optimal approach
	 * time complexity: O(nlogn) + O(n) ( sorting + for loop inside helper function )
	 * space complexity: O(n)        
	 */
	
	static class Item implements Comparable<Item>
	{
		int value, weight;
		double ratio;
		
		Item(int value, int weight)
		{
			this.value = value;
			this.weight = weight;
			ratio = (double) value / (double) weight;
		}
		
		@Override
		public int compareTo(Item other)
		{
			if (other.ratio > this.ratio) return 1;
			else if (other.ratio < this.ratio)  return -1;
			else return 0;
		}
	}

	static class ratioComparator implements Comparator<Item>
	{
		@Override
		public int compare(Item a, Item b)
		{
			if (b.ratio > a.ratio) return 1;
			else if (b.ratio < a.ratio) return -1;
			else return 0;
		}
	}
	
	public static int helper(int n, int weight, Item[] allItems)
	{
		int maxValue = 0, maxWeight = weight, currWeight = 0;
		
		for (int i = 0; i < n; i++)
		{
			if (allItems[i].weight + currWeight <= maxWeight)
			{
				maxValue += allItems[i].value;
				currWeight += allItems[i].weight;
			}
			else
			{
				int remainingWeight = maxWeight - currWeight;
				maxValue += allItems[i].ratio * remainingWeight;
				break;
			}
		}
		return maxValue;
	}
	
	public static void main(String[] args)
	{
		int n = 3, weight = 50;
		int values[] = {100, 60, 120}, weights[] = {20, 10, 30};
		
		Item[] allItems = { new Item(100, 20), new Item(60, 10), new Item(120, 30)};
		
		//Arrays.sort(allItems, (a, b) -> Double.compare(b.ratio, a.ratio));

		Arrays.sort(allItems, new ratioComparator());
		
		//Arrays.sort(allItems);
		
		System.out.println(helper(n, weight, allItems));
	}
}