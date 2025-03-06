#include <iostream>
#include "node.cpp"
using namespace std;

void addNode(node ** head, int val)
{
    node * iterator = * head;
    while (iterator->next != nullptr)
        iterator = iterator->next;
    iterator->next = new node(val);
}

void removeNode(node ** head, int position)
{
    node * iterator = * head;
    if (position == 0)
    {
        iterator = iterator->next;
        delete *head;
        *head = iterator;
    }
    else
    {
        for (int i = 0; i < position - 1; i++)
        {
            iterator = (*iterator).next;
        }
        if (iterator->next->next == nullptr)
        {
            delete iterator->next;
        }
        else
        {
            node * temp = iterator->next->next;
            delete iterator->next;
            iterator->next = temp;
        }
    }
}


void modifyNode(node ** head, int position, int newValue)
{
    node * iterator = * head;
    
    for (int i = 0; i < position; i++)
    {
        iterator = iterator->next;
    }
    
    iterator->val = newValue;

}

void deleteList(node ** head)
{
    node * iterator = * head;
    if (iterator->next == nullptr)
    {
        delete iterator;
        return;
    }
    else
    {
        deleteList(&(iterator->next));
        iterator->next = nullptr;
        deleteList(&(iterator));
    }
}

int main(void)
{
    node * head = new node(1);
    addNode(&head, 2);
    addNode(&head, 3);
    addNode(&head, 4);
    deleteList(&head);
}