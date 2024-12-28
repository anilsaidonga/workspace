#include <iostream>
#include "node.cpp"
#include <queue>
using namespace std;

void levelOrderTraversal(node * root) // Time: O(n), Space: O(n);
{
	queue<node *> q;
	q.push(root);
	while(!q.empty())
	{
		node * t = q.front();
		cout << t->val << endl;
		q.pop();
		if (t->left != nullptr)
			q.push(t->left);
		if (t->right != nullptr)
			q.push(t->right);
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

	levelOrderTraversal(root);

	return 0;
}
