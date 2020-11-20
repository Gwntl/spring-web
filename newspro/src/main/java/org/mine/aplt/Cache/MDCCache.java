package org.mine.aplt.Cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * log日志MDC值缓存.
 * @Description: 
 * @ClassName: MDCCache
 * @author: wntl
 * @date: 2020年5月25日 下午7:42:44
 */
public class MDCCache {

	private static ConcurrentHashMap<String, String> stringCache = new ConcurrentHashMap<>();

	public static String get(String key){
		return stringCache.getOrDefault(key, null);
	}

	public static void set(String key, String value){
		if (!stringCache.containsKey(key)) {
			stringCache.put(key, value);
		}
	}

	public static String remove(String key){
		return stringCache.containsKey(key) ? stringCache.remove(key) : null;
	}
}
