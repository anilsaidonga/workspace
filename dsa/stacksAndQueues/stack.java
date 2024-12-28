public class stack
{
	int[] arr;
	int top;

	public stack(int n)
	{
		this.arr = new int[n];
		this.top = -1;
	}

	public void push(int val)
	{
		arr[++top] = val;
	}

	public void pop()
	{
		top--;
	}

	public int top()
	{
		return arr[top];
	}

	public boolean isEmpty()
	{
		return (top == -1);
	}
}

