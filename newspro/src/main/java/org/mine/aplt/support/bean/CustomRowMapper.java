package org.mine.aplt.support.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.BeanUtil;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class CustomRowMapper<T> implements RowMapper<T> {
	private static final Logger logger = LoggerFactory.getLogger(CustomRowMapper.class);
	private Class<T> methodClass;
	
	private Map<String, PropertyDescriptor> methodFields;
	
	public CustomRowMapper(Class<T> MethodClass) {
		initValue(MethodClass);
	}
	
	void initValue(Class<T> methodClass){
		this.methodClass = methodClass;
		methodFields = new HashMap<>();
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(methodClass);
		for(PropertyDescriptor descriptor : descriptors){
			if(descriptor.getWriteMethod() != null){
				methodFields.put(descriptor.getName().toUpperCase(), descriptor);
			}
		}
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T obj = BeanUtils.instantiateClass(methodClass);
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		PropertyDescriptor descriptor = null;
		for (int i = 1; i <= columnCount; i++) {
			String columnName = CommonUtils.underLineToHump(JdbcUtils.lookupColumnName(metaData, i), true);
			descriptor = methodFields.get(columnName.toUpperCase());
			if (descriptor == null) {
				continue;
			}
			Class<?> clz = descriptor.getPropertyType();
			Object value = JdbcUtils.getResultSetValue(rs, i, clz);
			Object v = null;
			if (clz.equals(Long.class) || clz.equals(long.class)) {
				v = BeanUtil.valueToLong(value);
			} else if (clz.equals(Integer.class) || clz.equals(int.class)) {
				v = BeanUtil.valueToInteger(value);
			} else if (clz.equals(Double.class) || clz.equals(double.class)) {
				v = BeanUtil.valueToDouble(value);
			} else if (clz.equals(Short.class) || clz.equals(short.class)) {
				v = BeanUtil.valueToShort(value);
			} else if (clz.equals(Float.class) || clz.equals(float.class)) {
				v = BeanUtil.valueToFloat(value);
			} else if (clz.equals(Boolean.class) || clz.equals(boolean.class)) {
				v = BeanUtil.valueToBoolean(value);
			} else if (clz.equals(Date.class)) {
				v = BeanUtil.valueToDate(value);
			} else if (clz.equals(String.class)) {
				v = BeanUtil.valueToString(value);
			} else {
				v = value;
			}
			wrapper.setPropertyValue(columnName, v);
		}
		return obj;
	}
	
	public Object get(ResultSet rs, String column, Class<?> requiredType){
		Object obj = null;
		Class<?> clz = null;
		try {
			Method method = null;
			do {
				try {
					clz = rs.getClass();
					method = rs.getClass().getDeclaredMethod("get" + requiredType.getSimpleName(), String.class);
					break;
				} catch (NoSuchMethodException e) {
					clz = clz.getSuperclass();
					if (clz == Object.class) {
						throw e;
					}
				}
			} while(clz != Object.class);
			method.setAccessible(true);
			obj = method.invoke(rs, column);
		} catch (Exception e) {
			logger.error(MineException.getStackTrace(e));
			throw new DataRetrievalFailureException("Unable to set value for the column : " + column, e);
		}
		return obj;
	}
}
