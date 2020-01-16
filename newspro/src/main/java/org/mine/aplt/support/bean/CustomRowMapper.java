package org.mine.aplt.support.bean;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

public class CustomRowMapper<T> implements RowMapper<Map<String, Object>> {
	private Class<T> MethodClass;
	
	private Map<String, PropertyDescriptor> methodFields;
	
	public CustomRowMapper(Class<T> MethodClass) {
		initValue(MethodClass);
	}
	
	void initValue(Class<T> MethodClass){
		this.MethodClass = MethodClass;
		methodFields = new HashMap<>();
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(MethodClass);
		for(PropertyDescriptor descriptor : descriptors){
			if(descriptor.getWriteMethod() != null){
				methodFields.put(descriptor.getName().toUpperCase(), descriptor);
			}
		}
	}
	
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
