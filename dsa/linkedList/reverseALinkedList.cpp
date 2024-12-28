#include <iostream>
using namespace std;

class node
{
	public:
		int val;
		node * next;

		node(int val)
		{
			this->val = val;
			this->next = nullptr;
		}
};

void addNodePassByReference(node ** head, int val)
{
	if (* head == nullptr)
	{
		* head = new node(val);
	}
	else
	{
		node * tempHead = * head;
		while(tempHead->next != nullptr)
			tempHead = tempHead->next;
		tempHead->next = new node(val);
	}
}

void reverseTheLinkedList(node ** head)
{
	node * tempHead = * head;
	node * prev = nullptr, * curr = tempHead, * nxt = tempHead->next;

	while(curr != nullptr)
	{
		curr->next = prev;
		prev = curr;
		curr = nxt;
		if (nxt != nullptr)
		{
			nxt = nxt->next;
		}
	}
	* head = prev;
}

int main(void)
{
	node * head = nullptr;
	int n;
	cin >> n;
	while (n--)
	{
		int val;
		cin >> val;
		addNodePassByReference(&head, val);
	}

	reverseTheLinkedList(&head);

	node * tempHead = head;

	while (tempHead != nullptr)
	{
		cout << tempHead->val << endl;
		tempHead = tempHead->next;
	}
	return 0;
}
