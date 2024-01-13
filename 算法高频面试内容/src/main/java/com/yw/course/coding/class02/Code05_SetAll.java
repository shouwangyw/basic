package com.yw.course.coding.class02;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwei
 */
public class Code05_SetAll {

	public static class MyHashMap<K, V> {
		private static class MyValue<V> {
			private V value;
			private long time;	// 记录每个值put进HashMap的时间戳
			public MyValue(V v, long t) {
				value = v;
				time = t;
			}
		}

		private Map<K, MyValue<V>> map;
		private long time;	// 全局时间戳
		private MyValue<V> setAll;	// 记录setAll时的值和时间戳
		public MyHashMap() {
			map = new HashMap<>();
			time = 0;
			setAll = new MyValue<>(null, -1);
		}

		public void put(K key, V value) {
			map.put(key, new MyValue<>(value, time++));
		}
		public void setAll(V value) {
			setAll = new MyValue<>(value, time++);
		}
		public V get(K key) {
			MyValue<V> val = map.get(key);
			if (val == null) return null;

			return val.time > setAll.time ? val.value : setAll.value;
		}
	}
}
