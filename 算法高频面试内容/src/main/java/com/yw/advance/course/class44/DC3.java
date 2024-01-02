package com.yw.advance.course.class44;

/**
 * @author yangwei
 */
public class DC3 {
	/**
	 * 			[a,a,b,a]
	 *  		 0 1 2 3
	 * sa = 	[3,0,1,2] 	第几名在原数组的位置
	 * rank = 	[1,2,3,0]	原数组位置对应第几名
	 * h	=	[1,1,0,0]
	 *
	 * h数组
	 * h[i]：假设以i位置开始的后缀字符串的排名x，该子串与排名x-1的字符串的最长公共前缀多长
	 * 比如：i=0是"aaba"排名1, 上一排名是0的字符串"a"，最长公共前缀"a"长度1，∴ h[0] = 1
	 * 		i=1是"aba"排名2, 上一排名是1的字符串"aaba"，最长公共前缀"a"长度1，∴ h[1] = 1
	 * 		i=2是"ba"排名3, 上一排名是2的字符串"aba"，最长公共前缀没有，∴ h[2] = 0
	 * 		i=3是"a"排名0, 上一排名没有，∴ h[3] = 0
	 * height数组
	 * height[i]: 假设sa[i]位置的后缀字符串x，sa[i-1]位置的后缀字符串y，x与y的最长公共前缀长度
	 * 比如：i=0对应sa[0]=3, 字符串x是"a"，sa[-1]不存在，∴ height[0] = 0
	 * 		i=1对应sa[1]=0, 字符串x是"aaba"，sa[0]=3, 字符串y是"a"，最长公共前缀"a"长度1，∴ height[1] = 1
	 * 		i=2对应sa[2]=1, 字符串x是"aba"，sa[1]=0, 字符串y是"aaba"，最长公共前缀"a"长度1，∴ height[2] = 1
	 * 		i=3对应sa[3]=2, 字符串x是"ba"，sa[2]=1, 字符串y是"aba"，最长公共前缀没有，∴ height[3] = 0
	 */
	private int[] sa;
	private int[] rank;
	private int[] height;

	// 构造方法的约定:
	// 1. 数组叫nums，如果你是字符串，请转成整型数组nums
	// 2. 数组中，最小值>=1，如果不满足，处理成满足的，也不会影响使用
	// 3. max是nums里面的最大值
	public DC3(int[] nums, int max) {
		sa = sa(nums, max);
		rank = rank();
		height = height(nums);
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

	private int[] height(int[] s) {
		int n = s.length;
		int[] ans = new int[n];
		for (int i = 0, k = 0; i < n; ++i) {
			if (rank[i] != 0) {
				if (k > 0) {
					--k;
				}
				int j = sa[rank[i] - 1];
				while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
					++k;
				}
				ans[rank[i]] = k;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 100000;
		int maxValue = 100;
		long start = System.currentTimeMillis();
		new DC3(randomArray(len, maxValue), maxValue);
		long end = System.currentTimeMillis();
		System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
	}
	public static int[] randomArray(int len, int maxValue) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * maxValue) + 1;
		}
		return arr;
	}
}