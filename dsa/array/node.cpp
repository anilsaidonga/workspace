class node
{
    public:
    
    int val;
    node * next;

    node(int val, node * next = nullptr)
    {
        this->val = val;
        this->next = nullptr;
    }
};