package linkedList;

public class MergeTwoSortedLinkedList {

	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5, 6};
		ListNode head = null;
		for (int i = 0; i < arr.length; i++)
			head = addListNode(arr[i], head);
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
