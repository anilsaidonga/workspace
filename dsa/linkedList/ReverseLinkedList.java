package linkedList;

import java.util.Stack;

public class ReverseLinkedList {
	
	/*
	 * brute force approach        
	 * time complexity: O(n) + O(n) ( put everthing in stack + pop everthing out of the stack )        
	 * space complexity: O(n) ( stack s space )        
	 */
//    public static ListNode reverseList(ListNode head) {
//    	if (head == null) return head;        
//    	Stack<Integer> s = new Stack<Integer>();
//    	ListNode temp = head;
//    	
//    	while (temp.next != null)
//    	{
//    		s.push(temp.val);
//    		temp = temp.next;
//    	}
//    	s.push(temp.val);
//    	
//    	temp = head;
//    	
//    	while (!s.empty())
//    	{
//    		temp.val = s.pop();
//    		temp = temp.next;
//    	}
//        return head;
//    }
    
//    public static ListNode reverseList(ListNode head)
//    {
//    	if (head == null) return head;
//    	ListNode prev = null, curr = head, next = null;
//    	while (curr != null)
//    	{
//    		next = curr.next;
//    		curr.next = prev;
//    		prev = curr;
//    		curr = next;
//    	}
//    	return prev;
//    }
	
    public static ListNode reverseList(ListNode head)
    {
    	if (head == null || head.next == null)
    	{
    		return head;
    	}
    	ListNode newHead = reverseList(head.next);
    	ListNode next = head.next;
    	next.next = head;
    	head.next = null;
    	return newHead;
    }

	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5};
		ListNode head = null;
		for (int i = 0; i < arr.length; i++)
			head = addListNode(arr[i], head);
		head = reverseList(head);
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
