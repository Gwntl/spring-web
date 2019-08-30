package org.mine.aplt.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共工具类
 *
 * @filename CommonUtils.java 
 * @author wzaUsers
 * @date 2019年8月21日下午7:19:03 
 * @version v1.0
 */
public final class CommonUtils {

	public static boolean isEmpty(Object obj){
		return obj == null;
	}
	
	public static boolean isNotEmpty(Object obj){
		return obj != null;
	}
	
	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0;
	}
	
	public static boolean isNotEmpty(String str){
		return str != null && str.trim().length() > 0;
	}
	
	public static boolean isEmpty(List<?> list){
		return list == null || list.size() == 0;
	}
	
	public static boolean isNotEmpty(List<?> list){
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Map<?, ?> map){
		return map == null || map.size() == 0;
	}
	
	public static boolean isNotEmpty(Map<?, ?> map){
		return !isEmpty(map);
	}
	
	public static final Long toLong(Object obj){
		if(obj == null){
			return null;
		}
		if(obj instanceof Long){
			return (Long)obj;
		}else{
			try{
				return new Long(obj.toString());
			}catch(Exception e){
				return null;
			}
		}
	}
	
	public static final int toInt(String str){
		return toInt(str, 0);
	}
	
	public static final int toInt(String str, int defaultValue){
		if(str == null){
			return defaultValue;
		}
		try{
			return Integer.parseInt(str.trim());
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static String toString(Map<?, ?> map){
		if(map == null){
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
//		buffer.append(map.getClass().getSimpleName() + "{");
//		Iterator<?> iterator = map.keySet().iterator();
//		while(iterator.hasNext()){
//			Object key = iterator.next();
//			Object value = map.get(key);
//			buffer.append(key).append("=").append(value);
//			if(iterator.hasNext()){
//				buffer.append(",");
//			}
//		}
//		buffer.append("}");
		
		buffer.append(map.getClass().getSimpleName() + "{");
		for(Map.Entry<?, ?> entry : map.entrySet()){
			buffer.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
		}
		
		return buffer.substring(0, buffer.lastIndexOf(",")) + "}";
	}
	
	public static String toString(List<?> list){
		if(list == null)
			return null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(list.getClass().getSimpleName() + "{");
//		Iterator<?> iterator = list.iterator();
//		while(iterator.hasNext()){
//			buffer.append(iterator.next());
//			if(iterator.hasNext()){
//				buffer.append(",");
//			}
//		}
		for(int i = 0; i < list.size(); i++){
			buffer.append(list.get(i)).append(",");
		}
		return buffer.substring(0,buffer.lastIndexOf(",")) + "}";
	}
	
	public static String toString(Object[] obj){
		if(obj == null)
			return null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(obj.getClass().getSimpleName() + "{");
		for(int i = 0; i < obj.length; i++){
			buffer.append(obj[i]).append(",");
		}
		return buffer.substring(0,buffer.lastIndexOf(",")) + "}";
	}
	
	/**
	 * 数组转list(list转数组使用list.toArray())
	 * @param obj
	 * @return
	 */
	public static final List<Object> arrayToList(Object[] obj){
		List<Object> list = new ArrayList<>();
		for(int i = 0; i < obj.length; i ++){
			list.add(obj[i]);
		}
//		Arrays.asList(obj);
		return list;
	}
	
	
	public static void main(String[] args){
		Map<String, Object> map = new HashMap<>();
		map.put("1", 100);
		map.put("2", 2.093);
		map.put("3", "4947rjr");
		map.put("4", new BigDecimal(100));
		
		System.out.println(toString(map));
		
		List<String> list = new ArrayList<>();
		list.add("qweqwe");
		list.add("qwe12e3qwe");
		list.add("qweqwqwe12e");
		list.add("qweeqe121qwe");
		
		System.out.println(toString(list));
		
		String[] a = {"94jueek","454","we1231"};
		System.out.println(toString(a));
	}
	
}
