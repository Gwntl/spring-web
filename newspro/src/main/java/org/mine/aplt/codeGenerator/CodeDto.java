package org.mine.aplt.codeGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeDto {
	//源字段名称
	private Map<String, String> orginalColumns;
	//首字母大写 用于set和get方法
	//表列名集合(驼峰,首字母大写)
	private List<String> columns;
	//列对应的类型(以JAVA类型显示)
	private Map<String, Object> types;
	//列对应的备注
	private Map<String, Object> remarks;
	//列对应默认值
	private Map<String, Object> defaults;
	//首字母小写 用于class中的属性值及变量
	//表列名集合(驼峰,首字母小写)
	private List<String> fields;
	//列对应的类型(以JAVA类型显示)
	private Map<String, Object> firstLowerTypes;
	//列对应的备注
	private Map<String, Object> firstLowerRemarks;
	//列对应默认值
	private Map<String, Object> firstLowerDefaults;
	//唯一索引
	private List<StringBuffer> uniqueIndexs;
	//普通索引
	private List<StringBuffer> normalIndexs;
	//impoert资源
	private List<String> importResource;
	//表注释
	private String tableComment;
	
	public CodeDto() {
		this.orginalColumns = new ConcurrentHashMap<>();
		this.columns = Collections.synchronizedList(new ArrayList<>());
		this.fields = Collections.synchronizedList(new ArrayList<>());
		this.types = new ConcurrentHashMap<>();
		this.remarks = new ConcurrentHashMap<>();
		this.defaults = new ConcurrentHashMap<>();
		this.firstLowerTypes = new ConcurrentHashMap<>();
		this.firstLowerRemarks = new ConcurrentHashMap<>();
		this.firstLowerDefaults = new ConcurrentHashMap<>();
		this.uniqueIndexs = Collections.synchronizedList(new ArrayList<>());
		this.normalIndexs = Collections.synchronizedList(new ArrayList<>());
		this.importResource = Collections.synchronizedList(new ArrayList<>());
		this.tableComment = "";
	}
	/**
	 * @return the orginalColumns
	 */
	public Map<String, String> getOrginalColumns() {
		return orginalColumns;
	}

	/**
	 * @param orginalColumns the orginalColumns to set
	 */
	public void setOrginalColumns(Map<String, String> orginalColumns) {
		this.orginalColumns = orginalColumns;
	}

	/**
	 * @return the columns
	 */
	public List<String> getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	/**
	 * @return the fields
	 */
	public List<String> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	/**
	 * @return the types
	 */
	public Map<String, Object> getTypes() {
		return types;
	}
	/**
	 * @param types the types to set
	 */
	public void setTypes(Map<String, Object> types) {
		this.types = types;
	}
	/**
	 * @return the remarks
	 */
	public Map<String, Object> getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(Map<String, Object> remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the defaults
	 */
	public Map<String, Object> getDefaults() {
		return defaults;
	}
	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(Map<String, Object> defaults) {
		this.defaults = defaults;
	}
	/**
	 * @return the firstLowerTypes
	 */
	public Map<String, Object> getFirstLowerTypes() {
		return firstLowerTypes;
	}
	/**
	 * @param firstLowerTypes the firstLowerTypes to set
	 */
	public void setFirstLowerTypes(Map<String, Object> firstLowerTypes) {
		this.firstLowerTypes = firstLowerTypes;
	}
	/**
	 * @return the firstLowerRemarks
	 */
	public Map<String, Object> getFirstLowerRemarks() {
		return firstLowerRemarks;
	}
	/**
	 * @param firstLowerRemarks the firstLowerRemarks to set
	 */
	public void setFirstLowerRemarks(Map<String, Object> firstLowerRemarks) {
		this.firstLowerRemarks = firstLowerRemarks;
	}
	/**
	 * @return the firstLowerDefaults
	 */
	public Map<String, Object> getFirstLowerDefaults() {
		return firstLowerDefaults;
	}
	/**
	 * @param firstLowerDefaults the firstLowerDefaults to set
	 */
	public void setFirstLowerDefaults(Map<String, Object> firstLowerDefaults) {
		this.firstLowerDefaults = firstLowerDefaults;
	}
	/**
	 * @return the uniqueIndexs
	 */
	public List<StringBuffer> getUniqueIndexs() {
		return uniqueIndexs;
	}
	/**
	 * @param uniqueIndexs the uniqueIndexs to set
	 */
	public void setUniqueIndexs(List<StringBuffer> uniqueIndexs) {
		this.uniqueIndexs = uniqueIndexs;
	}
	/**
	 * @return the normalIndexs
	 */
	public List<StringBuffer> getNormalIndexs() {
		return normalIndexs;
	}
	/**
	 * @param normalIndexs the normalIndexs to set
	 */
	public void setNormalIndexs(List<StringBuffer> normalIndexs) {
		this.normalIndexs = normalIndexs;
	}
	/**
	 * @return the importResource
	 */
	public List<String> getImportResource() {
		return importResource;
	}
	/**
	 * @param importResource the importResource to set
	 */
	public void setImportResource(List<String> importResource) {
		this.importResource = importResource;
	}
	/**
	 * @return the tableComment
	 */
	public String getTableComment() {
		return tableComment;
	}
	/**
	 * @param tableComment the tableComment to set
	 */
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
