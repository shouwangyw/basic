package com.yw.util;

/**
 * 区间查询最小值的线段树
 * 注意下标从1开始，不从0开始。比如你传入size = 8，则位置对应为1~8，而不是0~7
 *
 * @author yangwei
 */
public class SegmentTree {
    private int[] min;
    private int[] change;
    private boolean[] update;

    public SegmentTree(int size) {
        int maxN = size + 1;
        min = new int[maxN << 2];
        change = new int[maxN << 2];
        update = new boolean[maxN << 2];
        update(1, size, Integer.MAX_VALUE, 1, size, 1);
    }

    private void pushUp(int rt) {
        min[rt] = Math.min(min[rt << 1], min[rt << 1 | 1]);
    }

    private void pushDown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true;
            update[rt << 1 | 1] = true;
            change[rt << 1] = change[rt];
            change[rt << 1 | 1] = change[rt];
            min[rt << 1] = change[rt];
            min[rt << 1 | 1] = change[rt];
            update[rt] = false;
        }
    }

    // 最后三个参数是固定的, 每次传入相同的值即可:
    // l = 1(固定)
    // r = size(你设置的线段树大小)
    // rt = 1(固定)
    public void update(int L, int R, int C, int l, int r, int rt) {
        if (L <= l && r <= R) {
            update[rt] = true;
            change[rt] = C;
            min[rt] = C;
            return;
        }
        // 当前任务躲不掉，无法懒更新，要往下发
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);					// 先往下发一层
        if (L <= mid) update(L, R, C, l, mid, rt << 1);			// 任务下发给左边
        if (R > mid) update(L, R, C, mid + 1, r, rt << 1 | 1);	// 任务下发给右边
        pushUp(rt);
    }

    // 最后三个参数是固定的, 每次传入相同的值即可:
    // l = 1(固定)
    // r = size(你设置的线段树大小)
    // rt = 1(固定)
    public int query(int L, int R, int l, int r, int rt) {
        // 任务全包
        if (L <= l && r <= R) return min[rt];
        // 任务没全包
        int mid = (l + r) >> 1;
        pushDown(rt, mid - l + 1, r - mid);						// 先往下发一层
        int left = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
        if (L <= mid) left = query(L, R, l, mid, rt << 1);           // 任务下发给左边
        if (R > mid) right = query(L, R, mid + 1, r, rt << 1 | 1); // 任务下发给右边
        return Math.min(left, right);
    }

}