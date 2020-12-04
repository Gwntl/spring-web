package org.mine.aplt.support.bean;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrThreadExecutionEnv {
	private static final Logger logger = LoggerFactory.getLogger(CurrThreadExecutionEnv.class);
	/**
	 * 事务点前缀
	 */
	public static final String SAVEPOINT_NAME_PREFIX = "SAVEPOINT_";
	/**
	 * 事务状态,由Spring管理(从ThreadLocal对象中获取)
	 */
	private TransactionStatus status;
	private List<TransactionStatus> statusList;
	/**
	 * Spring封装的JDBC操作
	 */
	private JdbcTemplate jdbcTemplate;
	/**
	 * 事务点计数器
	 */
	private int savepointCounter = 0;
	/**
	 * 事务点缓存对象
	 */
	private Map<String, InnerSavepoint> savepointMap;

	private Map<String, Object> systemParams;
	
	public CurrThreadExecutionEnv() {
		status = null;
		savepointMap = new HashMap<>();
		statusList = new ArrayList<>();
		systemParams = new HashMap<>();
	}
	
	/**
	 * @return the status
	 */
	public TransactionStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TransactionStatus status) {
		statusList.add(this.status = status);
	}
	
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public TransactionStatus releaseTransactionStatus(){
		TransactionStatus releaseTransactionStatus = null;
		if(statusList.size() > 0){
			releaseTransactionStatus = statusList.remove(statusList.size() - 1);
		}
		if(statusList.size() > 0){
			status = statusList.get(statusList.size() - 1);
		}
		return releaseTransactionStatus;
	}

	/**
	 * 获取当前环境的数据库连接对象
	 * @return
	 */
	protected Connection getConnection() {
		if(status == null){
			throw GitWebException.GIT_DB_CONNECT("当前线程环境下未初始化事务连接.");
		}
		JdbcTransactionObjectSupport support = (JdbcTransactionObjectSupport) ((DefaultTransactionStatus) status)
				.getTransaction();
		return support.getConnectionHolder().getConnection();
	}
	
	/**
	 * 创建事务点
	 * @param savepointName 事务点名称
	 */
	public void createSavepoint(String savepointName) {
		if(savepointMap.get(savepointName) != null){
			throw GitWebException.GIT_DB_OPERATOR("当前环境已创建该事务点" + savepointName);
		}
		
		Connection connection = getConnection();
		if(connection == null){
			throw GitWebException.GIT_DB_CONNECT("获取当前环境数据库连接失败");	
		}
		try {
			InnerSavepoint innerSavepoint = new InnerSavepoint(savepointName, savepointCounter++);
			innerSavepoint.savepoint = connection.setSavepoint(savepointName);
			savepointMap.put(innerSavepoint.savepointName, innerSavepoint);
		} catch (SQLException e) {
			throw GitWebException.GIT_DB_OPERATOR("当前环境创建事务点" + savepointName +"失败");
		}
	}
	
	/**
	 * 回滚事务点
	 * @param savepointName
	 */
	public void rollbackSavepoint(String savepointName) {
		if(logger.isTraceEnabled()){
			logger.trace("当前环境事务点内容为: {}", CommonUtils.toString(savepointMap));
		}
		InnerSavepoint innerSavepoint = savepointMap.get(savepointName);
		if(innerSavepoint == null){
			throw GitWebException.GIT_DB_OPERATOR("当前环境不存在该事务点" + savepointName);
		}
		Connection connection = getConnection();
		if(connection == null){
			throw GitWebException.GIT_DB_CONNECT("获取当前环境数据库连接失败");	
		}
		try {
			connection.rollback(innerSavepoint.savepoint);
			savepointMap.remove(savepointName);
		} catch (SQLException e) {
			throw GitWebException.GIT_DB_OPERATOR("当前环境回滚事务点" + savepointName +"失败");
		}
		
		if(logger.isTraceEnabled()){
			logger.trace("当前回滚事务点为: {}", innerSavepoint.toString());
		}
	}
	
	/**
	 * 撤销事务点
	 * @param savepointName
	 */
	public void releaseSavepoint(String savepointName) {
		if(logger.isTraceEnabled()){
			logger.trace("当前环境事务点内容为: {}", CommonUtils.toString(savepointMap));
		}
		InnerSavepoint innerSavepoint = savepointMap.get(savepointName);
		if(innerSavepoint == null){
			throw GitWebException.GIT_DB_OPERATOR("当前环境不存在该事务点" + savepointName);
		}
		Connection connection = getConnection();
		if(connection == null){
			throw GitWebException.GIT_DB_CONNECT("获取当前环境数据库连接失败");	
		}
		try {
			connection.releaseSavepoint(innerSavepoint.savepoint);
			savepointMap.remove(savepointName);
		} catch (SQLException e) {
			throw GitWebException.GIT_DB_OPERATOR("当前环境回滚事务点" + savepointName +"失败");
		}
		
		if(logger.isTraceEnabled()){
			logger.trace("当前撤销事务点为: {}", innerSavepoint.toString());
		}
	}
	
	class InnerSavepoint {
		private String savepointName;
		private String innerName;
		private Savepoint savepoint;
		public InnerSavepoint(String savepointName, int savepointCounter) {
			this.savepointName = savepointName;
			this.innerName = SAVEPOINT_NAME_PREFIX + savepointCounter;
		}
		@Override
		public String toString() {
			return "InnerSavepoint [savepointName=" + savepointName + ", innerName=" + innerName + ", savepoint="
					+ savepoint + "]";
		}
	}

	public <T> List<T> queryForSingleFieldList(String sql, Object[] args, Class<T> elementType) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
			logger.debug("queryForSingleFieldList elementType : {}", elementType);
		}
		try {
			return jdbcTemplate.queryForList(sql, args, elementType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForSingleFieldList failed", de);
		}
	}

	public Object[] queryForArrayByExtractor(final String sql, Object[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForArrayByExtractor sql : {}", sql);
			logger.debug("queryForArrayByExtractor args : {}", CommonUtils.toString(args));
		}
		try {
			return this.jdbcTemplate.query(sql, new ResultSetExtractor<Object[]>() {
				@Override
				public Object[] extractData(ResultSet rs) throws SQLException, DataAccessException {
					int count = 0;
					Object[] obj = null;
					while (rs.next()) {
						if (count > 0) {
							throw new TooManyResultsException(sql);
						}
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						obj = new Object[columnCount];
						for (int index = 1; index <= columnCount; index++) {
							obj[index - 1] = JdbcUtils.getResultSetValue(rs, index);
						}
						count ++;
					}
					return obj;
				}
			}, args);
		}  catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForArrayByExtractor failed", de);
		}
	}

	public List<Object[]> queryForArraysByExtractor(String sql, Object[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
		}
		try {
			return this.jdbcTemplate.query(sql, new ResultSetExtractor<List<Object[]>>() {
				@Override
				public List<Object[]> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Object[]> objects = new ArrayList<>();
					while(rs.next()) {
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						Object[] obj = new Object[columnCount];
						for (int index = 1; index <= columnCount; index++) {
							obj[index - 1] = JdbcUtils.getResultSetValue(rs, index);
						}
						objects.add(obj);
					}
					return objects;
				}
			}, args);
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForArraysByExtractor failed", de);
		}
	}

	public List<Object[]> queryForArraysByRowMapper(String sql, Object[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
		}
		try {
			return this.jdbcTemplate.query(sql, args, new RowMapper<Object[]>() {
				@Override
				public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
					int count = rs.getMetaData().getColumnCount();
					Object[] object = new Object[count];
					for (int index = 1; index <= count; index++) {
						object[index - 1] = JdbcUtils.getResultSetValue(rs, index);
					}
					return object;
				}
			});
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForArraysByRowMapper failed", de);
		}
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
		}
		try {
			return jdbcTemplate.queryForList(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForList failed", de);
		}
	}
	
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> clz) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
		}
		try {
			return jdbcTemplate.query(sql, args, new CustomRowMapper<T>(clz));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForList failed", de);
		}
	}
	
	public Map<String,Object> queryForMap(String sql, Object[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
		}
		try {
			return this.jdbcTemplate.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForMap failed", de);
		}
	}
	
	public Map<String, Object> queryForMap(String sql, Object[] args, final String key, final String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
			logger.debug("queryForSingleFieldList key : {}", key);
			logger.debug("queryForSingleFieldList value : {}", value);
		}
		try {
			return this.jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Object>>() {

				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Object> map = new LinkedCaseInsensitiveMap<>();
					String k = "";
					Object v = null;
					while (rs.next()) {
						ResultSetMetaData metaData = rs.getMetaData();
						int columnCount = metaData.getColumnCount();
						for (int index = 1; index <= columnCount; index++) {
							String column = JdbcUtils.lookupColumnName(metaData, index);
							if (column.equalsIgnoreCase(key)) {
								k = (String) JdbcUtils.getResultSetValue(rs, index);
							}
							if (column.equalsIgnoreCase(value)) {
								v = JdbcUtils.getResultSetValue(rs, index);
							}
						}
						map.put(k, v);
					}
					return map;
				}
			}, args);
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForMap failed", de);
		}
	}
	/**
	* 根据条件查询单个指定对象
	* @param sql
	* @param args
	* @param clz
	* @return: T
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public <T> T queryForObject(String sql, Object[] args, Class<T> clz) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
			logger.debug("queryForSingleFieldList clz : {}", clz);
		}
		try {
			return this.jdbcTemplate.queryForObject(sql, args, new RowMapper<T>(){
				@Override
				public T mapRow(ResultSet rs, int rowNum) throws SQLException {
					return (T)rs.getObject(1);
				}
			});

		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForObject failed", de);
		}
	}

	/**
	* 根据指定sql查询单个对象
	* @param sql
	* @param clz
	* @return: T
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public <T> T queryForObject(String sql, Class<T> clz) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList clz : {}", clz);
		}
		try {
			return this.jdbcTemplate.queryForObject(sql, clz);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (DataAccessException de) {
			throw GitWebException.GIT_DB_DATA_ACCESS("queryForObject failed", de);
		}
	}
	
	public int update(String sql) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
		}
		return this.jdbcTemplate.update(sql);
	}
	
	public int update(String sql, Object[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList args : {}", CommonUtils.toString(args));
		}
		return this.jdbcTemplate.update(sql, args);
	}
	
	public int[] batchUpdate(String sql) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
		}
		return this.jdbcTemplate.batchUpdate(sql);
	}
	
	public int [] batchUpdate(String sql, List<Object[]> batchArgs) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryForSingleFieldList sql : {}", sql);
			logger.debug("queryForSingleFieldList batchArgs : {}", CommonUtils.toString(batchArgs));
		}
		return this.jdbcTemplate.batchUpdate(sql, batchArgs);
	}
}
