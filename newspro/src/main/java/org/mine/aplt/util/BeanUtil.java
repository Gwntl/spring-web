package org.mine.aplt.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.controller.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

public class BeanUtil extends BeanUtils{

	public static BaseServiceTasketExcutor getObj(String className){
		BaseServiceTasketExcutor obj = null;
		try{
			obj = (BaseServiceTasketExcutor) Class.forName(className).newInstance();
		} catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
			throw new FatalBeanException("Could not newinstance clazz : " + className);
		}
		return obj;
	}
	
	/**
	 * 为对象初始化值
	 * @param clazz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz){
		if(clazz == null){
			return null;
		}
		
		try {
			T obj = clazz.newInstance();
			//此处使用Spring中的BeanUtils工具类进行赋值
			initValue(obj);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new FatalBeanException("Could not newinstance clazz" + clazz);
		}
	}
	
	/**
	 * 根据Java自带的反射方法进行赋值
	 * @param obj
	 * @param flag
	 */
	public static void initValue(Object obj, boolean flag){
		Class<?> clazz = obj.getClass();
		//getDeclaredFields是获取所有已声明的字段,getFields()是获取所有已声明的公共方法.
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				if(!Modifier.isPublic(field.getModifiers())){
					field.setAccessible(true);
				}
				Object value = field.get(obj);
				if (value == null) {
					Class<?> type = field.getType();
					if(type.equals(String.class)){
						value = "";
					}
					field.set(obj, value);
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据Bean中字段不同的类型进行赋值(此处使用Spring的BeanUtils中的方法.)
	 * PropertyDescriptor是Spring用来存储Bean内容的构建
	 * @param obj
	 */
	public static void initValue(Object obj){
		Class<?> clazz = obj.getClass();
		PropertyDescriptor[] descriptors = getPropertyDescriptors(clazz);
		for(PropertyDescriptor descriptor : descriptors){
			if(descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null){
				try{
					Method readMethod = descriptor.getReadMethod();
					if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())){
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(obj);
					if(value == null){
						Class<?> type = descriptor.getPropertyType();
						if(type.getName().equals(String.class.getName())){
							value = "";
						} else if(type.getName().equals(Long.class.getName()) || type.getName().equals(long.class.getName())){
							value = 0L;
						} else if(type.getName().equals(Short.class.getName()) || type.getName().equals(short.class.getName())){
							value = 0;
						} else if(type.getName().equals(Float.class.getName()) || type.getName().equals(float.class.getName())){
							value = 0.00f;
						} else if(type.getName().equals(Double.class.getName()) || type.getName().equals(double.class.getName())){
							value = 0.00d;
						} else if(type.getName().equals(Character.class.getName()) || type.getName().equals(char.class.getName())){
							value = 0;
						} else if(type.getName().equals(Byte.class.getName()) || type.getName().equals(byte.class.getName())){
							value = 0;
						} else if(type.getName().equals(Integer.class.getName()) || type.getName().equals(int.class.getName())){
							value = 0;
						} else if(type.getName().equals(Boolean.class.getName()) || type.getName().equals(boolean.class.getName())){
							value = false;
						} else if(type.getName().equals(BigDecimal.class.getName())){
							value = BigDecimal.ZERO;
						} else if(type.getName().equals(Set.class.getName())){
							value = new HashSet<>();
						} else if(type.getName().equals(List.class.getName())){
							value = new ArrayList<>();
						} else if(type.getName().equals(Map.class.getName())){
							value = new HashMap<>();
						} else{
							value = null;
						}
						
						Method writeMethod = descriptor.getWriteMethod();
						if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())){
							writeMethod.setAccessible(true);
						}
						
						writeMethod.invoke(obj, value);
					}
				}catch(Exception a){
					throw new FatalBeanException(String.format("set value for that class[%s] failed", clazz));
				}
			}
		}
	}
	
	/**
	 * 将对象转化为Map
	 * @param obj
	 * @param isSort 是否有序
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj, boolean isSort){
		try {
			Map<String, Object> map = new HashMap<>();
			if(isSort){
				map = new LinkedHashMap<>();
			}
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field : fields){
				if(!Modifier.isPublic(field.getModifiers())){
					field.setAccessible(true);
				}
				String name = field.getName();
				Object value = field.get(obj);
				map.put(name, value);
			}
			return map;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new FatalBeanException("parse bean to map failed");
		}
		
	}
	
	/**
	 * 将map中的值放入bean对象中
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T mapValueIntoBean(Map<String, Object> map, Class<T> clazz){
		try{
			T t = clazz.newInstance();
			if(map != null){
				Class<?> cls = clazz;
				while(!cls.equals(Object.class)){
					Field[] fields = cls.getDeclaredFields();
					for(Field field : fields){
						String name = field.getName();
						Object value = map.get(name);
						if(value == null){
							continue;
						}
						
						Class<?> type = field.getType();
						if(type.equals(String.class)){
							value = value + "";
						} else if(type.equals(Long.class) || type.equals(long.class.getName())){
							value = CommonUtils.toLong(value);
						} else if(type.equals(Integer.class) || type.equals(int.class.getName())){
							value = CommonUtils.toInt(value + "");
						}
						if(!Modifier.isPublic(field.getModifiers())){
							field.setAccessible(true);
						}
						field.set(t, value);
					}
					cls = cls.getSuperclass();
				}
			}
			return t;
		}catch(NullPointerException | InstantiationException | IllegalAccessException e){
			throw new FatalBeanException("");
		}
		
	}
	
	public static Boolean valueToBoolean(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Boolean.class) || obj.getClass().equals(boolean.class)) {
			return (Boolean)obj;
		} else {
			return new Boolean(valueToString(obj));
		}
	}
	
	public static Long valueToLong(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Long.class) || obj.getClass().equals(long.class)) {
			return (Long)obj;
		} else {
			String s = valueToString(obj);
			if (s == null || s.length() <= 0) {
				s = "0";
			}
			return new Long(s);
		}
	}
	
	public static Short valueToShort(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Short.class) || obj.getClass().equals(short.class)) {
			return (Short)obj;
		} else {
			String s = valueToString(obj);
			if (s == null || s.length() <= 0) {
				s = "0.0";
			}
			return new Short(s);
		}
	}
	
	public static Integer valueToInteger(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Integer.class) || obj.getClass().equals(int.class)) {
			return (Integer)obj;
		} else {
			String s = valueToString(obj);
			if (s == null || s.length() <= 0) {
				s = "0";
			}
			return new Integer(s);
		}
	}
	
	public static Double valueToDouble(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Double.class) || obj.getClass().equals(double.class)) {
			return (Double)obj;
		} else {
			String s = valueToString(obj);
			if (s == null || s.length() <= 0) {
				s = "0.00";
			}
			return new Double(s);
		}
	}
	
	public static Float valueToFloat(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Float.class) || obj.getClass().equals(float.class)) {
			return (Float)obj;
		} else {
			String s = valueToString(obj);
			if (s == null || s.length() <= 0) {
				s = "0";
			}
			return new Float(s);
		}
	}
	
	public static BigDecimal valueToBigDecimal(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(BigDecimal.class)) {
			return (BigDecimal)obj;
		} else {
			String s = valueToString(obj);
			if (s == null || s.length() <= 0) {
				s = "0.00";
			}
			return new BigDecimal(s);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Date valueToDate(Object obj){
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(Date.class)) {
			return (Date)obj;
		} else {
			return new Date(valueToString(obj));
		}
	}
	
	public static String valueToString(Object obj){
		if (obj == null) {
			return null;
		}
		Class<?> clz = obj.getClass();
		if (clz.equals(Long.class) || clz.equals(long.class)) {
			return ((Long)obj) + "";
		} else if (clz.equals(Integer.class) || clz.equals(int.class)) {
			return ((Integer)obj) + "";
		} else if (clz.equals(Double.class) || clz.equals(double.class)) {
			return ((Double)obj) + "";
		} else if (clz.equals(Short.class) || clz.equals(short.class)) {
			return ((Short)obj) + "";
		} else if (clz.equals(Float.class) || clz.equals(float.class)) {
			return ((Float)obj) + "";
		} else if (clz.equals(Boolean.class) || clz.equals(boolean.class)) {
			return ((Boolean)obj) + "";
		} else if (clz.equals(BigDecimal.class)) {
			return ((BigDecimal)obj).toPlainString();
		} else if (clz.equals(BigInteger.class)) {
			return ((BigInteger)obj).toString();
		} else if (clz.equals(Date.class)) {
			return ((Date)obj).toString();
		} else {
			return obj.toString();
		}
	}
	
	
	public static void main(String[] args) {
		UserDto dto = BeanUtil.newInstance(UserDto.class);
		System.out.println(dto.toString());
		
		
		Map<String, Object> map = beanToMap(dto, false);
		System.out.println(CommonUtils.toString(map));
		
		Map<String, Object> mapSort = beanToMap(dto, true);
		System.out.println(CommonUtils.toString(mapSort));
		
		Map<String, Object> mapToBean = new HashMap<>();
		mapToBean.put("username", "213ewq");
		mapToBean.put("password", "wqedf4gg43t231");
		mapToBean.put("id", "1200090");
		
		System.out.println(mapValueIntoBean(mapToBean, UserDto.class).toString());
	}
}
