package org.mine.aplt.support.bean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

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
	
	public CurrThreadExecutionEnv() {
		status = null;
		savepointMap = new HashMap<>();
		statusList = new ArrayList<>();
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
	protected Connection getConnection(){
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
	public void createSavepoint(String savepointName){
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
	public void rollbackSavepoint(String savepointName){
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
	public void releaseSavepoint(String savepointName){
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
	
	class InnerSavepoint{
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
	
	public List<Map<String, Object>> queryForList(String sql, Object[] args){
		return jdbcTemplate.queryForList(sql, args);
	}
	
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> obj){
		return (List<T>) jdbcTemplate.query(sql, args, new CustomRowMapper<T>(obj));
	}
}
