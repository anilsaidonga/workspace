#include <iostream>
#include <climits>
using namespace std;

class maxHeap
{
	public:
		int * arr;
		int arrSize;
		int maxHeapSize;
		maxHeap(int arrSize)
		{
			this->arrSize = arrSize;
			arr = new int[arrSize];
			maxHeapSize = 0;
		}

		int parent(int i)
		{
			return ((i - 1)/2);
		}

		int leftChild(int i)
		{
			return (2 * i + 1);
		}

		int rightChild(int i)
		{
			return (2 * i + 2);
		}

		void add(int val)
		{
			if (maxHeapSize == arrSize)
			{
				cout << "Heap is full!" << endl;
				return;
			}

			maxHeapSize++;
			arr[maxHeapSize - 1] = val;

			int i = maxHeapSize - 1;

			while (i != 0 && arr[parent(i)] < arr[i])
			{
				swap(arr[parent(i)], arr[i]);
				i = parent(i);
			}
		}

		void update(int i, int val)
		{
			if (i <= maxHeapSize - 1)
			{
				arr[i] = val;

				while (i != 0 && arr[parent(i)] < arr[i])
				{
					swap(arr[parent(i)], arr[i]);
					i = parent(i);
				}
			}
		}

		void heapify(int i)
		{
			int leftChild = parent(i);
			int rightChild = parent(i);
			int largest = i;

			if (leftChild < maxHeapSize && arr[leftChild] > arr[largest])
			{
				largest = leftChild;
			}

			if (rightChild < maxHeapSize && arr[rightChild] > arr[largest])
			{
				largest = rightChild;
			}

			if (largest != i)
			{
				swap(arr[i], arr[largest]);
				heapify(largest);
			}
		}

		int removeMax()
		{
			if (maxHeapSize <= 0)
				return INT_MIN;
			if (maxHeapSize == 1)
			{
				maxHeapSize--;
				return arr[0];
			}

			int maxValue = arr[0];
			arr[0] = arr[maxHeapSize - 1]; // bring last element and make it as max heap
			maxHeapSize--;

			heapify(0);

			return maxValue;
		}

		void remove(int i)
		{
			update(i, INT_MAX);
			removeMax();
		}

		int currentHeapSize()
		{
			return maxHeapSize;
		}
};

int main(void)
{
	maxHeap mH(3);
	mH.add(1);
	mH.add(2);
	mH.add(3);
	mH.update(2, 4);

	for (int i = 0; i < mH.arrSize; i++)
		cout << mH.arr[i] << endl;
	return 0;
}
