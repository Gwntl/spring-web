package org.mine.aplt.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineBizException extends MineException{
	private static final long serialVersionUID = 11745362295480131L;
	
	private static final Logger logger = LoggerFactory.getLogger(MineBizException.class);
	
	public MineBizException(String error_code, String error_msg) {
		super(error_code, error_msg);
	}
	
	public static MineBizException newException(Properties properties, String error_code, Object[] args){
		return new MineBizException(error_code, formatErrorMsg(properties, error_code, args));
	}
	
	public static Properties loadProperties(String path){
		Properties properties = new Properties();
		try {
			InputStream in = MineBizException.class.getClassLoader().getResourceAsStream(path);
			if(in != null){
				properties.load(in);
			}else{
				logger.error("待加载资源文件" +path + "不存在!!!请联系技术人员检查.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("加载资源文件" +path + "失败!!!请联系技术人员检查.");
		}
		return properties;
	}
	
	public static String formatErrorMsg(Properties properties, String error_code, Object[] args){
		
		String resource = properties.getProperty(error_code,"(错误信息为定义)");
		
		return String.format(resource, args);
	}
	
	public static void main(String args[]){
		Properties properties = loadProperties("config/properties/error/error.properties");
		for(Entry<Object, Object> s : properties.entrySet()){
			System.out.println(s.getKey() + " = " + s.getValue());
		}
		String resource = properties.getProperty("GIT1001","(错误信息为定义)");
		System.out.println(String.format(resource, new Object[]{"ssss"}));
	}
}
