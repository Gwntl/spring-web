package com.jdbc.codeUtilTest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGeneration {
	private static final Logger logger = LoggerFactory.getLogger(CodeGeneration.class);
	public static Map<String, String> interfaceMethodMap = new HashMap<String, String>();
	public static final String LINE_SUFFIX = ";\n";
	public static final String NEWLINE = "\n";
	public static final String TABS = "\t";
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	public static final String SELECTONE = "selectOne";
	public static final String SELECTONER = "selectOneR";
	public static final String SELECTONEL = "selectOneL";
	public static final String INSERTONE = "insertOne";
	public static final String BATCHINSERT = "batchInsert";
	public static final String BATCHINSERTXML = "batchInsertXML";
	public static final String UPDATEONE = "updateOne";
	public static final String UPDATEONER = "updateOneR";
	public static final String UPDATEONEL = "updateOneL";
	public static final String BATCHUPDATE = "batchUpdate";
	public static final String BATCHUPDATEXML = "batchUpdateXML";
	public static final String DELETEONE = "deleteOne";
	public static final String DELETEONEL = "deleteOneL";
	public static final String SELECTALL = "selectAll";
	public static final String SELECTALLR = "selectAllR";
	public static final String SELECTALLL = "selectAllL";
	public static final String BATCHCOMMITSIZE = "batchCommitSize";
	public static final String AND = " and ";
	public static ThreadLocal<List<String>> isOneIndex = new ThreadLocal<List<String>>(){
		 protected List<String> initialValue() {
			 return new ArrayList<String>();
		 }
	};
	
	public static ThreadLocal<Map<String, List<String>>> methodMaps = new ThreadLocal<Map<String, List<String>>>(){
		 protected Map<String, List<String>> initialValue() {
			 return new LinkedHashMap<>();
		 }
	};
	
	static{
		Properties properties = new Properties();
		try{
			InputStreamReader is = new InputStreamReader(CodeGeneration.class
					.getResourceAsStream("config/template/InterfaceMethodTemplate.properties"),Charset.forName("UTF-8"));
			properties.load(is);
		} catch(IOException e){
			logger.error("加载资源文件失败: {}" + MineException.getStackTrace(e));
		}
		
		Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Object, Object> entry = iterator.next();
			interfaceMethodMap.put(entry.getKey().toString(), entry.getValue().toString());
		}
		
	}
	/**
	 * 增加类注释
	 * @param javaFile
	 * @param dto
	 * @param javaFileName
	 * @param tableName
	 */
	public static void appendClassAnnotation(StringBuffer javaFile, CodeDto dto, String javaFileName, String tableName){
		appendClassAnnotation(javaFile, javaFileName, tableName + "--" + dto.getTableComment());
	}
	
	/**
	 * 增加类注释
	 * @param javaFile
	 * @param javaFileName
	 * @param comments
	 */
	public static void appendClassAnnotation(StringBuffer javaFile, String javaFileName, String comments){
		javaFile.append("/**").append(NEWLINE);
		javaFile.append(" * ").append(comments).append(NEWLINE);
		javaFile.append(" * ").append("@filename ").append(javaFileName + ".java").append(NEWLINE);
		javaFile.append(" * ").append("@author ").append("wzaUsers").append(NEWLINE);
		javaFile.append(" * ").append("@date ").append(FORMAT.format(new Date())).append(NEWLINE);
		javaFile.append(" * ").append("@version ").append("v1.0").append(NEWLINE);
		javaFile.append("*/").append(NEWLINE);
	}
	
	/**
	 * 创建类属性
	 * @param javaFile
	 * @param dto
	 */
	public static void createField(StringBuffer javaFile, CodeDto dto){
		List<String> fields = dto.getFields();
		Map<String, Object> firstLowerFieldComments = dto.getFirstLowerRemarks();
		Map<String, Object> firstLowerFieldTypes = dto.getFirstLowerTypes();
		for(int i = 0, size = fields.size(); i < size; i++){
			String field = fields.get(i);
			String firstLowerFieldComment = firstLowerFieldComments.get(field) + "";
			String firstLowerFieldType = firstLowerFieldTypes.get(field) + "";
			appendFieldAndAnnotation(javaFile, field, firstLowerFieldComment, firstLowerFieldType);
		}
	}
	
	/**
	 * 增加每个类属性和注释
	 * @param javaFile
	 * @param column
	 * @param fieldComment
	 * @param fieldType
	 */
	public static void appendFieldAndAnnotation(StringBuffer javaFile, String column, String fieldComment, String fieldType){
		javaFile.append(TABS).append("/**").append(NEWLINE);
		javaFile.append(TABS).append(" * ").append(fieldComment).append(NEWLINE);
		javaFile.append(TABS).append(" */").append(NEWLINE);
		javaFile.append(TABS).append("private ").append(fieldType).append(" ").append(column).append(LINE_SUFFIX);
	}
	
	/**
	 * 创建get和set方法
	 * @param javaFile
	 * @param dto
	 */
	public static void createSetterOrGetterField(StringBuffer javaFile, CodeDto dto){
		List<String> columns = dto.getColumns();
		List<String> fields = dto.getFields();
		Map<String, Object> fieldComments = dto.getRemarks();
		Map<String, Object> fieldTypes = dto.getTypes();
		for(int i = 0, size = columns.size(); i < size; i++){
			String column = columns.get(i);
			String field = fields.get(i);
			String fieldComment = fieldComments.get(column) + "";
			String fieldType = fieldTypes.get(column) + "";
			appendSetterAndGetterAndAnnotation(javaFile, column, field, fieldComment, fieldType);
		}
	}
	
	
	/**
	 * 每个方法增加方法体和注释
	 * @param javaFile
	 * @param field
	 * @param fieldComment
	 * @param fieldType
	 */
	public static void appendSetterAndGetterAndAnnotation(StringBuffer javaFile, String column, String field, String fieldComment,
			String fieldType) {
		//getter
		javaFile.append(TABS).append("/**").append(NEWLINE);
		javaFile.append(TABS).append(" * ").append(fieldComment).append(NEWLINE);
		javaFile.append(TABS).append(" * ").append("@return the").append(field).append(NEWLINE);
		javaFile.append(TABS).append(" */").append(NEWLINE);
		javaFile.append(TABS).append("public ").append(fieldType).append(" get").append(column).append("() {").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("return ").append(field).append(LINE_SUFFIX);
		javaFile.append(TABS).append("}").append(NEWLINE);
		//setter
		javaFile.append(TABS).append("/**").append(NEWLINE);
		javaFile.append(TABS).append(" * ").append(fieldComment).append(NEWLINE);
		javaFile.append(TABS).append(" * ").append("@param ").append(field).append(" the ").append(field).append(" to set").append(NEWLINE);
		javaFile.append(TABS).append(" */").append(NEWLINE);
		javaFile.append(TABS).append("public void set").append(column).append("(").append(fieldType).append(" " + field).append(") {").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("this.").append(field).append(" = ").append(field).append(LINE_SUFFIX);
		javaFile.append(TABS).append("}").append(NEWLINE);
	}
	
	/**
	 * 增加toString方法
	 * @param javaFile
	 * @param javaFileName
	 * @param fields
	 */
	public static void addToStringMethod(StringBuffer javaFile, String javaFileName, List<String> fields){
		javaFile.append(TABS).append("/* (non-Javadoc)").append(NEWLINE);
		javaFile.append(TABS).append(" * @see java.lang.Object#toString()").append(NEWLINE);
		javaFile.append(TABS).append(" */").append(NEWLINE);
		javaFile.append(TABS).append("@Override").append(NEWLINE);
		javaFile.append(TABS).append("public String toString() {").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("return \"").append(javaFileName).append("[\" + ").append(NEWLINE);
		javaFile.append(TABS).append(TABS);
		for(int i = 0, size = fields.size(); i < size; i++){
			if(i == 0){
				javaFile.append("\"").append(fields.get(i)).append("=").append("\" + ").append(fields.get(i)).append(" + ");
			} else {
				javaFile.append("\", ").append(fields.get(i)).append("=").append("\" + ").append(fields.get(i)).append(" + ");
			}
				
			if(((i % 3) == 0) && i > 0){
				javaFile.append(NEWLINE);
				javaFile.append(TABS).append(TABS);
			}
			if(i == (size - 1)){
				javaFile.append("\"]\"").append(LINE_SUFFIX);
			}
		}
		javaFile.append(TABS).append("}").append(NEWLINE);
	}
	
	/**
	 * 增加MODEL类中的无参构造函数
	 * @param javaFile
	 * @param javaFileName
	 * @param dto
	 */
	public static void addNoArguConstructor(StringBuffer javaFile, String javaFileName, CodeDto dto){
		//是否存在默认值 false-不存在
		boolean defaultIsEmpty = false;
		List<String> fields = dto.getFields();
		Map<String, Object> firstLowerFieldTypes = dto.getFirstLowerTypes();
		Map<String, Object> firstLowerFieldDefaults = dto.getFirstLowerDefaults();
		String default_value = "";
		String type = "";
		javaFile.append(TABS).append("public ").append(javaFileName).append("() {").append(NEWLINE);
		for(int i = 0, size = fields.size(); i < size; i++){
			String field = fields.get(i);
			javaFile.append(TABS).append(TABS).append("this.").append(field).append(" = ");
			default_value = firstLowerFieldDefaults.get(field) + "";
			type = firstLowerFieldTypes.get(field) + "";
			if(CommonUtils.isNotEmpty(default_value) && !default_value.equalsIgnoreCase("null")){
				defaultIsEmpty = true;
				if(type.equalsIgnoreCase("String")){
					javaFile.append("\"").append(default_value).append("\"");
				} else {
					javaFile.append(default_value);
				}
			}
			
			if(type.equalsIgnoreCase("Long")){
				if(!defaultIsEmpty){
					javaFile.append("0L");
				} else {
					javaFile.append("L");
				}
			} else if(type.equalsIgnoreCase("Integer")){
				if(!defaultIsEmpty){
					javaFile.append("0");
				}
			} else if(type.equalsIgnoreCase("BigDecimal") && !defaultIsEmpty){
				javaFile.append("BigDecimal.ZERO");
			} else if(type.equalsIgnoreCase("String") && !defaultIsEmpty){
				logger.debug(">>>>>>dto >>> {}", CommonUtils.toString(CodeUtil.dateField.get()));
				if(CodeUtil.dateField.get().containsKey(field)){
					javaFile.append("null");
				} else {
					javaFile.append("\"\"");
				}
			}
			javaFile.append(LINE_SUFFIX);
			
			if(defaultIsEmpty){
				defaultIsEmpty = false;
			}
		}
		javaFile.append(TABS).append("}").append(NEWLINE);
	}
	
	/**
	 * 增加方法
	 * @param javaFile
	 * @param javaDaoFileName
	 * @param javaFileName
	 * @param dto
	 * @param daoImplFlag
	 */
	public static void addInterfaceMethod(StringBuffer javaFile, String javaDaoFileName, String javaFileName, CodeDto dto, boolean daoImplFlag){
		List<StringBuffer> uniqueIndexs = dto.getUniqueIndexs();
		List<StringBuffer> normalIndexs = dto.getNormalIndexs();
		int uniqueCount = uniqueIndexs.size();
		int normalCount = normalIndexs.size();
		logger.debug("索引长度: {} {}", uniqueCount, normalCount);
		
		//插入方法
		appendInterfaceMethod(javaFile, interfaceMethodMap.get(INSERTONE), INSERTONE, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		appendInterfaceMethod(javaFile, interfaceMethodMap.get(BATCHINSERT), BATCHINSERT, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		appendInterfaceMethod(javaFile, interfaceMethodMap.get(BATCHINSERTXML), BATCHINSERTXML, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		
		//唯一索引查询+删除
		if(uniqueCount > 0){
			for(int i = 1; i <= uniqueCount; i++){
				String[] columns = uniqueIndexs.get(i - 1).toString().split("\\|");
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(SELECTONE), SELECTONE + i, columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(SELECTONER), SELECTONE + i + "R", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(SELECTONEL), SELECTONE + i + "L", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(DELETEONE), DELETEONE + i, columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(DELETEONEL), DELETEONE + i + "L", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(UPDATEONE), UPDATEONE + i, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(UPDATEONER), UPDATEONE + i + "R", new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(UPDATEONEL), UPDATEONE + i + "L", new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
				if(columns.length == 1){
					isOneIndex.get().add(columns[0]);
				}
			}
			//更新批量方法
			for(int i = 0, size = isOneIndex.get().size(); i < size; i ++){
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(BATCHUPDATEXML), BATCHUPDATEXML + (i + 1), new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
			}
			appendInterfaceMethod(javaFile, interfaceMethodMap.get(BATCHUPDATE), BATCHUPDATE, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		}
		//普通索引查询
		if(normalCount > 0){
			for(int i = 1; i <= normalCount; i ++){
				String[] columns = normalIndexs.get(i - 1).toString().split("\\|");
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(SELECTALL), SELECTALL + i, columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(SELECTALLR), SELECTALL + i + "R", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(SELECTALLL), SELECTALL + i + "L", columns, dto, javaFileName, true, daoImplFlag);
			}
		}
	}
	
	/**
	 * 创建方法详细信息
	 * @param javaFile
	 * @param comments
	 * @param methodName
	 * @param columns
	 * @param dto
	 * @param javaFileName
	 * @param isIndex
	 * @param daoImplFlag
	 */
	public static void appendInterfaceMethod(StringBuffer javaFile, String comments, String methodName,
			String[] columns, CodeDto dto, String javaFileName, boolean isIndex, boolean daoImplFlag) {
		methodMaps.get().put(methodName, Arrays.asList(columns));
		
		StringBuffer buffer = new StringBuffer();
		Map<String, Object> remarks = dto.getFirstLowerRemarks();
		Map<String, Object> types = dto.getFirstLowerTypes();
		javaFile.append(TABS).append("/**").append(NEWLINE);
		javaFile.append(TABS).append(" * ").append(comments).append(NEWLINE);
		for(int i = 0, length = columns.length; i < length; i ++){
			String column = columns[i];
			String comment = remarks.get(column) != null ? remarks.get(column).toString() : "";
			javaFile.append(TABS).append(" * @param ").append(column).append(" ").append(comment)
					.append(NEWLINE);
			Object type = types.get(column);
			if(column.equalsIgnoreCase(javaFileName)){
				if(methodName.startsWith(UPDATEONE) || methodName.startsWith(INSERTONE)){
					buffer.append(column).append(" ").append(CommonUtils.firstLetterLowerCase(column)).append(", ");
				} else {
					buffer.append("List").append("<").append(column).append(">").append(" list, ");
				}
			} else if(CommonUtils.isNotEmpty(type)){
				buffer.append(type).append(" ").append(column).append(", ");
			}
		}
		javaFile.append(TABS).append(" */").append(NEWLINE);
		String returnType = getReturnType(methodName, javaFileName);
		if(daoImplFlag){
			javaFile.append(TABS).append("@Override").append(NEWLINE);
			javaFile.append(TABS).append("public ").append(returnType).append(" ").append(methodName).append("(")
					.append(buffer.length() <= 0 ? "" : buffer.substring(0, buffer.length() - 2)).append(")")
					.append("{").append(NEWLINE);
			//增加方法体
			appendInterfaceMethodBody(javaFile, methodName, javaFileName, columns, isIndex);
			javaFile.append(TABS).append("}").append(NEWLINE).append(NEWLINE);
		} else {
			javaFile.append(TABS).append(returnType).append(" ").append(methodName).append("(")
					.append(buffer.length() <= 0 ? "" : buffer.substring(0, buffer.length() - 2)).append(")")
					.append(LINE_SUFFIX);
		}
	}
	
	/**
	 * 获取返回值类型
	 * @param methodName
	 * @param javaFileName
	 * @return
	 */
	public static String getReturnType(String methodName, String javaFileName) {
		String returnType = "";
		if (methodName.indexOf(SELECTONE) != -1) {
			returnType = javaFileName;
		} else if (methodName.indexOf(SELECTALL) != -1) {
			returnType = "List<" + javaFileName + ">";
		} else if (methodName.indexOf(DELETEONE) != -1 || methodName.indexOf(UPDATEONE) != -1
				|| methodName.indexOf(INSERTONE) != -1) {
			returnType = "int";
		} else if (methodName.indexOf(BATCHUPDATE) != -1 || methodName.indexOf(BATCHINSERT) != -1) {
			returnType = "void";
		}
		return returnType;
	}
	
	/**
	 * 创建实现类方法体
	 * @param javaFile
	 * @param methodName
	 * @param javaFileName
	 * @param columns
	 * @param isIndex
	 */
	public static void appendInterfaceMethodBody(StringBuffer javaFile, String methodName, String javaFileName, String[] columns, boolean isIndex){
		if(isIndex){
			javaFile.append(TABS).append(TABS).append("Map<String, Object> map = new HashMap<String, Object>()").append(LINE_SUFFIX);
			for(String param : columns){
				javaFile.append(TABS).append(TABS).append("map.put(\"").append(param).append("\", ").append(param).append(")").append(LINE_SUFFIX);
			}
			javaFile.append(TABS).append(TABS).append("return getSqlSessionTemplate().").append(getSessionTypeName(methodName))
			.append("(\"").append(javaFileName).append(".").append(methodName).append("\"").append(", map)").append(LINE_SUFFIX);
		} else {
			if(methodName.startsWith(BATCHINSERTXML) || methodName.startsWith(BATCHUPDATEXML)){
				javaFile.append(TABS).append(TABS).append("BatchInsertByXML(list, \"list\", ")
						.append(interfaceMethodMap.get(BATCHCOMMITSIZE)).append(", new BatchOperator() {")
						.append(NEWLINE);
				javaFile.append(TABS).append(TABS).append(TABS).append("@Override").append(NEWLINE);
				javaFile.append(TABS).append(TABS).append(TABS).append("public void call(Map<String, Object> map) {").append(NEWLINE);
				javaFile.append(TABS).append(TABS).append(TABS).append(TABS).append("getSqlSessionTemplate().")
				.append(getSessionTypeName(methodName)).append("(\"").append(javaFileName).append(".").append(methodName)
				.append("\", map)").append(LINE_SUFFIX);
				javaFile.append(TABS).append(TABS).append(TABS).append("}").append(LINE_SUFFIX);
				javaFile.append(TABS).append(TABS).append("})").append(LINE_SUFFIX);
			} else if((methodName.startsWith(BATCHINSERT) || methodName.startsWith(BATCHUPDATE)) && !methodName.endsWith("XML")){
				javaFile.append(TABS).append(TABS).append("batchExcutor(\"").append(javaFileName).append(".");
				
				if(methodName.startsWith(BATCHINSERT)){
					javaFile.append(methodName).append("\", list, \"insert\", ")
							.append(interfaceMethodMap.get(BATCHCOMMITSIZE)).append(")").append(LINE_SUFFIX);
				} else if(methodName.startsWith(BATCHUPDATE)){
					javaFile.append(methodName).append("\", list, \"update\", ")
							.append(interfaceMethodMap.get(BATCHCOMMITSIZE)).append(")").append(LINE_SUFFIX);
				}
				
			} else {
				javaFile.append(TABS).append(TABS).append("return getSqlSessionTemplate().")
						.append(getSessionTypeName(methodName)).append("(\"").append(javaFileName).append(".")
						.append(methodName).append("\", ").append(CommonUtils.firstLetterLowerCase(javaFileName))
						.append(")").append(LINE_SUFFIX);
			}
		}
	}
	
	/**
	 * 获取sqlsession的方法名
	 * @param methodName
	 * @return
	 */
	public static String getSessionTypeName(String methodName){
		String sessionTypeName = "";
		if(methodName.indexOf(SELECTONE) != -1){
			sessionTypeName = SELECTONE;
		} else if(methodName.indexOf(SELECTALL) != -1){
			sessionTypeName = "selectList";
		} else if(methodName.indexOf(DELETEONE) != -1){
			sessionTypeName = "delete";
		} else if(methodName.indexOf(UPDATEONE) != -1 || methodName.indexOf(BATCHUPDATE) != -1){
			sessionTypeName = "update";
		} else if(methodName.indexOf(INSERTONE) != -1 || methodName.indexOf(BATCHINSERT) != -1){
			sessionTypeName = "insert";
		}
		return sessionTypeName;
	}
	
	public static void addMapperXmlInfo(CodeDto dto, StringBuffer javaFile, String javaFileName, String tableName){
		Map<String, String> orginalColumns = dto.getOrginalColumns();
		appendResultMap(javaFile, dto, javaFileName);
		for(Map.Entry<String, List<String>> entry : methodMaps.get().entrySet()){
			String methodName = entry.getKey();
			List<String> params = entry.getValue(); 
			if(methodName.startsWith("select")){
				appendSelectSql(javaFile, methodName, params, tableName, orginalColumns, javaFileName);
			} else if(methodName.startsWith("insert")){
				appendInsertSql(javaFile, methodName, params, tableName, orginalColumns, javaFileName);
			} else if(methodName.startsWith("update")){
				appendUpdateSql(javaFile, methodName, dto, params, tableName, javaFileName);
			} else if(methodName.startsWith("delete")){
				appendDeleteSql(javaFile, methodName, params, tableName, orginalColumns, javaFileName);
			} else if(methodName.startsWith("batchInsert")){
				if(methodName.endsWith("XML")){
					appendBatchInsertSql(javaFile, methodName, dto, javaFileName, tableName);
				} else {
					appendInsertSql(javaFile, methodName, params, tableName, orginalColumns, javaFileName);
				}
			} else if(methodName.startsWith("batchUpdate")){
				if(methodName.contains("batchUpdateXML") && isOneIndex.get().size() > 0){
					for(String unqiue : isOneIndex.get()){
						appendBatchUpdateSql(javaFile, methodName, dto, javaFileName, tableName, unqiue);
					}
				} else {
					appendUpdateSql(javaFile, methodName, dto, params, tableName, javaFileName);
				}
			}
		}
	}
	
	/**
	 * 增加sql片段以及resultMap
	 * @param javaFile
	 * @param dto
	 * @param javaFileName
	 */
	public static void appendResultMap(StringBuffer javaFile, CodeDto dto, String javaFileName) {
		List<String> fields = dto.getFields();
		Map<String, String> orginalColumns = dto.getOrginalColumns();
		//select
		StringBuffer selectBuffer = new StringBuffer();
		selectBuffer.append(TABS).append("<sql id=\"Base_Select_List\">").append(NEWLINE).append(TABS).append(TABS);
		//insert-column
		StringBuffer insertCBuffer = new StringBuffer();
		insertCBuffer.append(TABS).append("<sql id=\"Base_Insert_Column\">").append(NEWLINE);
		insertCBuffer.append(TABS).append(TABS).append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(NEWLINE);
		//insert-value
		StringBuffer insertVBuffer = new StringBuffer();
		insertVBuffer.append(TABS).append("<sql id=\"Base_Insert_Values\">").append(NEWLINE);
		insertVBuffer.append(TABS).append(TABS).append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(NEWLINE);
		
		javaFile.append(TABS).append("<resultMap type=\"").append(CodeUtil.PACKAGE_MODEL).append(".").append(javaFileName)
				.append("\" id=\"BaseResultMap\">").append(NEWLINE);
		for(int i = 0, size = fields.size(); i < size; i ++){
			String field = fields.get(i);
			String orginal = orginalColumns.get(field);
			javaFile.append(TABS).append(TABS).append("<result column=\"").append(orginal)
					.append("\" property=\"").append(field).append("\"/>").append(NEWLINE);
			//select
			selectBuffer.append(orginal).append(",");
			if((i % 5) == 0 && i > 0 && i < (size - 1)){
				selectBuffer.append(NEWLINE).append(TABS).append(TABS);
			}
			//insert-column
			insertCBuffer.append(TABS).append(TABS).append(TABS).append("<if test=\"").append(field).append(" != null\">")
			.append(orginal).append(",</if>").append(NEWLINE);
			//insert-value
			insertVBuffer.append(TABS).append(TABS).append(TABS).append("<if test=\"").append(field).append(" != null\">")
			.append("#{" + field + "}").append(",</if>").append(NEWLINE);
		}
		javaFile.append(TABS).append("</resultMap>").append(NEWLINE).append(NEWLINE);
		javaFile.append(selectBuffer.substring(0, selectBuffer.length() - 1));
		javaFile.append(NEWLINE).append(TABS).append("</sql>").append(NEWLINE).append(NEWLINE);
		javaFile.append(insertCBuffer).append(TABS).append(TABS).append("</trim>").append(NEWLINE);
		javaFile.append(TABS).append("</sql>").append(NEWLINE).append(NEWLINE);
		javaFile.append(insertVBuffer).append(TABS).append(TABS).append("</trim>").append(NEWLINE);
		javaFile.append(TABS).append("</sql>").append(NEWLINE).append(NEWLINE);
	}
	
	public static void appendSelectSql(StringBuffer javaFile, String methodName, List<String> params, String tableName,
			Map<String, String> orginalColumns, String javaFileName) {
		StringBuffer buffer = new StringBuffer();
		javaFile.append(TABS).append("<select id=\"").append(methodName)
				.append("\" parameterType=\"java.util.HashMap\" resultMap=\"BaseResultMap\">").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("select").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("<include refid=\"" + javaFileName + ".Base_Select_List\"/>").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("from ").append(tableName).append(NEWLINE).append(TABS).append(TABS).append("where ");
		for(int i = 0, size = params.size(); i < size; i ++){
			String param = params.get(i);
			if(i == 0){
				buffer.append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			} else {
				buffer.append(AND).append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			}
			if((i % 3 == 0) && i > 0  && i < (size - 1)){
				buffer.append(NEWLINE).append(TABS).append(TABS);
			}
		}
		javaFile.append(buffer).append(getSqlSuffix(methodName)).append(NEWLINE);
		javaFile.append(TABS).append("</select>").append(NEWLINE).append(NEWLINE);
	}
	
	public static void appendInsertSql(StringBuffer javaFile, String methodName, List<String> params, String tableName,
			Map<String, String> orginalColumns, String javaFileName) {
		javaFile.append(TABS).append("<insert id=\"").append(methodName).append("\" parameterType=\"")
				.append(CodeUtil.PACKAGE_MODEL).append(".").append(javaFileName).append("\">").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("insert into ").append(tableName).append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("<include refid=\"" + javaFileName + ".Base_Insert_Column\"/>").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("values ").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("<include refid=\"" + javaFileName + ".Base_Insert_Values\"/>").append(NEWLINE);
		javaFile.append(TABS).append("</insert>").append(NEWLINE).append(NEWLINE);
	}
	
	public static void appendUpdateSql(StringBuffer javaFile, String methodName, CodeDto dto, List<String> params,
			String tableName, String javaFileName) {
		List<String> fields = dto.getFields();
		Map<String, String> orginalColumns = dto.getOrginalColumns();
		List<StringBuffer> uniqueIndexs = dto.getUniqueIndexs();
		for(StringBuffer unqiue : uniqueIndexs){
			String[] strs = unqiue.toString().split("\\|");
			List<String> fields_bak = fields;
			fields_bak.removeAll(Arrays.asList(strs));
			StringBuffer buffer = new StringBuffer();
			javaFile.append(TABS).append("<update id=\"").append(methodName).append("\" parameterType=\"")
			.append(CodeUtil.PACKAGE_MODEL).append(".").append(javaFileName).append("\">").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append("update ").append(tableName).append(NEWLINE);
			javaFile.append(TABS).append(TABS).append("<trim prefix=\"set\" suffixOverrides=\",\">").append(NEWLINE);
			for(int i = 0, size = fields_bak.size(); i < size; i ++){
				String field = fields_bak.get(i);
				javaFile.append(TABS).append(TABS).append(TABS).append("<if test=\"").append(field).append(" != null\">")
				.append(orginalColumns.get(field)).append(" = #{").append(field).append("},</if>").append(NEWLINE);
			}
			
			for(int i = 0, length = strs.length; i < length; i ++){
				String s = strs[i];
				if(i == 0){
					buffer.append(orginalColumns.get(s)).append(" = #{").append(s).append("}");
				} else {
					buffer.append(AND).append(orginalColumns.get(s)).append(" = #{").append(s).append("}");
				}
				
				if((i % 3 == 0) && i > 0  && i < (length - 1)){
					buffer.append(NEWLINE).append(TABS).append(TABS);
				}
			}
			javaFile.append(TABS).append(TABS).append("</trim>").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append("where ").append(buffer).append(getSqlSuffix(methodName)).append(NEWLINE);
			javaFile.append(TABS).append("</update>").append(NEWLINE).append(NEWLINE);
		}
	}
	
	public static void appendDeleteSql(StringBuffer javaFile, String methodName, List<String> params, String tableName,
			Map<String, String> orginalColumns, String javaFileName){
		StringBuffer buffer = new StringBuffer();
		javaFile.append(TABS).append("<delete id=\"").append(methodName)
				.append("\" parameterType=\"java.util.HashMap\">").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("delete ").append("from ").append(tableName).append(NEWLINE)
		.append(TABS).append(TABS).append("where ");
		for(int i = 0, size = params.size(); i < size; i ++){
			String param = params.get(i);
			if(i == 0){
				buffer.append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			} else {
				buffer.append(AND).append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			}
			
			if((i % 3 == 0) && i > 0  && i < (size - 1)){
				buffer.append(NEWLINE).append(TABS).append(TABS);
			}
		}
		javaFile.append(buffer).append(getSqlSuffix(methodName)).append(NEWLINE);
		javaFile.append(TABS).append("</delete>").append(NEWLINE).append(NEWLINE);
	}
	
	public static void appendBatchInsertSql(StringBuffer javaFile, String methodName, CodeDto dto, String javaFileName,
			String tableName) {
		StringBuffer buffer = new StringBuffer();
		List<String> fields = dto.getFields();
		Map<String, String> orignalColumns = dto.getOrginalColumns();
		javaFile.append(TABS).append("<insert id=\"").append(methodName).append("\" parameterType=\"java.util.List\">").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("insert into ").append(tableName).append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("(");
		buffer.append(TABS).append(TABS).append(TABS).append("(");
		for(int i = 0, size = fields.size(); i < size; i ++){
			String orignalColumn = orignalColumns.get(fields.get(i));
			if(i == 0){
				javaFile.append(orignalColumn);
				buffer.append("#{item.").append(fields.get(i)).append("}");
			} else {
				javaFile.append(",").append(orignalColumn);
				buffer.append(",").append("#{item.").append(fields.get(i)).append("}");
			}
			if((i % 4 == 0) && i > 0 && (i < size - 1)){
				javaFile.append(NEWLINE).append(TABS).append(TABS).append(TABS);
				buffer.append(NEWLINE).append(TABS).append(TABS).append(TABS).append(TABS);
			}
		}
		javaFile.append(")").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("values").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">").append(NEWLINE);
		javaFile.append(buffer).append(")").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("</foreach>").append(NEWLINE);
		javaFile.append(TABS).append("</insert>").append(NEWLINE);
	}
	
	public static void appendBatchUpdateSql(StringBuffer javaFile, String methodName, CodeDto dto, String javaFileName,
			String tableName, String unique){
		List<String> fields = dto.getFields();
		Map<String, String> orginalColumns = dto.getOrginalColumns();
		String index_orginal = orginalColumns.get(unique);
		javaFile.append(TABS).append("<update id=\"").append(methodName).append("\" parameterType=\"java.util.List\">").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("update ").append(tableName).append(" set").append(NEWLINE);
		List<String> fields_bak = fields;
		fields_bak.remove(unique);
		for(int i = 0, size = fields_bak.size(); i < size; i ++){
			String field_bak = fields_bak.get(i);
			String orginal_colunm = orginalColumns.get(field_bak);
			if(i == (size - 1)){
				javaFile.append(TABS).append(TABS).append("<trim prefix=\"").append(orginal_colunm)
				.append("=case\" suffix=\"end\">").append(NEWLINE);
			} else {
				javaFile.append(TABS).append(TABS).append("<trim prefix=\"").append(orginal_colunm)
				.append("=case\" suffix=\"end,\">").append(NEWLINE);
			}
			javaFile.append(TABS).append(TABS).append(TABS)
			.append("<foreach collection=\"list\" item=\"item\" index=\"index\">").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append(TABS).append(TABS).append("<if test=\"item.")
			.append(field_bak).append(" != null\">").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append(TABS).append(TABS).append(TABS).append("when ")
			.append(index_orginal).append(" = #{item.").append(unique).append("} then #{item.")
			.append(field_bak).append("}").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append(TABS).append(TABS).append("</if>").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append(TABS).append("</foreach>").append(NEWLINE);
			javaFile.append(TABS).append(TABS).append("</trim>").append(NEWLINE);
		}
		javaFile.append(TABS).append(TABS).append("where").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append(index_orginal).append(" in").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("<foreach collection=\"list\" item=\"item\" index=\"index\"")
		.append(" separator=\",\" open=\"(\" close=\")\">").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append(TABS).append("#{item.").append(unique).append("}").append(NEWLINE);
		javaFile.append(TABS).append(TABS).append("</foreach>").append(NEWLINE);
		javaFile.append(TABS).append("</update>").append(NEWLINE);
	}
	
	public static String getSqlSuffix(String methodName){
		String flag = methodName.substring(methodName.length() - 1, methodName.length());
		String suffix = "";
		if(flag.equals("R")){
			suffix = " and vaild_status = '0'";
		} else if(flag.equals("L")){
			suffix = " for update";
		}
		return suffix;
	}
	
	/**
	 * 增加制表符
	 * @param javaFile 字符对象
	 * @param count 增加制表符的个数
	 */
	public static void addTabs(StringBuffer javaFile, int count){
		String tabs_info = "";
		while(count > 0){
			tabs_info += TABS;
			count--;
		}
		javaFile.insert(0, tabs_info);
		for(int i = 0, length = javaFile.length(); i < length; i ++){
			//ACSII中10代表的是换行键
			if(javaFile.charAt(i) == 10){
				javaFile.insert(i + 1, tabs_info);
			}
		}
	}
}
