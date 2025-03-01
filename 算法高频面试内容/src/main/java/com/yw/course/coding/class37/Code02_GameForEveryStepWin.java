package com.yw.course.coding.class37;


/**
 * 来自字节
 * 扑克牌中的红桃J和梅花Q找不到了，为了利用剩下的牌做游戏，小明设计了新的游戏规则
 * 1) A,2,3,4....10,J,Q,K分别对应1到13这些数字，大小王对应0
 * 2) 游戏人数为2人，轮流从牌堆里摸牌，每次摸到的牌只有“保留”和“使用”两个选项，且当前轮必须做出选择
 * 3) 如果选择“保留”当前牌，那么当前牌的分数加到总分里，并且可以一直持续到游戏结束
 * 4) 如果选择“使用”当前牌，那么当前牌的分数*3，加到总分上去，但是只有当前轮，下一轮，下下轮生效，之后轮效果消失。
 * 5) 每一轮总分大的人获胜
 * 假设小明知道每一轮对手做出选择之后的总分，返回小明在每一轮都赢的情况下，最终的最大分是多少
 * 如果小明怎么都无法保证每一轮都赢，返回-1
 *
 * @author yangwei
 */
public class Code02_GameForEveryStepWin {

	public static int maxScore(int[] cards, int[] scores) {
		return process(cards, scores, 0, 0, 0, 0);
	}
	// 当前来到idx位置，牌面值是cards[idx]
	// 对手第i轮的得分是scores[i]
	// hold: i之前保留的牌的总分
	// cur: 当前轮得到的加成是多少 = 之前的牌只算上"使用"的效果
	// next: 之前的牌，对idx的下一轮，使用效果的加成是多少
	// 返回值: 如果i...最后，不能全赢，返回-1；如果能全赢，返回最后一轮的最大值
	// index -> 26种
	// hold -> (1+2+3+..13) -> 91 -> 91 * 4 - (11 + 12) -> 341
	// cur -> 26 * 3 = 78
	// next -> 13 * 3 = 39
	// 26 * 341 * 79 * 39 -> 10^7
	public static int process(int[] cards, int[] scores, int idx, int hold, int cur, int next) {
		if (idx == 25) { // 最后一张
			int score = hold + cur + cards[idx] * 3;
			if (score <= scores[idx]) return -1;
			return score;
		}
		// 不是最后一张
		// 1. 保留
		int score1 = hold + cur + cards[idx];
		int p1 = -1;
		if (score1 > scores[idx])
			p1 = process(cards, scores, idx + 1, hold + cards[idx], next, 0);
		// 2. 使用
		int score2 = hold + cur + cards[idx] * 3;
		int p2 = -1;
		if (score2 > scores[idx])
			p2 = process(cards, scores, idx + 1, hold, next + cards[idx] * 3, cards[idx] * 3);
		return Math.max(p1, p2);
	}

	// index -> 26种
	// hold -> (1+2+3+..13) -> 91 -> 91 * 4 - (11 + 12) -> 341
	// cur -> 26
	// next -> 13
	// 26 * 341 * 26 * 13 -> ? * (10 ^ 5)

	// cur: 牌点数    ->  * 3之后是效果
	// next: 牌点数   ->  * 3之后是效果
	public static int process2(int[] cards, int[] scores, int idx, int hold, int cur, int next) {
		if (idx == 25) { // 最后一张
			int score = hold + cur * 3 + cards[idx] * 3;
			if (score <= scores[idx]) return -1;
			return score;
		}
		// 不仅最后一张
		// 保留
		int score1 = hold + cur * 3 + cards[idx];
		int p1 = -1;
		if (score1 > scores[idx])
			p1 = process2(cards, scores, idx + 1, hold + cards[idx], next, 0);
		// 爆发
		int score2 = hold + cur * 3 + cards[idx] * 3;
		int p2 = -1;
		if (score2 > scores[idx])
			p2 = process2(cards, scores, idx + 1, hold, next + cards[idx], cards[idx]);
		return Math.max(p1, p2);
	}
	
	// 改出动态规划，记忆化搜索！
	
	

}
