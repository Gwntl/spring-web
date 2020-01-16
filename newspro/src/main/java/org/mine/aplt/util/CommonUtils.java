package org.mine.aplt.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公共工具类
 *
 * @filename CommonUtils.java 
 * @author wzaUsers
 * @date 2019年8月21日下午7:19:03 
 * @version v1.0
 */
public final class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	

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
		if(isEmpty(map)){
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
		return (buffer.lastIndexOf(",") != -1 ? buffer.substring(0,buffer.lastIndexOf(",")) : buffer) + "}";
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
	
	public static Date stringToDate(String timeStr, String format) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(timeStr);
	}
	
	public static String dateToString(Date date, String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**
	 * 将需要加入的初始值放入对应的属性中
	 * @param map 初始值集
	 * @param initValue 该值形式为<init=12,13,15;init1=1q;init2=orij>
	 * @return
	 */
	public static Map<String, Object> initMapValue(Map<String, Object> map, String initValue){
		if(map != null && isNotEmpty(initValue)){
			String[] values = initValue.split(";");
			for(int i = 0, length = values.length; i < length; i++){
				String[] key_value = values[i].split("=");
				map.put(key_value[0], key_value[1]);
			}
		} else {
			logger.error("设置值为空!!!!");
		}
		return map;
	}
	
	/**
	 * 下划线转驼峰
	 * @param str
	 * @param fisrtLetterSkip 第一个单词跳过
	 * @return
	 */
	public static String underLineToHump(String str, boolean fisrtLetterSkip){
		String[] content = str.toLowerCase().split("_");
		int length = content.length;
		StringBuffer buffer = new StringBuffer(length);
		for(int i = 0; i < length; i ++){
			if(fisrtLetterSkip && i == 0){
				buffer.append(content[i]);
			} else {
				buffer.append(capitalizedFirstLetter(content[i]));
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 驼峰转为下划线格式字符串
	 * */
	public static String humpToUnderLine(String str){
		str = firstLetterLowerCase(str);
		char[] chars = str.toCharArray();
		StringBuffer buffer = new StringBuffer(str);
		for(int i = 0; i < chars.length; i ++){
			if(Character.isUpperCase(chars[i])){
				buffer = buffer.insert(i, '_');
			}
		}
		
		return buffer.toString().toLowerCase();
	}
	
	/**
	 * 首字母大写(前移为大写)
	 * @param s
	 * @return
	 */
	public static String capitalizedFirstLetter(String str){
		char[] chars = str.toLowerCase().toCharArray();
		char firstLetter = chars[0];
		if(firstLetter >= 'a' && firstLetter <= 'z'){
			chars[0] = (char) (firstLetter - 32);
		}
		return new String(chars);
	}
	
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	public static String firstLetterLowerCase(String str){
		char[] chars = str.toCharArray();
		char firstLetter = chars[0];
		if(firstLetter >= 'A' && firstLetter <= 'Z'){
			chars[0] += 32;
		}
		return new String(chars);
	}
	
	public static Long addZeroWithMiddle(Long l, Long count){
		return new Long(l + "00" + count);
	}
	
	/**
	 * 判断当前字符串是否全为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		if(str == null || str.length() <= 0){
			return false;
		}
		
		int length = str.toCharArray().length - 1;
		while(length > 0){
			if(!Character.isDigit(str.charAt(length))){
				return false;
			}
			length--;
		}
		
		return true;
	}
	
	/**
	 * 四舍五入
	 * @param o
	 * @param scale
	 * @return
	 */
	public static String round(Object o, int scale){
		if(o == null){
			return "";
		}
		return new BigDecimal(o + "").setScale(scale, RoundingMode.HALF_UP) + "";
	}
	
	/**
	 * 将字符串根据指定分隔符分割成数组
	 * @param str 源字符串
	 * @param split 分割符
	 * @param isLast 当分割符后一位为空时,是否读取
	 * @return
	 */
	public static List<String> splitStrToList(String str, String split, boolean isLast){
		if(isEmpty(str)){
			return null;
		}
		List<String> list = new ArrayList<>();
		int index = 0;
		while((index = str.indexOf(split)) != -1){
			list.add(str.substring(0, index));
			str = str.substring(index + 1);
		}
		if(isLast || isNotEmpty(str)){
			list.add(str);
		}
		return list;
	}
	
	/**
	 * list根据给定分割符转为String
	 * @param list
	 * @param split
	 * @return
	 */
	public static String listToStr(List<String> list, String split){
		if(isEmpty(list)){
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for(int i = 0, size = list.size(); i < size; i++){
			String con = list.get(i);
			if(isNotEmpty(con)){
				buffer.append(con).append(split);
			}
		}
		
		return buffer.substring(0, buffer.length() -1);
	}
	
	/**
	 * 获取当前数据邻近的2幂次数(向上取值)
	 * @param capacity
	 * @return
	 */
	public static int initialSize(int capacity){
		//判断是否为2的倍数
		if((capacity & (capacity-1)) == 0){
			return capacity;
		}
		capacity |= capacity >> 1;
		capacity |= capacity >> 2;
		capacity |= capacity >> 4;
		capacity |= capacity >> 8;
		capacity |= capacity >> 16;
		return capacity + 1;
	}
	
	public static void main(String[] args){
//		Map<String, Object> map = new HashMap<>();
//		map.put("1", 100);
//		map.put("2", 2.093);
//		map.put("3", "4947rjr");
//		map.put("4", new BigDecimal(100));
//		
//		System.out.println(toString(map));
//		
//		List<String> list = new ArrayList<>();
//		list.add("qweqwe");
//		list.add("qwe12e3qwe");
//		list.add("qweqwqwe12e");
//		list.add("");
//		
//		System.out.println(listtoStr(list,","));
//		
//		String[] a = {"94jueek","454","we1231"};
//		System.out.println(toString(a));
//		System.out.println(underLineToHump("ass_bsd_vdd", false));
//		System.out.println(isNumber("1230.1"));
//		System.out.println(toString(splitStrToList("a|b|c|d|", "|", true)));
		System.out.println(humpToUnderLine("RaBc"));
	}
	
}
