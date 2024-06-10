package com.yw.course.coding.class30;

import com.yw.entity.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yangwei
 */
public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {

	// 方法一：层序遍历
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> ans = new LinkedList<>();
		levelOrder(root, 0, ans);
		return ans;
	}
	private void levelOrder(TreeNode root, int level, List<List<Integer>> ans) {
		if (root == null) return;
		if (level == ans.size()) ans.add(new LinkedList<>());
		if ((level & 1) == 1) ans.get(level).add(0, root.val);
		else ans.get(level).add(root.val);
		levelOrder(root.left, level + 1, ans);
		levelOrder(root.right, level + 1, ans);
	}

	// 方法二：宽度优先遍历
	public static List<List<Integer>> zigzagLevelOrder0(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		LinkedList<TreeNode> deque = new LinkedList<>();
		deque.add(root);
		int size = 0;
		boolean isHead = true;
		while (!deque.isEmpty()) {
			size = deque.size();
			List<Integer> curLevel = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode cur = isHead ? deque.pollFirst() : deque.pollLast();
				curLevel.add(cur.val);
				if(isHead) {
					if (cur.left != null) {
						deque.addLast(cur.left);
					}
					if (cur.right != null) {
						deque.addLast(cur.right);
					}
				}else {
					if (cur.right != null) {
						deque.addFirst(cur.right);
					}
					if (cur.left != null) {
						deque.addFirst(cur.left);
					}
				}
			}
			ans.add(curLevel);
			isHead = !isHead;
		}
		return ans;
	}

}
