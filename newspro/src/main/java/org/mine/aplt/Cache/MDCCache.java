package org.mine.aplt.Cache;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;

/**
 * log日志MDC值缓存.
 * @Description: 
 * @ClassName: MDCCache
 * @author: wntl
 * @date: 2020年5月25日 下午7:42:44
 */
public class MDCCache implements InitializingBean{
	/**
	 * 扫描线程. 每隔一段时间扫描缓存是否需要清除.
	 * @Fields threadScan
	 */
	private Thread threadScan = null;
	
	public volatile boolean isRun = true;
	
	private static ConcurrentHashMap<Long, String> longCache = new ConcurrentHashMap<>();
	
	private static ConcurrentHashMap<String, String> stringCache = new ConcurrentHashMap<>();
	
	public static String get(Long key){
		return longCache.getOrDefault(key, null);
	}
	
	public static String get(String key){
		return stringCache.getOrDefault(key, null);
	}
	
	public static String set(Long key, String value){
		return longCache.put(key, value);
	}
	
	public static String set(String key, String value){
		return stringCache.put(key, value);
	}
	
	public static String remove(Long key){
		return longCache.containsKey(key) ? longCache.remove(key) : null;
	}
	
	public static String remove(String key){
		return longCache.containsKey(key) ? longCache.remove(key) : null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(threadScan == null){
			if(isRun){
				threadScan = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}
		
	}
}
