package com.quartz.job.abs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

public class AbstractDemo implements InitializingBean{

	protected String className;
	protected String classPath;
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the classPath
	 */
	public String getClassPath() {
		return classPath;
	}

	/**
	 * @param classPath the classPath to set
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	private static Map<String, String> cacheMap = null;
	static{
		cacheMap = new HashMap<String, String>();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		synchronized(cacheMap){
			cacheMap.put(this.className, this.classPath);
		}
	}
	
	public void init(){
		System.out.println(">>>>>>>>>>cacheMap>>>>>>>>>>" + cacheMap.size());
	}
}
