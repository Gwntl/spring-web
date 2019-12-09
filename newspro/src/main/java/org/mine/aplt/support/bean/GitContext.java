package org.mine.aplt.support.bean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class GitContext implements ApplicationContextAware{
	private static final Logger logger = LoggerFactory.getLogger(GitContext.class);
	private static Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
	private static Lock lock = new ReentrantLock();
	private static ApplicationContext applicationContext;
	
	/**
	 * @return the transActionTemplate
	 */
	public static TransactionTemplate getTransActionTemplate() {
		return (TransactionTemplate)applicationContext.getBean("transActionTemplate");
	}


	public static Object put(String key, Object value){
		try{
			lock.lock();
			if(key == null || value == null){
				throw GitWebException.GIT1001("key或value不允许为空!!!");
			}
			return dataMap.put(key, value);
		} catch(Exception e){
			throw e;
		} finally{
			lock.unlock();
		}
	}
	
	public static Long getLongValue(String key){
		return (Long)dataMap.get(key);
	}
	
	public static String getStringValue(String key){
		return dataMap.get(key).toString();
	}
	
	public static Map<String, Object> getDataMap(){
		return dataMap;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		GitContext.applicationContext = applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz){
		T t = null;
		try{
			t = applicationContext.getBean(clazz);
		} catch(Exception e){
			System.out.println(GitWebException.getStackTrace(e));
		}
		return t;
	}
	
	public static <T> T getBean(String beanName){
		T t = null;
		try{
			t = (T)applicationContext.getBean(beanName);
		} catch(NoSuchBeanDefinitionException e){
			throw e;
		}
		return t;
	}
	
	/**
	 * 环境内容初始化
	 * @param dataMap
	 */
	public static void init(Map dataMap){
		String transName = dataMap.get("transName") + "";
		loadMDC(transName);
	}
	
	/**
	 * 加载日志名称
	 * @param transName
	 */
	public static void loadMDC(String transName){
		if(CommonUtils.isEmpty(transName)){
			transName = "DEFAULT";
		}
		String threadName = Thread.currentThread().getName().replaceAll("-", " ").replaceAll("_", " ");
		logger.debug("Thread.Name {}", threadName);
		String[] threadNames = threadName.split("[ ]");
		logger.debug("Thread.info {}", CommonUtils.toString(threadNames));
		String threadNo = (threadNames == null || threadNames.length <= 0) ? "0" : threadNames[threadNames.length - 1];
		logger.debug("Thread.No {}", threadNo);
		if(!CommonUtils.isNumber(threadNo)){
			threadNo = "0";
		}
		logger.debug("MDC put value : {}", transName + "_" +threadNo);
		MDC.put("trade", transName + "_" +threadNo);
	}
	
	/**
	 * 独立事务处理
	 * @param operator
	 * @param map
	 */
	public static void doIndependentTransActionControl(final BatchOperator operator, final Map<String, Object> map){
		getTransActionTemplate().execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus status) {
				if(logger.isTraceEnabled()){
					logger.trace("IndependentTransAction input : BatchOperator : {}, map : {}"
							, operator.getClass(), CommonUtils.toString(map));
				}
				Object r = null;
				try{
					logger.trace("IndependentTransAction begin");
					r = operator.call(map);
					logger.trace("IndependentTransAction call() end");
					return r;
				} catch(Exception e){
					logger.trace("IndependentTransAction call() exception");
					status.setRollbackOnly();
					throw e;
				}
			}
		});
	}
}
