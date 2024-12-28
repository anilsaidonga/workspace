class node
{
	public:
		int val;
		node * left;
		node * right;

		node(int val)
		{
			this->val = val;
			this->left = this->right = nullptr;
		}
};
