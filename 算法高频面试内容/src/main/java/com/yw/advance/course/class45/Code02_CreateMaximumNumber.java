package com.yw.advance.course.class45;

/**
 * 测试链接: https://leetcode.cn/problems/create-maximum-number/
 *
 * @author yangwei
 */
public class Code02_CreateMaximumNumber {

    // 方法一：未使用DC3优化版本
    public static int[] maxNumber0(int[] nums1, int[] nums2, int k) {
        int n1 = nums1.length, n2 = nums2.length;
        if (k < 0 || k > n1 + n2) return null;
        // 生成dp表
        int[][] dp1 = getDp(nums1), dp2 = getDp(nums2);
        int[] ans = new int[k];
        int down = Math.max(0, k - n2), up = Math.min(k, n1);
        for (int i = down; i <= up; i++) {
            // nums1中挑i个，怎么得到一个最优结果
            int[] pick1 = maxPick(nums1, dp1, i), pick2 = maxPick(nums2, dp2, k - i);
            int[] merge = merge(pick1, pick2);
            ans = preMoreThanLast(ans, 0, merge, 0) ? ans : merge;
        }
        return ans;
    }
    private static int[][] getDp(int[] arr) {
        int n = arr.length;
        // dp[i][j]：表示只能从 i 出发及其往后位置一定要挑 j 个数，所组成的最大数结果的那个数的开始位置在哪
        int[][] dp = new int[n][n + 1];
        // 第0列无意义，i>n-j的位置数不够，也是无效的
        // n - i == j的位置只能都要，所以 dp[i][j] = i
        for (int i = 0; i < n; i++) dp[i][n - i] = i;
        // 普遍位置：从左往右、从下往上
        for (int j = 1; j <= n; j++) {
            for (int i = n - j - 1; i >= 0; i--) {
                dp[i][j] = arr[i] < arr[dp[i + 1][j]] ? dp[i + 1][j] : i;
            }
        }
        return dp;
    }
    private static int[] maxPick(int[] arr, int[][] dp, int m) {
        int[] ans = new int[m];
        for (int i = 0, dpIdx = 0; m > 0; i++) {
            int pickIdx = dp[dpIdx][m--];
            ans[i] = arr[pickIdx];
            dpIdx = pickIdx + 1;
        }
        return ans;
    }
    private static int[] merge(int[] nums1, int[] nums2) {
        int k = nums1.length + nums2.length;
        int[] ans = new int[k];
        for (int i = 0, j = 0, r = 0; r < k; ++r) {
            ans[r] = preMoreThanLast(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return ans;
    }
    private static boolean preMoreThanLast(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    // 方法三：使用DC3优化版本
    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int n1 = nums1.length, n2 = nums2.length;
        if (k < 0 || k > n1 + n2) return null;
        // 生成dp表
        int[][] dp1 = getDp(nums1), dp2 = getDp(nums2);
        int[] ans = new int[k];
        int down = Math.max(0, k - n2), up = Math.min(k, n1);
        for (int i = down; i <= up; i++) {
            int[] pick1 = maxPick(nums1, dp1, i), pick2 = maxPick(nums2, dp2, k - i);
            int[] merge = mergeBySuffixArray(pick1, pick2);
            ans = moreThan(ans, merge) ? ans : merge;
        }
        return ans;
    }
    private static boolean moreThan(int[] pre, int[] last) {
        int i = 0;
        int j = 0;
        while (i < pre.length && j < last.length && pre[i] == last[j]) {
            i++;
            j++;
        }
        return j == last.length || (i < pre.length && pre[i] > last[j]);
    }
    private static int[] mergeBySuffixArray(int[] nums1, int[] nums2) {
        int size1 = nums1.length;
        int size2 = nums2.length;
        int[] nums = new int[size1 + 1 + size2];
        for (int i = 0; i < size1; i++) {
            nums[i] = nums1[i] + 2;
        }
        nums[size1] = 1;
        for (int j = 0; j < size2; j++) {
            nums[j + size1 + 1] = nums2[j] + 2;
        }
        DC3 dc3 = new DC3(nums, 11);
        int[] rank = dc3.rank;
        int[] ans = new int[size1 + size2];
        int i = 0;
        int j = 0;
        int r = 0;
        while (i < size1 && j < size2) {
            ans[r++] = rank[i] > rank[j + size1 + 1] ? nums1[i++] : nums2[j++];
        }
        while (i < size1) {
            ans[r++] = nums1[i++];
        }
        while (j < size2) {
            ans[r++] = nums2[j++];
        }
        return ans;
    }
    public static class DC3 {
        private int[] sa;
        private int[] rank;

        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
            rank = rank();
        }

        private int[] sa(int[] nums, int max) {
            int n = nums.length;
            int[] arr = new int[n + 3];
            for (int i = 0; i < n; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, n, max);
        }

        private int[] skew(int[] nums, int n, int K) {
            int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
            int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
            for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
                if (0 != i % 3) {
                    s12[j++] = i;
                }
            }
            radixPass(nums, s12, sa12, 2, n02, K);
            radixPass(nums, sa12, s12, 1, n02, K);
            radixPass(nums, s12, sa12, 0, n02, K);
            int name = 0, c0 = -1, c1 = -1, c2 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                    name++;
                    c0 = nums[sa12[i]];
                    c1 = nums[sa12[i] + 1];
                    c2 = nums[sa12[i] + 2];
                }
                if (1 == sa12[i] % 3) {
                    s12[sa12[i] / 3] = name;
                } else {
                    s12[sa12[i] / 3 + n0] = name;
                }
            }
            if (name < n02) {
                sa12 = skew(s12, n02, name);
                for (int i = 0; i < n02; i++) {
                    s12[sa12[i]] = i + 1;
                }
            } else {
                for (int i = 0; i < n02; i++) {
                    sa12[s12[i] - 1] = i;
                }
            }
            int[] s0 = new int[n0], sa0 = new int[n0];
            for (int i = 0, j = 0; i < n02; i++) {
                if (sa12[i] < n0) {
                    s0[j++] = 3 * sa12[i];
                }
            }
            radixPass(nums, s0, sa0, 0, n0, K);
            int[] sa = new int[n];
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
                int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                int j = sa0[p];
                if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
                        : leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                    sa[k] = i;
                    t++;
                    if (t == n02) {
                        for (k++; p < n0; p++, k++) {
                            sa[k] = sa0[p];
                        }
                    }
                } else {
                    sa[k] = j;
                    p++;
                    if (p == n0) {
                        for (k++; t < n02; t++, k++) {
                            sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                        }
                    }
                }
            }
            return sa;
        }

        private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
            int[] cnt = new int[k + 1];
            for (int i = 0; i < n; ++i) {
                cnt[nums[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < cnt.length; ++i) {
                int t = cnt[i];
                cnt[i] = sum;
                sum += t;
            }
            for (int i = 0; i < n; ++i) {
                output[cnt[nums[input[i] + offset]]++] = input[i];
            }
        }

        private boolean leq(int a1, int a2, int b1, int b2) {
            return a1 < b1 || (a1 == b1 && a2 <= b2);
        }

        private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
            return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
        }

        private int[] rank() {
            int n = sa.length;
            int[] ans = new int[n];
            for (int i = 0; i < n; i++) {
                ans[sa[i]] = i;
            }
            return ans;
        }
    }
}
