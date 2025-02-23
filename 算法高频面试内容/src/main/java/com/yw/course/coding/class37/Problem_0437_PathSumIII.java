package com.yw.course.coding.class37;

import com.yw.entity.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Problem_0437_PathSumIII {

	public int pathSum(TreeNode root, int targetSum) {
		Map<Long, Integer> prefixSumMap = new HashMap<>();
		prefixSumMap.put(0L, 1);
		return process(root, targetSum, 0L, prefixSumMap);
	}
	private int process(TreeNode root, int targetSum, long currentSum, Map<Long, Integer> prefixSumMap) {
		if (root == null) return 0;
		currentSum += root.val; // 更新前缀和
		int count = prefixSumMap.getOrDefault(currentSum - targetSum, 0);
		prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1); // 记录当前前缀和
		count += process(root.left, targetSum, currentSum, prefixSumMap);
		count += process(root.right, targetSum, currentSum, prefixSumMap);
		prefixSumMap.put(currentSum, prefixSumMap.get(currentSum) - 1); // 回溯，恢复状态
		return count;
	}
}
