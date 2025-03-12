package com.yw.course.coding.class42;

import com.yw.util.CommonUtils;

import java.util.Arrays;

/**
 * 在一个数组中有 0 和1，数字 1 就是活细胞，0 就认为是死细胞，整个数组不是 0 就1。
 * 活细胞的规则就是活细胞会一直活。对于死细胞，它临近位置有一个1它就会复活。
 * 之后它复活之后会一直存活，但如果死细胞临近位置有两个1，它就不能复活。
 *
 * @author yangwei
 */
public class Code01_CellLifeStatus {

    public static void updateCellLifeStatus(int[] cells, int m) {
        // 定义两个辅助数组，分别记录每个0最左和最右1出现的位置
        int n = cells.length;
        int[] lpos = new int[n], rpos = new int[n];
        // 1. 从左往右遍历数组，得到lpos
        for (int i = 0, pos = -1; i < n; i++) {
            if (cells[i] == 1) pos = i;
            else lpos[i] = pos;
        }
        // 2. 从右往左遍历数组，得到rpos
        for (int i = n - 1, pos = -1; i >= 0; i--) {
            if (cells[i] == 1) pos = i;
            else rpos[i] = pos;
        }
        // 3. 再次遍历数组，计算m代后0的状态
        for (int i = 0; i < n; i++) {
            if (cells[i] == 1) continue;
            if (lpos[i] == -1 && rpos[i] != -1 && rpos[i] - i <= m) cells[i] = 1;
            if (lpos[i] != -1 && rpos[i] == -1 && i - lpos[i] <= m) cells[i] = 1;
            if (lpos[i] != -1 && rpos[i] != -1 && i - lpos[i] != rpos[i] - i && Math.min(i - lpos[i], rpos[i] - i) <= m) cells[i] = 1;
        }
    }

    public static void main(String[] args) {
//        int[] cells = {0, 1, 1, 0, 0, 0, 1, 1, 1, 0};
        int[] cells = {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0};

        int m = 5;
        for (int i = 0; i <= m; i++) {
            int[] newCells = CommonUtils.copyArray(cells);
            updateCellLifeStatus(newCells, i);
            System.out.println(i + ": " + Arrays.toString(newCells));
        }
    }
}
