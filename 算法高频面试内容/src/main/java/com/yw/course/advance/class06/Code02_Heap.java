package com.yw.course.advance.class06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static com.yw.util.CommonUtils.swap;

/**
 * @author yangwei
 */
public class Code02_Heap {

	public static class Heap<T extends Comparable<? super T>> {
		private List<T> items;
		private int count;
		private Comparator<? super T> comparator;

		public Heap() {
			this(Comparator.naturalOrder());
		}
		public Heap(Comparator<? super T> comparator) {
			this.comparator = comparator;
			this.items = new ArrayList<>();
			this.count = 0;
		}
		public void add(T item) {
			items.add(item);
			// 将item向上调整
			shiftUp(count++, item);
		}
		public void shiftUp(int k, T x) {
			int i = k;
			while (k > 0) {
				// 获取父节点索引位置 parent = (child - 1) / 2
				int parent = (k - 1) >>> 1;
				T e = items.get(parent);
				// 比较当前节点值和父节点值，如果 x.val >= e.val 不需要调整直接 break
				if ((comparator == null
						? e.compareTo(x)
						: comparator.compare(x, e)) >= 0) {
					break;
				}
				// 交换
				items.set(k, e);
				k = parent;
			}
			// i != k 说明发生了交换
			if (i != k) items.set(k, x);
		}
		public T poll() {
			if (size() == 0) return null;
			// 取出堆顶元素，并将末尾元素放到堆顶
			T e = items.get(0);
			T item = items.remove(--count);
			if (count > 0) {
				// 先将最末尾元素放到头位置(0索引位置)
				items.set(0, item);
				// 将item向下调整
				shiftDown(0, item);
			}
			return e;
		}
		public void shiftDown(int k, T x) {
			int half = count >>> 1, i = k;
			while (k < half) {
				// 获取左右子节点索引位置 left = root * 2 + 1, right = root * 2 + 2
				int child = (k << 1) + 1, right = child + 1;
				// 左子节点一定存在，先拿到左节点
				T e = items.get(child);
				// right < count: 防止越界，说明右子节点存在
				// 比较左右子节点，并从中选一个较小的: 即当leftChild.val > rightChild.val时，就换成右子节点
				if (right < count && (comparator == null
						? e.compareTo(items.get(right))
						: comparator.compare(e, items.get(right))) > 0) {
					child = right;
					e = items.get(child);
				}
				// 比较当前节点值和所选节点的值，如果 x.val <= e.val 不需要调整直接 break
				if ((comparator == null
						? x.compareTo(e)
						: comparator.compare(x, e)) <= 0) {
					break;
				}
				// 交换
				items.set(k, e);
				k = child;
			}
			// i != k 说明发生了交换
			if (i != k) items.set(k, x);
		}
		public T peek() {
			if (size() <= 0) return null;
			return items.get(0);
		}
		public int size() {
			return count;
		}
		public boolean isEmpty() {
			return size() == 0;
		}
	}

	public static class MyMaxHeap {
		private int[] heap;
		private final int limit;
		private int heapSize;

		public MyMaxHeap(int limit) {
			heap = new int[limit];
			this.limit = limit;
			heapSize = 0;
		}

		public boolean isEmpty() {
			return heapSize == 0;
		}

		public boolean isFull() {
			return heapSize == limit;
		}

		public void push(int value) {
			if (heapSize == limit) {
				throw new RuntimeException("heap is full");
			}
			heap[heapSize] = value;
			// value heapSize
			heapInsert(heap, heapSize++);
		}

		// 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
		// 剩下的数，依然保持大根堆组织
		public int pop() {
			int ans = heap[0];
			swap(heap, 0, --heapSize);
			heapify(heap, 0, heapSize);
			return ans;
		}

		// 新加进来的数，现在停在了index位置，请依次往上移动，
		// 移动到0位置，或者干不掉自己的父亲了，停！
		private void heapInsert(int[] arr, int index) {
			// [index] [index-1]/2
			// index == 0
			while (arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		// 从index位置，往下看，不断的下沉
		// 停：较大的孩子都不再比index位置的数大；已经没孩子了
		private void heapify(int[] arr, int index, int heapSize) {
			int left = index * 2 + 1;
			while (left < heapSize) { // 如果有左孩子，有没有右孩子，可能有可能没有！
				// 把较大孩子的下标，给largest
				int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
				largest = arr[largest] > arr[index] ? largest : index;
				if (largest == index) {
					break;
				}
				// index和较大孩子，要互换
				swap(arr, largest, index);
				index = largest;
				left = index * 2 + 1;
			}
		}
	}

	public static class RightMaxHeap {
		private int[] arr;
		private final int limit;
		private int size;

		public RightMaxHeap(int limit) {
			arr = new int[limit];
			this.limit = limit;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("heap is full");
			}
			arr[size++] = value;
		}

		public int pop() {
			int maxIndex = 0;
			for (int i = 1; i < size; i++) {
				if (arr[i] > arr[maxIndex]) {
					maxIndex = i;
				}
			}
			int ans = arr[maxIndex];
			arr[maxIndex] = arr[--size];
			return ans;
		}

	}

	public static void main(String[] args) {

		// 小根堆
//		PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2 - o1);
//		Heap<Integer> heap = new Heap<>();
		Heap<Integer> heap = new Heap<>((o1, o2) -> o2 - o1);
		heap.add(5);
		heap.add(7);
		heap.add(5);
		heap.add(3);
		// 5 , 3
		System.out.println("peek: " + heap.peek());
		heap.add(8);
		heap.add(0);
		heap.add(7);
		heap.add(0);
		heap.add(9);
		heap.add(0);
		System.out.println("peek: " + heap.peek());
		System.out.println("poll: " + heap.poll());
		System.out.println("poll: " + heap.poll());
		while (!heap.isEmpty()) {
			System.out.println(heap.poll());
		}

		int value = 1000;
		int limit = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			int curLimit = (int) (Math.random() * limit) + 1;
			MyMaxHeap my = new MyMaxHeap(curLimit);
			RightMaxHeap test = new RightMaxHeap(curLimit);
			int curOpTimes = (int) (Math.random() * limit);
			for (int j = 0; j < curOpTimes; j++) {
				if (my.isEmpty() != test.isEmpty()) {
					System.out.println("Oops!");
				}
				if (my.isFull() != test.isFull()) {
					System.out.println("Oops!");
				}
				if (my.isEmpty()) {
					int curValue = (int) (Math.random() * value);
					my.push(curValue);
					test.push(curValue);
				} else if (my.isFull()) {
					if (my.pop() != test.pop()) {
						System.out.println("Oops!");
					}
				} else {
					if (Math.random() < 0.5) {
						int curValue = (int) (Math.random() * value);
						my.push(curValue);
						test.push(curValue);
					} else {
						if (my.pop() != test.pop()) {
							System.out.println("Oops!");
						}
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
