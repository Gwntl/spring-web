package org.mine.aplt.util;

import com.alibaba.druid.sql.visitor.functions.Char;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

	public static boolean isEmpty(Object[] objects){
		return objects == null || objects.length <= 0;
	}

	public static boolean isNotEmpty(Object[] objects){
		return !isEmpty(objects);
	}

	public static boolean equals(String source, String target){
		if (source == null && target == null) {
			return true;
		}
		if (source == null) {
			source = "";
		}
		if (target == null) {
			target = "";
		}
		return source.compareTo(target) == 0;
	}

	public static boolean equalIgnoreCase(String source, String target){
		if (source == null && target == null) {
			return true;
		}
		if (source == null) {
			source = "";
		}
		if (target == null) {
			target = "";
		}
		return source.toLowerCase().compareTo(target.toLowerCase()) == 0;
	}

	public static boolean equals(Long src, Long tar){
		if(src == null && tar == null){
			return true;
		}
		if(src == null){
			src = 0L;
		}
		if(tar == null){
			tar = 0L;
		}
		return src.compareTo(tar) == 0;
	}

	public static boolean equals(Integer src, Integer tar){
		if(src == null && tar == null){
			return true;
		}
		if(src == null){
			src = 0;
		}
		if(tar == null){
			tar = 0;
		}
		return src.compareTo(tar) == 0;
	}

	public static boolean equals(Double src, Double tar){
		if(src == null && tar == null){
			return true;
		}
		if(src == null){
			src = 0.00;
		}
		if(tar == null){
			tar = 0.00;
		}
		return src.compareTo(tar) == 0;
	}

	public static boolean notEquals(String source, String target){
		return !equals(source, target);
	}

	/**
	* 字符串比较, 忽略大小写
	* @param source
	* @param target
	* @return: boolean
	* @Author: wntl
	* @Date: 2020/8/12
	*/
	public static boolean notEqualIgnoreCase(String source, String target){
		return !equalIgnoreCase(source, target);
	}

	public static boolean notEquals(Long source, Long target){
		return !equals(source, target);
	}

	public static boolean notEquals(Integer source, Integer target){
		return !equals(source, target);
	}

	public static boolean notEquals(Double source, Double target){
		return !equals(source, target);
	}


	
	public static Long toLong(Object obj){
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
	
	public static int toInt(String str){
		return toInt(str, 0);
	}
	
	public static int toInt(String str, int defaultValue){
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
		
		StringBuilder buffer = new StringBuilder();
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

		StringBuilder buffer = new StringBuilder();
		buffer.append(list.getClass().getSimpleName() + "{");
//		Iterator<?> iterator = list.iterator();
//		while(iterator.hasNext()){
//			buffer.append(iterator.next());
//			if(iterator.hasNext()){
//				buffer.append(",");
//			}
//		}
		Object obj = null;
		for(int i = 0; i < list.size(); i++){
			if((obj = list.get(i)) instanceof Object[]){
				buffer.append(toString((Object[])obj)).append(",");
			} else if (obj instanceof Map) {
				buffer.append(toString((Map)obj)).append(",");
			} else if (obj instanceof List) {
				buffer.append(toString((List)obj)).append(",");
			} else {
				buffer.append(obj).append(",");
			}
		}
		return (buffer.lastIndexOf(",") != -1 ? buffer.substring(0,buffer.lastIndexOf(",")) : buffer) + "}";
	}

	public static String toString(Set<?> c) {
		if (c == null) {
			return null;
		}
		Iterator iterator = c.iterator();
		StringBuilder buffer = new StringBuilder();
		while (iterator.hasNext()) {
			buffer.append(iterator.next());
			if (iterator.hasNext()) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
	public static String toString(Object[] obj){
		if(obj == null)
			return null;

		StringBuilder buffer = new StringBuilder();
		buffer.append(obj.getClass().getSimpleName() + "{");
		for(int i = 0; i < obj.length; i++){
			buffer.append(obj[i]).append(",");
		}
		return buffer.substring(0,buffer.lastIndexOf(",")) + "}";
	}

	public static String toStringNoTitle(Object[] obj) {
		if(obj == null)
			return null;

		StringBuilder buffer = new StringBuilder();
		for(int i = 0; i < obj.length; i++){
			buffer.append(toStringByType(obj[i])).append(",");
		}
		return buffer.substring(0,buffer.lastIndexOf(","));
	}

	public static String toStringByType(Object obj) {
		String str = "";
		if (obj instanceof Map) {
			str = toString((Map)obj);
		} else if (obj instanceof List) {
			str = toString((List)obj);
		} else if (obj instanceof String[]) {
			str = toString((String[])obj);
		} else if (obj instanceof Long[] || obj instanceof long[] ) {
			str = toString((Long[])obj);
		} else if (obj instanceof Integer[] || obj instanceof int[] ) {
			str = toString((Integer[])obj);
		} else if (obj instanceof Boolean[] || obj instanceof boolean[]) {
			str = toString((Boolean[])obj);
		} else if (obj instanceof Double[] || obj instanceof double[]) {
			str = toString((Double[])obj);
		} else if (obj instanceof Short[] || obj instanceof short[]) {
			str = toString((Short[])obj);
		} else if (obj instanceof Character[] || obj instanceof char[]) {
			str = toString((Character[])obj);
		} else if (obj instanceof Byte[] || obj instanceof byte[]) {
			str = toString((Byte[])obj);
		} else if (obj instanceof Float[] || obj instanceof float[]) {
			str = toString((Float[])obj);
		} else {
			str = obj.toString();
		}
		return str;
	}
	
	/**
	 * 数组转list(list转数组使用list.toArray())
	 * @param obj
	 * @return
	 */
	public static List<Object> arrayToList(Object[] obj){
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
	* 获取当前日期
	* @param date
	* @return: java.lang.String
	* @Author: wntl
	* @Date: 2020/9/22
	*/
	public static String currentDate(Date date){
		return dateToString(date, "yyyyMMdd");
	}
	/**
	* 获取当前时间
	* @param date
	* @return: java.lang.String
	* @Author: wntl
	* @Date: 2020/9/22
	*/
	public static String currentTime(Date date){
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
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
				if(values[i].length() > 0){
					String[] key_value = values[i].split("=");
					map.put(key_value[0], key_value[1]);
				}
			}
		}
		return map;
	}

	public static void mergeMaps(Map<String, Object> sourceMap, Map<String, Object>... targetMaps){
		if (sourceMap != null && isNotEmpty(targetMaps)) {
			for (Map<String, Object> targetMap : targetMaps) {
				if (isNotEmpty(targetMap)) {
					for (Map.Entry<String, Object> entry : targetMap.entrySet()) {
						sourceMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
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
	 * 首字母大写(前移为大写),其他字母转为小写.
	 * @param str
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
	* 将字符首字母大写
	* @param str
	* @return: java.lang.String
	* @Author: wntl
	* @Date: 2020/8/13
	*/
	public static String firstLetterUpperCase(String str) {
		char[] chars = str.toCharArray();
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
	* 将字符串按照指定分隔符转化为链表.
	* @param str
	* @param split
	* @param isLast
	* @return: java.util.LinkedList<java.lang.String>
	* @Author: wntl
	* @Date: 2020/8/26
	*/
	public static LinkedList<String> splitStrToLink(String str, String split, boolean isLast){
		if(isEmpty(str)){
			return null;
		}
		LinkedList<String> list = new LinkedList<>();
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
		StringBuilder buffer = new StringBuilder();
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

	/**
	 * 按指定规则填充字符串
	 * @param sourceStr 源字符串
	 * @param digits 位数
	 * @param rule 填充字符
	 * @param prefix 前缀. 指定则添加.
	 * @return: java.lang.String
	 * @Author: wntl
	 * @Date: 2020/8/11
	 */
	public static String fillByRule(String sourceStr, int digits, char rule, String prefix){
		int length = sourceStr.length();
		StringBuffer buffer = new StringBuffer();
		if (isNotEmpty(prefix)) {
			buffer.append(prefix);
		}
		for(int i = length; i < digits; i++){
			buffer.append(rule);
		}
		buffer.append(sourceStr);
		return buffer.toString();
	}

	/**
	* Map间的复制, 当存在同样的key时, value将存在List<Object>集合中.
	* @param sourceMap
	* @param targetMap
	* @return: void
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	public static void putAll(Map<String, Object> sourceMap, Map<String, Object> targetMap){
		if(sourceMap == null || isEmpty(targetMap)) {
			return;
		}
		String key = "";
		Object value = "";
		for(Map.Entry<String, Object> entry : targetMap.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
			if (sourceMap.containsKey(key)) {
				if (sourceMap.get(key) instanceof List) {
					((List) sourceMap.get(key)).add(value);
				} else {
					List<Object> list = new ArrayList<>();
					list.add(sourceMap.get(key));
					list.add(value);
					sourceMap.put(key, list);
				}
			} else {
				sourceMap.put(key, entry.getValue());
			}
		}
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","").trim();
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
//		System.out.println(humpToUnderLine("RaBc"));
//		System.out.println(getUUID());
//		try {
//			InetAddress address = InetAddress.getLocalHost();
//			System.out.println(address.getHostAddress() + ", " + address.getHostName());
//		} catch (UnknownHostException e) {
//
//		}
//		Properties properties = System.getProperties();
//		Enumeration<?> enumeration =  properties.propertyNames();
//		while (enumeration.hasMoreElements()) {
//			Object obj = (Object)enumeration.nextElement();
//			System.out.println(obj);
//		}
//		System.out.println(System.getProperty("os.name"));
//		System.out.println(System.getProperty("user.home"));
//		System.out.println(System.getProperty("java.runtime.version"));
		Map<String, Object> map = new HashMap<>();
		map.put("1", 100);
		map.put("2", 2.093);
		map.put("3", "4947rjr");
		map.put("4", new BigDecimal(100));
		List<String> list = new ArrayList<>();
		list.add("qweqwe");
		list.add("qwe12e3qwe");
		list.add("qweqwqwe12e");
		list.add("");
		String[] str = new String[]{"a","b"};
		Long[] l = new Long[]{1L,2L};
		Object[] obj = new Object[]{map, list, str, l};
		System.out.println(toStringNoTitle(obj));
	}
	
}
