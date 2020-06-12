package org.mine.aplt.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.JobKey;

public class JobCache {

	private static Map<JobKey, String> jobCache = new ConcurrentHashMap<>();
	
	private static final String FILLER = "";
	
	public static void put(JobKey key){
		jobCache.put(key, FILLER);
	}
	
	public static boolean get(JobKey key){
		return jobCache.containsKey(key);
	}
	
	public static boolean remove(JobKey key){
		return jobCache.containsKey(key) && jobCache.remove(jobCache, FILLER);
	}
}
