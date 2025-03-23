package com.yw.course.coding.class49;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangwei
 */
public class Problem_0489_RobotRoomCleaner {

	// 不要提交这个接口的内容
	interface Robot {
		boolean move();
		void turnLeft();
		void turnRight();
		void clean();
	}

	public static void cleanRoom(Robot robot) {
		clean(robot, 0, 0, 0, new HashSet<>());
	}
	private static final int[][] dirs = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	// 机器人robot当前来到的位置(x,y)，且之前没来过
	// 机器人脸冲什么方向d: 0 1 2 3
	// visited里记录了机器人走过哪些位置
	// 函数的功能：不要重复走visited里面的位置，把剩下的位置，都打扫干净！而且要回去！
	private static void clean(Robot robot, int x, int y, int d, Set<String> visited) {
		robot.clean();
		visited.add(x + "_" + y);
		for (int i = 0; i < dirs.length; i++) {
			// d = 0 :  0 1 2 3
			// d = 1 :  1 2 3 0
			// d = 2 :  2 3 0 1
			// d = 3 :  3 0 1 2
			// 下一步的方向！
			int nd = (i + d) % 4;
			// 当下一步的方向定了！下一步的位置在哪？(nx, ny)
			int nx = dirs[nd][0] + x, ny = dirs[nd][1] + y;
			if (!visited.contains(nx + "_" + ny) && robot.move())
				clean(robot, nx, ny, nd, visited);
			robot.turnRight();
		}
		// 负责回去：之前的位置，怎么到你的！你要回去，而且方向和到你之前，要一致！
		robot.turnRight();
		robot.turnRight();
		robot.move();
		robot.turnRight();
		robot.turnRight();
	}

}
