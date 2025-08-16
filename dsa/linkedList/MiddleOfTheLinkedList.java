package linkedList;

public class MiddleOfTheLinkedList {
	
	/*
	 * brute force approach
	 * time complexity: O(n) + O(n/2) ( while loop for nodes count + while loop to iterate half the linked list )
	 * space complexity: O(1)
	 */
//	public static ListNode middleNode(ListNode head)
//	{
//		ListNode temp = head;
//		int count = 0;
//		while (temp != null) 
//		{
//			count++;
//			temp = temp.next;
//		}
//		temp = head;
//		int mid = count / 2;
//		while (mid > 0)
//		{
//			temp = temp.next;
//			mid--;
//		}
//		return temp;
//	}
	
	/*
	 * optimal approach
	 * time complexity: O(n/2) ~ O(n) ( we only iterate half the linked list with slow and fast pointers )        
	 * space complexity: O(1)        
	 */
	public static ListNode middleNode(ListNode head)
	{
		ListNode slow = head, fast = head;
		while (fast != null && fast.next != null)
		{
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5, 6};
		ListNode head = null;
		for (int i = 0; i < arr.length; i++)
			head = addListNode(arr[i], head);
		head = middleNode(head);
	}

	private static ListNode addListNode(int i, ListNode head) {
		if (head == null) {
			head = new ListNode(i, null);
			return head;
		}
		ListNode temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = new ListNode(i, null);
		return head;
	}
}
