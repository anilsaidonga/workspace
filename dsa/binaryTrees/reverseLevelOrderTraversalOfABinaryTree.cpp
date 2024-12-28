#include <iostream>
#include "node.cpp"
#include <queue>
#include <stack>
using namespace std;

void reverseLevelOrderTraversal(node * root) // Time: O(n), Space: O(n);
{
	queue<node *> q;
	stack<node *> s;
	q.push(root);
	while(!q.empty())
	{
		node * t = q.front();
		s.push(t);
		q.pop();
		if (t->left != nullptr)
			q.push(t->left);
		if (t->right != nullptr)
			q.push(t->right);
	}

	while(!s.empty())
	{
		cout << s.top()->val << endl;
		s.pop();
	}
}

int main(void)
{
	node * root = new node(1);
	root->left = new node(2);
	root->right = new node(3);
	root->left->left = new node(4);
	root->left->right = new node(5);
	root->right->left = new node(6);
	root->right->right = new node(7);

	reverseLevelOrderTraversal(root);

	return 0;
}
