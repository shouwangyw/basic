package com.yw.course.coding.class34;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author yangwei
 */
public class Problem_0295_FindMedianFromDataStream {

	class MedianFinder {
		// 定义两个堆: 一个小根堆、一个大根堆
		private Queue<Integer> minHeap;
		private Queue<Integer> maxHeap;
		public MedianFinder() {
			minHeap = new PriorityQueue<>();
			maxHeap = new PriorityQueue<>((a, b) -> b - a);
		}

		public void addNum(int num) {
			// 如果大根堆空、或者大根堆堆顶元素大于num，则num直接进大根堆；否则，进小根堆
			if (maxHeap.isEmpty() || maxHeap.peek() >= num) maxHeap.offer(num);
			else minHeap.offer(num);
			// 调整大根堆和小根堆:
			//  小根堆中数量大于大根堆中数量，则弹出小根堆堆顶元素放入大根堆
			//  小根堆中数量+2等于大根堆中数量，则弹出大根堆堆顶元素放入小根堆
			if (minHeap.size() > maxHeap.size()) maxHeap.offer(minHeap.poll());
			if (minHeap.size() + 2 == maxHeap.size()) minHeap.offer(maxHeap.poll());
		}

		public double findMedian() {
			// 若是偶数，返回两个堆顶值相加除以2；否则，返回大根堆堆顶值
			return minHeap.size() == maxHeap.size() ? ((minHeap.peek() + maxHeap.peek()) / 2.0d) : maxHeap.peek();
		}
	}

}