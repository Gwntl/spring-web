package com.quartz.traditional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.JobKey;

public class JobKeyTest {

	public static void main(String[] args) {
		Map<JobKey, String> map = new ConcurrentHashMap<>();
		JobKey key = new JobKey("a", "b");
		map.put(key, "");
		JobKey key1 = new JobKey("a1", "b");
		map.put(key1, "");
		JobKey key2 = new JobKey("a", "b1");
		map.put(key2, "");
		
		JobKey key3 = new JobKey("a", "b1");
		System.out.println(map.containsKey(key3));
	}
}
