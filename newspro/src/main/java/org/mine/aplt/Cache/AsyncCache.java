package org.mine.aplt.Cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.support.bean.GitContext;
import org.mine.dao.BatchTimingStepLogRegisterDao;
import org.mine.model.BatchTimingStepLogRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description 异步任务执行时日志缓存. 避免反复从数据库中读取值.</br>将查出来的日志信息存在内存中, 当使用完之后便立即抛弃.
 * @ClassName: AsyncCache
 * @author: wntl
 * @date: 2020年5月27日 下午7:12:43
 */
@Component
public class AsyncCache {
	private static final Logger logger = LoggerFactory.getLogger(AsyncCache.class);
	private static ConcurrentHashMap<Long, BatchTimingStepLogRegister> stepCache = new ConcurrentHashMap<>(1 << 6);
	private static ConcurrentHashMap<Long, Integer> innerCount = new ConcurrentHashMap<>();

	public static BatchTimingStepLogRegister get(Long key) {
		BatchTimingStepLogRegister cacheRegister = null;
		if (stepCache.containsKey(key)) {
			cacheRegister = stepCache.get(key);
		} else {
			//当第二次还未获取到日志记录时. 等待100ms. 可能存在数据库竞争的原因导致初始日志记录未及时插入库中.
			if(innerCount.containsKey(key)){
				while(true){
					if ((cacheRegister = GitContext.getBean(BatchTimingStepLogRegisterDao.class).selectOne1R(key , false)) != null) {
						cacheRegister = stepCache.put(key, cacheRegister);
						break;
					} else {
						try{
							TimeUnit.MILLISECONDS.sleep(100);
						} catch(InterruptedException e){
							logger.error("get timing log cache failed.");
						}
					}
				}
			} else {
				innerCount.put(key, 1);
				if ((cacheRegister = GitContext.getBean(BatchTimingStepLogRegisterDao.class).selectOne1R(key , false)) != null) {
					stepCache.put(key, cacheRegister);
				}
			}
		}
		return cacheRegister;
	}

	public static void remove(Long key) {
		if (stepCache.containsKey(key)) {
			stepCache.remove(key);
		}
		if (innerCount.containsKey(key)) {
			innerCount.remove(key);
		}
	}
}
