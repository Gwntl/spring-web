package org.mine.aplt.support.bean;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class GitContext implements ApplicationContextAware{
	private static final Logger logger = LoggerFactory.getLogger(GitContext.class);
	private static Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
	private static Lock lock = new ReentrantLock();
	private static ApplicationContext applicationContext;
	private static JdbcTemplate jdbcTemplate;
	/**
	 * 线程安全的执行环境对象实例
	 */
	private static final ThreadLocal<CurrThreadExecutionEnv> executionEnv = new ThreadLocal<CurrThreadExecutionEnv>(){
		protected CurrThreadExecutionEnv initialValue() {
			CurrThreadExecutionEnv env = new CurrThreadExecutionEnv();
			env.setJdbcTemplate(jdbcTemplate);
	        return env;
}
	};
	
	/**
	 * @return the executionEnv
	 */
	private static CurrThreadExecutionEnv getExecutionEnv() {
		return executionEnv.get();
	}
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		GitContext.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 继承原有事务处理.
	 * @return the transActionTemplate
	 */
	public static TransactionTemplate getTransActionTemplate() {
		return (TransactionTemplate)applicationContext.getBean("transActionTemplate");
	}
	
	/**
	 * 独立事务下的处理. 当启用该方法时会直接另起一个事务进行处理.
	 * @return the independentTransActionTemplate
	 */
	public static TransactionTemplate getIndependentTransActionTemplate() {
		return (TransactionTemplate)applicationContext.getBean("independentTransActionTemplate");
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
			logger.error(GitWebException.getStackTrace(e));
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		T t = null;
		try{
			t = (T)applicationContext.getBean(beanName);
		} catch(NoSuchBeanDefinitionException e){
			throw e;
		}
		return t;
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> clazz){
		Map<String, T> t = null;
		try{
			t = (Map<String, T>) applicationContext.getBeansOfType(clazz);
		} catch(Exception e){
			logger.error(GitWebException.getStackTrace(e));
		}
		return t;
	}
	
	/**
	 * 环境内容初始化
	 * @param dataMap
	 */
	public static void init(@SuppressWarnings("rawtypes") Map dataMap){
		String transName = dataMap.get("transName") + "";
		loadMDC(transName);
	}
	
	public static void init(String transName){
		loadMDC(transName);
	}
	
	public static void clear(){
		MDC.clear();
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
		MDC.put("trade", transName + "_" + threadNo);
	}
	
	/**
	 * 创建事务点
	 * @param savepointName
	 */
	public static void createSavepoint(String savepointName) {
		getExecutionEnv().createSavepoint(savepointName);
	}
	
	/**
	 * 回滚事务点
	 * @param savepointName
	 */
	public static void rollbackSavepoint(String savepointName) {
		getExecutionEnv().rollbackSavepoint(savepointName);
	}
	
	/**
	 * 释放事务点
	 * @param savepointName
	 */
	public static void releaseSavepoint(String savepointName) {
		getExecutionEnv().releaseSavepoint(savepointName);
	}
	
	/**
	 * 继承原有事务处理
	 * @param operator
	 * @param input
	 */
	@SuppressWarnings("unchecked")
	public static <R, I> R doTransActionControl(final BatchOperator<R, I> operator, final I input) {
		return (R)getTransActionTemplate().execute(new TransactionCallback<Object>() {

			@Override
			public R doInTransaction(TransactionStatus status) {
				if(logger.isTraceEnabled()){
					logger.trace("IndependentTransAction input : BatchOperator : {}, clazz : {}"
							, operator.getClass(), input.getClass());
				}
				R r = null;
				try{
					logger.trace("IndependentTransAction begin");
					getExecutionEnv().setStatus(status);
					r = operator.call(input);
					logger.trace("IndependentTransAction call() end");
					return r;
				} catch(Exception e){
					logger.trace("IndependentTransAction call() exception");
					status.setRollbackOnly();
					throw e;
				} finally{
					logger.trace("IndependentTransAction call() finally");
					getExecutionEnv().releaseTransactionStatus();
				}
			}
		});
	}
	
	/**
	 * 独立事务处理
	 * @param operator
	 * @param input
	 */
	@SuppressWarnings("unchecked")
	public static <R, I> R doIndependentTransActionControl(final BatchOperator<R, I> operator, final I input) {
		return (R)getIndependentTransActionTemplate().execute(new TransactionCallback<Object>() {

			@Override
			public R doInTransaction(TransactionStatus status) {
				if(logger.isTraceEnabled()){
					logger.trace("IndependentTransAction input : BatchOperator : {}, clazz : {}"
							, operator.getClass(), input.getClass());
				}
				R r = null;
				try{
					logger.trace("IndependentTransAction begin");
					getExecutionEnv().setStatus(status);
					r = operator.call(input);
					logger.trace("IndependentTransAction call() end");
					return r;
				} catch(Exception e){
					logger.trace("IndependentTransAction call() exception");
					status.setRollbackOnly();
					throw e;
				} finally{
					logger.trace("IndependentTransAction call() finally");
					getExecutionEnv().releaseTransactionStatus();
				}
			}
		});
	}
	
	/**数据库操作**/
	/**
	 * 直接调用Spring提供的jdbstemplate方法. 查询出的结果为List<Map>类型. key-字段名, value-字段对应值.
	 * @param sql 需执行的sql语句
	 * @param args 参数值
	 * @return
	 */
	public static List<Map<String, Object>> queryForListMap(String sql, Object[] args) {
		return getExecutionEnv().queryForList(sql, args);
	}

	/**
	* 查询单个字段结果为List
	* @param sql
	* @param args
	* @param type
	* @return: java.util.List<T>
	* @Author: wntl
	* @Date: 2020/8/14
	*/
	public static <T> List<T> queryForSingleFieldList(String sql, Object[] args, Class<T> type) {
		return getExecutionEnv().queryForSingleFieldList(sql, args, type);
	}
	
	/**
	 * 查询出的结果为List类型.
	 * @param sql 需执行的sql语句
	 * @param args 参数值
	 * @param returnType 返回对象类型
	 * @return
	 */
	public static <T> List<T> queryForList(String sql, Object[] args, Class<T> returnType) {
		return getExecutionEnv().queryForList(sql, args, returnType);
	}
	
	/**
	 * 查询出的结果为Map类型. key-字段名, value-字段对应值.
	 * @param sql
	 * @param args
	 * @return
	 */
	public static Map<String, Object> queryForMap(String sql, Object[] args) {
		return getExecutionEnv().queryForMap(sql, args);
	}
	
	/**
	 * 根据指定字段查询出Map结果集合.
	 * <p>
	 * 		sql : select key, value from system.
	 * 	<code>
	 * 		queryForMap(sql,null,"key","value");
	 * </code>
	 * 		result : Map:{key=value}
	 * </p>
	 * @param sql
	 * @param args
	 * @param key key字段值
	 * @param value value字段值
	 * @return
	 */
	public static Map<String, Object> queryForMap(String sql, Object[] args, String key, String value) {
		return getExecutionEnv().queryForMap(sql, args, key, value);
	}
	
	/**
	 * 查询出指定对象值
	 * @param sql
	 * @param args
	 * @param clz
	 * @return
	 */
	public static <T> T queryForObject(String sql, Object[] args, Class<T> clz) {
		return getExecutionEnv().queryForObject(sql, args, clz);
	}

	/**
	* 查询String值
	* @param sql
	* @return: java.lang.String
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static String queryForString(String sql) {
		return getExecutionEnv().queryForObject(sql, String.class);
	}

	/**
	* 根据条件查询String值
	* @param sql
	* @param args
	* @return: java.lang.String
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static String queryForString(String sql, Object[] args){
		return getExecutionEnv().queryForObject(sql, args, String.class);
	}

	/**
	* 查询Long值
	* @param sql
	* @return: java.lang.Long
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static Long queryForLong(String sql) {
		return getExecutionEnv().queryForObject(sql, Long.class);
	}

	/**
	* 根据条件查询处Long值
	* @param sql
	* @param args
	* @return: java.lang.Long
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static Long queryForLong(String sql, Object[] args){
		return getExecutionEnv().queryForObject(sql, args, Long.class);
	}

	/**
	*
	* @param sql
	* @return: java.lang.Integer
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static Integer queryForInteger(String sql) {
		return getExecutionEnv().queryForObject(sql, Integer.class);
	}

	/**
	*
	* @param sql
	* @param args
	* @return: java.lang.Integer
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static Integer queryForInteger(String sql, Object[] args){
		return getExecutionEnv().queryForObject(sql, args, Integer.class);
	}

	/**
	*
	* @param sql
	* @return: java.math.BigDecimal
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static BigDecimal queryForBigDecimal(String sql) {
		return getExecutionEnv().queryForObject(sql, BigDecimal.class);
	}

	public static Object[] queryForArrayByExtractor(String sql, Object[] args) {
		return getExecutionEnv().queryForArrayByExtractor(sql, args);
	}

	/**
	* 查询数组
	* @param sql
	* @param args
	* @return: java.util.List<java.lang.Object[]>
	* @Author: wntl
	* @Date: 2020/8/25
	*/
	public static List<Object[]> queryForArraysByExtractor(String sql, Object[] args){
		return getExecutionEnv().queryForArraysByExtractor(sql, args);
	}
	/**
	* 查询数组
	* @param sql
	* @param args
	* @return: java.util.List<java.lang.Object[]>
	* @Author: wntl
	* @Date: 2020/8/25
	*/
	public static List<Object[]> queryForArraysByRowMapper(String sql, Object[] args){
		return getExecutionEnv().queryForArraysByRowMapper(sql, args);
	}

	
	public static int update(String sql) {
		return getExecutionEnv().update(sql);
	}
	
	public static int update(String sql, Object[] args) {
		return getExecutionEnv().update(sql, args);
	}
	
	public static int[] batchUpdate(String sql) {
		return getExecutionEnv().batchUpdate(sql);
	}
	
	public static int [] batchUpdate(String sql, List<Object[]> batchArgs) {
		return getExecutionEnv().batchUpdate(sql, batchArgs);
	}
}
