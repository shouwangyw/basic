package com.yw.course.advance.class13;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
public class Code04_MaxHappy {

	private static class Employee {
		int happy;
		List<Employee> subordinates;
		public Employee(int h) {
			happy = h;
			subordinates = new ArrayList<>();
		}
	}

	public static int maxHappy1(Employee boss) {
		if (boss == null) return 0;
		return process1(boss, false);
	}

	// 当前来到的节点叫cur，
	// up表示cur的上级是否来，
	// 该函数含义：
	// 如果up为true，表示在cur上级已经确定来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	// 如果up为false，表示在cur上级已经确定不来，的情况下，cur整棵树能够提供最大的快乐值是多少？
	public static int process1(Employee cur, boolean up) {
		if (up) { // 如果cur的上级来的话，cur没得选，只能不来
			int ans = 0;
			for (Employee next : cur.subordinates) {
				ans += process1(next, false);
			}
			return ans;
		} else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
			int p1 = cur.happy;
			int p2 = 0;
			for (Employee next : cur.subordinates) {
				p1 += process1(next, true);
				p2 += process1(next, false);
			}
			return Math.max(p1, p2);
		}
	}

	public static int maxHappy(Employee root) {
		Info allInfo = process(root);
		return Math.max(allInfo.no, allInfo.yes);
	}
	private static class Info {
		int no;
		int yes;
		public Info(int n, int y) {
			no = n;
			yes = y;
		}
	}
	private static Info process(Employee root) {
		if (root == null) return new Info(0, 0);
		int no = 0;
		int yes = root.happy;
		for (Employee employee : root.subordinates) {
			Info nextInfo = process(employee);
			no += Math.max(nextInfo.no, nextInfo.yes);
			yes += nextInfo.no;
		}
		return new Info(no, yes);
	}

	// for test
	public static Employee generateBoss(int maxLevel, int maxSubordinates, int maxHappy) {
		if (Math.random() < 0.02) {
			return null;
		}
		Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
		generateSubordinates(boss, 1, maxLevel, maxSubordinates, maxHappy);
		return boss;
	}

	// for test
	public static void generateSubordinates(Employee e, int level, int maxLevel, int maxSubordinates, int maxHappy) {
		if (level > maxLevel) return;
		int subordinatesSize = (int) (Math.random() * (maxSubordinates + 1));
		for (int i = 0; i < subordinatesSize; i++) {
			Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
			e.subordinates.add(next);
			generateSubordinates(next, level + 1, maxLevel, maxSubordinates, maxHappy);
		}
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxSubordinates = 7;
		int maxHappy = 100;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			Employee boss = generateBoss(maxLevel, maxSubordinates, maxHappy);
			if (maxHappy1(boss) != maxHappy(boss)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
