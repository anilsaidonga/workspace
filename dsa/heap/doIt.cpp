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
			arr = new int[arrSize];
			this->arrSize = arrSize;
			maxHeapSize = 0;
		}

		int parent(int i)
		{
			return (i - 1)/2;
		}

		int leftChild(int i)
		{
			return (2 * i + 1);
		}

		int rightChild(int i)
		{
			return (2 * i + 2);
		}

		void addElement(int val)
		{
			if (maxHeapSize == arrSize)
			{
				cout << "maxHeap is fully occupied!" << endl;
				return;
			}
			else if(maxHeapSize == 0)
			{
				maxHeapSize++;
				arr[0] = val;
				return;
			}
			else
			{
				maxHeapSize++;
				int index = maxHeapSize - 1;
				arr[index] = val;

				while (index != 0 && arr[parent(index)] < arr[index])
				{
					swap(arr[parent(index)], arr[index]);
					index = parent(index);
				}
			}
		}

		void updateElement(int i, int val)
		{
			if (i < maxHeapSize)
			{
				arr[i] = val;

				while (i != 0 && arr[parent(i)] < arr[i])
				{
					swap(arr[parent(i)], arr[i]);
					i = parent(i);
				}
			}
		}

		int removeMaxElement()
		{
			int maxValue;
			if (maxHeapSize != 0)
			{
				maxValue = arr[0];
				arr[0] = arr[maxHeapSize - 1];
				maxHeapSize--;
				heapify(0);
			}
			return maxValue;
		}

		void heapify(int i)
		{
			int l = leftChild(i);
			int r = rightChild(i);
			int largest = i;

			if (l < maxHeapSize && arr[l] > arr[largest])
				largest = l;
			if (r < maxHeapSize && arr[r] > arr[largest])
				largest = r;

			if (largest != i)
			{
				swap(arr[i], arr[largest]);
				heapify(largest);
			}
		}

		int currentMaxHeapSize()
		{
			return maxHeapSize;
		}

		void deleteElement(int i)
		{
			if (i < maxHeapSize)
			{
				arr[i] = INT_MAX;

				while (i != 0 && arr[parent(i)] < arr[i])
				{
					swap(arr[parent(i)], arr[i]);
					i = parent(i);
				}
				removeMaxElement();
			}
		}
};

int main(void)
{
	maxHeap mH(7);
	mH.addElement(1);
	mH.addElement(2);
	mH.addElement(3);
	mH.addElement(4);
	mH.addElement(5);
	mH.addElement(6);
	mH.addElement(7);
	
	mH.updateElement(5, 10);

	mH.deleteElement(4);

	mH.removeMaxElement();

	for (int i = 0; i < mH.maxHeapSize; i++)
	{
		cout << mH.arr[i] << endl;
	}

	return 0;
}
