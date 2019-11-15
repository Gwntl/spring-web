package org.mine.aplt.support.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mine.aplt.exception.GitWebException;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BaseDaoSupport implements ApplicationContextAware{

	@Autowired
	SqlSessionFactoryBean factoryBean;
	
	private static ApplicationContext applicationContext;
	
	/**
	 * sqlsession(线程不安全)
	 */
//	private static SqlSession sqlSession;
	
//	public static SqlSession getSqlSession() {
//		return BastDaoSupport.sqlSession;
//	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		BaseDaoSupport.applicationContext = applicationContext;
		
//		try {
//			BastDaoSupport.sqlSession = factoryBean.getObject().openSession();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static Object getBean(Class<?> clazz){
		return BaseDaoSupport.applicationContext.getBean(clazz);
	}
	
	
	/**
	 * SqlSessionTemplate为线程安全的sqlSession的装饰类(用于增强SqlSession)
	 * @return
	 */
	public static SqlSessionTemplate getSqlSessionTemplate(){
		return (SqlSessionTemplate) getBean(SqlSessionTemplate.class);
	}
	
	/**
	 * 使用分批处理的方式插入数据库
	 * @param list 插入数据库List对象
	 * @param parseName mapper.xml中配置的collection名称
	 * @param commintSize 每次提交数
	 * @param operator 批量操作回调对象
	 * @return
	 */
	public <T> T BatchInsertByXML(List<T> list, String parseName, int commintSize, BatchOperator operator){
		Map<String, Object> map = new HashMap<>();
		int size = 1;
		List<T> CommitLists = new ArrayList<T>(commintSize);
		for(int i = 0, finalSize = list.size(); i < finalSize; i++){
			CommitLists.add(list.get(i));
			if(size == commintSize){
				map.put(parseName, CommitLists);
				operator.call(map);
				CommitLists.clear();
				size = 0;
			}
			size++;
		}
		
		if(size > 0){
			map.put(parseName, CommitLists);
			operator.call(map);
			CommitLists = null;
		}
		return null;
	}
	
	/**
	 * 此方法session.getMapper(clazz)时 报传入传入接口在mybatis中为定义, 待解决
	 * @param list
	 * @param clazz
	 * @param commitSize
	 * @return
	 */
	public <T> T batchExcutor(String statement, List<T> list, String operator, int commitSize){
		SqlSession session = null;
		try{
			session = getSqlSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH);
			for(int i = 0, size = list.size(); i < size; i ++){
				T t = list.get(i);
				if(operator.equalsIgnoreCase("insert")){
					session.insert(statement, t);
				} else if(operator.equalsIgnoreCase("update")){
					session.update(statement, t);
				}
//				//没commitSize次提交一次
//				if(i % commitSize == 0 || i == commitSize - 1){
//					
//				}
			}
			session.commit();
			//清理缓存
//			session.clearCache();
			
		} catch(Exception e){
			e.printStackTrace();
			if(session != null){
				session.rollback();
			}
			throw GitWebException.GIT1001("批量处理失败");
		} finally{
			if(session != null){
				session.close();
			}
		}
		return null;
	}
}
