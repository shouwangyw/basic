package com.yw.course.advance.class07;

import com.yw.entity.Person;

import java.util.*;

/**
 * 加强堆：维护一个元素反向索引表，并且提供高效的 调整、元素删除等方法
 * @author yangwei
 */
public class HeapPlus<T extends Comparable<? super T>> {
    private final List<T> items;
    private final Comparator<? super T> comparator;
    private final Map<T, Integer> itemIndexMap;
    private int count;

    public HeapPlus() {
        this(Comparator.naturalOrder());
    }

    public HeapPlus(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.items = new ArrayList<>();
        this.count = 0;
        itemIndexMap = new HashMap<>();
    }

    public void add(T item) {
        items.add(item);
        itemIndexMap.put(item, count);
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
            save(k, e);
            k = parent;
        }
        // i != k 说明发生了交换
        if (i != k) save(k, x);
    }

    public T poll() {
        if (size() == 0) return null;
        // 取出堆顶元素，并将末尾元素放到堆顶
        T e = items.get(0);
        itemIndexMap.remove(e);
        T item = items.remove(--count);
        if (count > 0) {
            // 先将最末尾元素放到头位置(0索引位置)
            save(0, item);
            // 将item向下调整
            shiftDown(0, item);
        }
        return e;
    }

    public void remove(T item) {
        // 先从索引表中移除，并返回在索引表中的位置
        Integer idx = itemIndexMap.remove(item);
        // 如果删除元素不存在，直接返回
        if (idx == null) return;
        // 移除取出末尾元素
        T t = items.remove(--count);
        if (idx == count) {
            // 如果是最后一个元素，不需要调整
            return;
        }
        // 与被删除元素位置交换
        save(idx, t);
        // 重新调整堆结构
        reBalance(idx, t);
    }
    // 堆中元素item发生修改，重平衡
    public void reBalance(T item) {
        reBalance(itemIndexMap.get(item), item);
    }

    private void reBalance(Integer idx, T item) {
        if (idx == null) return;
        shiftUp(idx, item);
        shiftDown(idx, item);
    }
    // 返回所有元素
    public List<T> getItems() {
        return items;
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
            save(k, e);
            k = child;
        }
        // i != k 说明发生了交换
        if (i != k) save(k, x);
    }
    // 统一在设置的地方维护元素索引表
    private void save(int idx, T item) {
        items.set(idx, item);
        itemIndexMap.put(item, idx);
    }

    public boolean exist(T item) {
        return itemIndexMap.containsKey(item);
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

    public static void main(String[] args) {
        HeapPlus<String> heapPlus = new HeapPlus<>();
        heapPlus.add("2李四");
        heapPlus.add("1张三");
        heapPlus.add("6王八");
        heapPlus.add("4赵六");
        heapPlus.add("5田七");
        heapPlus.add("3王五");

//        while (!heapPlus.isEmpty()) System.out.println(heapPlus.poll());

        heapPlus.remove("1张三");
        System.out.println("peek: " + heapPlus.peek());

        heapPlus.remove("4赵六");
        while (!heapPlus.isEmpty()) System.out.println(heapPlus.poll());

        System.out.println("================");

        HeapPlus<Person> personHeap = new HeapPlus<>(Comparator.comparingInt(o -> o.age));
        Person p1 = new Person("张三", 13);
        Person p2 = new Person("李四", 34);
        Person p3 = new Person("王五", 25);
        Person p4 = new Person("赵六", 66);
        Person p5 = new Person("田七", 27);
        Person p6 = new Person("王八", 48);
        personHeap.add(p1);
        personHeap.add(p2);
        personHeap.add(p3);
        personHeap.add(p4);
        personHeap.add(p5);
        personHeap.add(p6);
        for (Person item : personHeap.getItems()) {
            System.out.println(item);
        }
        System.out.println("================");

        p4.age = 6;
        personHeap.reBalance(p4);
        while (!personHeap.isEmpty()) System.out.println(personHeap.poll());
    }
}