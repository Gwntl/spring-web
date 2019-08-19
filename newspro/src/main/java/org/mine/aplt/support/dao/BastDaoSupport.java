package org.mine.aplt.support.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BastDaoSupport implements ApplicationContextAware{

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
		BastDaoSupport.applicationContext = applicationContext;
		
//		try {
//			BastDaoSupport.sqlSession = factoryBean.getObject().openSession();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static Object getBean(Class<?> clazz){
		return BastDaoSupport.applicationContext.getBean(clazz);
	}
	
	
	/**
	 * SqlSessionTemplate为线程安全的sqlSession的装饰类(用于增强SqlSession)
	 * @return
	 */
	public static SqlSessionTemplate getSqlSessionTemplate(){
		return (SqlSessionTemplate) getBean(SqlSessionTemplate.class);
	}
}
