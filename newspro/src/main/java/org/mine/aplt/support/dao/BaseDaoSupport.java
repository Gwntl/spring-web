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
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * MyBatisDao操作基类.</br>
 * MyBatis操作Dao存在两种方式:</br>
 * 	第一种: 使用XML配置的形式.将SQL语句存放再XML中,使用namespace来标志唯一的XML文件.</br>
 * 当使用Spring配置时,将与DAO对应的Mapper.xml文件注册进{@link SqlSessionFactoryBean}里面,使Spring容器在</br>
 * 加载时将这些MyBatis的配置文件读进缓存中,供后续调用使用.(由于{@link SqlSession}是线程不安全的,因此Spring</br>
 * 使用{@link SqlSessionTemplate}来封装SqlSession,使用ThreadLocal类型来存放SqlSession实例,保证每个线程获</br>
 * 取的SqlSession是线程独立的).此时调用Sql使用namespace.id的形式来调用,参数和返回值用paramaterMap和</br>
 * resultMap、resultType来确定,MyBatis内部会对参数类型进行判断放至map中("collection",obj)/("list",obj)....</br>
 * 
 * 	第二种: 使用注解的方式(Dao中需要加上Spring组件的注解),在Spring容器中配置{@link MapperScannerConfigurer},</br>
 * 此类实现了Spring的{@link BeanDefinitionRegistryPostProcessor}方法,当容器加载时会自动调用该方法,然后将</br>
 * basePackage中的注解类加载进Spring容器中,并且将{@link MapperFactoryBean}加载进Sprint容器中,改类的父类实现</br>
 * {@link InitializingBean} Spring将自动调用其中的afterProperties()方法,其中存在checkDaoConfig()方法,此方法</br>
 * 将所有的MyBatis注解方法加载进内存,等待调用.</br>
 *
 * 
 * MyBatis调用DAO方法时采用动态代理的模式</br>
 *  XMl方式: getSqlSessionTemplate().select(), {@link SqlSessionTemplate.SqlSessionInterceptor}</br>
 *  注解方式: sqlSession.getMapper(Class).select(), @link MapperProxy</br>
 * 
 * @filename BaseDaoSupport.java 
 * @author wzaUsers
 * @date 2019年12月9日下午2:54:46 
 * @version v1.0
 */
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
				} else if(operator.equalsIgnoreCase("delete")){
					session.delete(statement, t);
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
