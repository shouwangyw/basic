package com.yw.course.coding.class34;

import java.util.*;

/**
 * @author yangwei
 */
public class Problem_0380_InsertDeleteGetRandom {

	public class RandomizedSet {
		private Map<Integer, Integer> map;
		private List<Integer> list;
		Random r = new Random();
		public RandomizedSet() {
			this.map = new HashMap<>();
			this.list= new ArrayList<>();
		}

		public boolean insert(int val) {
			if (map.containsKey(val)) return false;
			map.put(val, list.size());
			list.add(val);
			return true;
		}

		public boolean remove(int val) {
			if (!map.containsKey(val)) return false;
			int last  = list.get(list.size() - 1);
			int idx = map.get(val);
			list.set(idx, last);
			map.put(last, idx);
			list.remove(list.size() - 1);
			map.remove(val);
			return true;
		}

		public int getRandom() {
			return list.get(r.nextInt(list.size()));
		}
	}

}
