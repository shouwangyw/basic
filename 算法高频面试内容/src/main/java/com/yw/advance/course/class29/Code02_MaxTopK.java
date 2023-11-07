package com.yw.advance.course.class29;

import java.util.Arrays;

import static com.yw.util.CommonUtils.*;

/**
 * @author yangwei
 */
public class Code02_MaxTopK {

    // 方法一：排序+收集，时间复杂度O(N*logN)
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) return new int[0];
        int n = arr.length;
        k = Math.min(n, k);
        Arrays.sort(arr);
        int[] ans = new int[k];
        for (int i = n - 1, j = 0; j < k; i--, j++) ans[j] = arr[i];
        return ans;
    }

    // 方法二：堆，时间复杂度O(N+K*logN)
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) return new int[0];
        int n = arr.length;
        k = Math.min(n, k);
        // 从底向上建堆，时间复杂度 O(N)
        for (int i = n - 1; i >= 0; i--) shiftDown(arr, i, n);
        // 只把前K个数放在arr末尾，然后收集，O(K*logN)
        for (int i = 1; i <= k; i++) {
            swap(arr, 0, n - i);
            shiftDown(arr, 0, n - i);
        }
        int[] ans = new int[k];
        for (int i = n - 1, j = 0; j < k; i--, j++) ans[j] = arr[i];
        return ans;
    }
    private static void shiftDown(int[] arr, int k, int limit) {
        int x = arr[k], half = limit >>> 1;
        while (k < half) {
            int l = (k << 1) + 1, r = l + 1;
            int childVal = arr[l];
            if (r < limit && arr[l] < arr[r]) {
                l = r;
                childVal = arr[l];
            }
            if (x >= childVal) break;
            arr[k] = childVal;
            k = l;
        }
        arr[k] = x;
    }

    // 方法三：改写快排，时间复杂度O(N+k*logk)
    public static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) return new int[0];
        int n = arr.length;
        k = Math.min(n, k);
        // 找到第(n-k)小的数，O(N)
        int num = minKth(arr, n - k), idx = 0;
        int[] ans = new int[k];
        // 先将所有大于num的数(n-(n-k)=k个)放入ans
        for (int i = 0; i < n; i++) {
            if (arr[i] > num) ans[idx++] = arr[i];
        }
        // 不足k个补num
        while (idx < k) ans[idx++] = num;
        // 排序，整理答案，O(k*logk)
        Arrays.sort(ans);
        for (int l = 0, r = k - 1; l < r; l++, r--) swap(ans, l, r);
        return ans;
    }
    // 时间复杂度O(N)
    private static int minKth(int[] arr, int idx) {
        int l = 0, r = arr.length - 1;
        while (l < r) {
            int pivot = arr[l + (int) (Math.random() * (r - l + 1))];
            int[] equals = partition3Way(arr, l, r, pivot);
            if (idx < equals[0]) r = equals[0] - 1;
            else if (idx > equals[1]) l = equals[1] + 1;
            else return pivot;
        }
        return arr[l];
    }
    private static int[] partition3Way(int[] arr, int l, int r, int pivot) {
        int lessR = l - 1, moreL = r + 1;
        while (l < moreL) {
            if (arr[l] < pivot) swap(arr, ++lessR, l++);
            else if (arr[l] > pivot) swap(arr, l, --moreL);
            else l++;
        }
        return new int[] {lessR + 1, moreL - 1};
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                pass = false;
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }

    private static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

}
