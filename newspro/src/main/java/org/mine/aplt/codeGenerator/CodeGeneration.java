package org.mine.aplt.codeGenerator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeGeneration {
	private static final Logger logger = LoggerFactory.getLogger(CodeGeneration.class);
	private static final String PROPERTIES_PATH = "config/code/template/InterfaceMethodTemplate.properties";
	public static Map<String, String> interfaceMethodMap = new HashMap<String, String>();
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	private static ThreadLocal<List<String[]>> isOneIndex = new ThreadLocal<List<String[]>>(){
		 protected List<String[]> initialValue() {
			 return new ArrayList<String[]>();
		 }
	};

	/**
	 * 增加对象来实现值去重
	 * duplicateRemoval
	 */
	public static ThreadLocal<Set<UniqueIndexDto>> duplicateRemoval = new ThreadLocal<Set<UniqueIndexDto>>(){
		 protected Set<UniqueIndexDto> initialValue() {
			 return new HashSet<UniqueIndexDto>();
		 }
	};

	public static ThreadLocal<Map<String, List<String>>> methodMaps = new ThreadLocal<Map<String, List<String>>>(){
		 protected Map<String, List<String>> initialValue() {
			 return new LinkedHashMap<>();
		 }
	};

	public static Map<String, String[]> mapU = new ConcurrentHashMap<>();

	static{
		Properties properties = new Properties();
		try{
			InputStreamReader is = new InputStreamReader(CodeGeneration.class.getClassLoader()
					.getResourceAsStream(PROPERTIES_PATH),Charset.forName("UTF-8"));
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

	public static void clear(){
		isOneIndex.set(new ArrayList<String[]>());
		duplicateRemoval.set(new HashSet<UniqueIndexDto>());
		methodMaps.set(new LinkedHashMap<>());
		mapU.clear();
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
		javaFile.append("/**").append(ApltContanst.NEWLINE);
		javaFile.append(" * ").append(comments).append(ApltContanst.NEWLINE);
		javaFile.append(" * ").append("@filename ").append(javaFileName + ".java").append(ApltContanst.NEWLINE);
		javaFile.append(" * ").append("@author ").append("wzaUsers").append(ApltContanst.NEWLINE);
		javaFile.append(" * ").append("@date ").append(FORMAT.format(new Date())).append(ApltContanst.NEWLINE);
		javaFile.append(" * ").append("@version ").append("v1.0").append(ApltContanst.NEWLINE);
		javaFile.append("*/").append(ApltContanst.NEWLINE);
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
		javaFile.append(ApltContanst.TABS).append("/**").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * ").append(fieldComment).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" */").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("private ").append(fieldType).append(" ").append(column).append(ApltContanst.LINE_SUFFIX);
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
		javaFile.append(ApltContanst.TABS).append("/**").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * ").append(fieldComment).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * ").append("@return the ").append(field).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" */").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("public ").append(fieldType).append(" get").append(column).append("() {").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("return ").append(field).append(ApltContanst.LINE_SUFFIX);
		javaFile.append(ApltContanst.TABS).append("}").append(ApltContanst.NEWLINE);
		//setter
		javaFile.append(ApltContanst.TABS).append("/**").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * ").append(fieldComment).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * ").append("@param ").append(field).append(" the ").append(field).append(" to set").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" */").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("public void set").append(column).append("(").append(fieldType).append(" " + field).append(") {").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("this.").append(field).append(" = ").append(field).append(ApltContanst.LINE_SUFFIX);
		javaFile.append(ApltContanst.TABS).append("}").append(ApltContanst.NEWLINE);
	}
	
	/**
	 * 增加toString方法
	 * @param javaFile
	 * @param javaFileName
	 * @param fields
	 */
	public static void addToStringMethod(StringBuffer javaFile, String javaFileName, List<String> fields){
		javaFile.append(ApltContanst.TABS).append("/* (non-Javadoc)").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * @see java.lang.Object#toString()").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" */").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("@Override").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("public String toString() {").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("return \"").append(javaFileName).append("[\" + ").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS);
		for(int i = 0, size = fields.size(); i < size; i++){
			if(i == 0){
				javaFile.append("\"").append(fields.get(i)).append("=").append("\" + ").append(fields.get(i)).append(" + ");
			} else {
				javaFile.append("\", ").append(fields.get(i)).append("=").append("\" + ").append(fields.get(i)).append(" + ");
			}
				
			if(((i % 3) == 0) && i > 0){
				javaFile.append(ApltContanst.NEWLINE);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS);
			}
			if(i == (size - 1)){
				javaFile.append("\"]\"").append(ApltContanst.LINE_SUFFIX);
			}
		}
		javaFile.append(ApltContanst.TABS).append("}").append(ApltContanst.NEWLINE);
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
		javaFile.append(ApltContanst.TABS).append("public ").append(javaFileName).append("() {").append(ApltContanst.NEWLINE);
		for(int i = 0, size = fields.size(); i < size; i++){
			String field = fields.get(i);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("this.").append(field).append(" = ");
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
			javaFile.append(ApltContanst.LINE_SUFFIX);
			
			if(defaultIsEmpty){
				defaultIsEmpty = false;
			}
		}
		javaFile.append(ApltContanst.TABS).append("}").append(ApltContanst.NEWLINE);
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
		int uniqueCount = uniqueIndexs == null ? 0 : uniqueIndexs.size();
		int normalCount = normalIndexs == null ? 0 : normalIndexs.size();
		logger.debug("索引长度: {} {}", uniqueCount, normalCount);
		
		//插入方法
		appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.INSERTONE), ApltContanst.INSERTONE, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.BATCHINSERT), ApltContanst.BATCHINSERT, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.BATCHINSERTXML), ApltContanst.BATCHINSERTXML, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
		
		//唯一索引查询+删除
		if(uniqueCount > 0){
			for(int i = 1; i <= uniqueCount; i++){
				String[] columns = uniqueIndexs.get(i - 1).toString().split("\\|");
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.SELECTONE), ApltContanst.SELECTONE + i, columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.SELECTONER), ApltContanst.SELECTONE + i + "R", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.SELECTONEL), ApltContanst.SELECTONE + i + "L", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.DELETEONE), ApltContanst.DELETEONE + i, columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.DELETEONEL), ApltContanst.DELETEONE + i + "L", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.UPDATEONE), ApltContanst.UPDATEONE + i, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.UPDATEONER), ApltContanst.UPDATEONE + i + "R", new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.UPDATEONEL), ApltContanst.UPDATEONE + i + "L", new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);

				UniqueIndexDto indexDto = new UniqueIndexDto();
				indexDto.setColumns(columns);
				//当Set集合中添加成功时,便代表当前值不重复
				if (duplicateRemoval.get().add(indexDto)) {
					isOneIndex.get().add(columns);
					mapU.put(ApltContanst.UPDATEONE + i, columns);
					mapU.put(ApltContanst.UPDATEONE + i + "R", columns);
					mapU.put(ApltContanst.UPDATEONE + i + "L", columns);
				}
			}
			System.out.println(">>>>>zhujian>>>>>>>>> " + CommonUtils.toString(isOneIndex.get()));
			//更新批量方法
			for(int i = 0, size = isOneIndex.get().size(); i < size; i ++){
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.BATCHUPDATE), ApltContanst.BATCHUPDATE + (i + 1), new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
				mapU.put(ApltContanst.BATCHUPDATE + (i + 1), isOneIndex.get().get(i));
			}
			appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.BATCHUPDATEXML), ApltContanst.BATCHUPDATEXML, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);
			appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.BATCHDELETE), ApltContanst.BATCHDELETE, new String[]{javaFileName}, dto, javaFileName, false, daoImplFlag);

		}
		//普通索引查询
		if(normalCount > 0){
			for(int i = 1; i <= normalCount; i ++){
				String[] columns = normalIndexs.get(i - 1).toString().split("\\|");
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.SELECTALL), ApltContanst.SELECTALL + i, columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.SELECTALLR), ApltContanst.SELECTALL + i + "R", columns, dto, javaFileName, true, daoImplFlag);
				appendInterfaceMethod(javaFile, interfaceMethodMap.get(ApltContanst.SELECTALLL), ApltContanst.SELECTALL + i + "L", columns, dto, javaFileName, true, daoImplFlag);
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
		javaFile.append(ApltContanst.TABS).append("/**").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(" * ").append(comments).append(ApltContanst.NEWLINE);
		for(int i = 0, length = columns.length; i < length; i ++){
			String column = columns[i];
			String comment = remarks.get(column) != null ? remarks.get(column).toString() : "";
			javaFile.append(ApltContanst.TABS).append(" * @param ").append(methodName.startsWith("batch") ? "list" : CommonUtils.firstLetterLowerCase(column)).append(" ").append(comment)
					.append(ApltContanst.NEWLINE);
			Object type = types.get(column);
			if(column.equalsIgnoreCase(javaFileName)){
				if(methodName.startsWith(ApltContanst.UPDATEONE) || methodName.startsWith(ApltContanst.INSERTONE)){
					buffer.append(column).append(" ").append(CommonUtils.firstLetterLowerCase(column)).append(", ");
				} else {
					buffer.append("List").append("<").append(column).append(">").append(" list, ");
				}
			} else if(CommonUtils.isNotEmpty(type)){
				buffer.append(type).append(" ").append(column).append(", ");
				if(methodName.startsWith(ApltContanst.SELECTONE) && (i == length - 1)){
					buffer.append("boolean nullException").append(", ");
				}
			}
		}
		javaFile.append(ApltContanst.TABS).append(" */").append(ApltContanst.NEWLINE);
		String returnType = getReturnType(methodName, javaFileName);
		if(daoImplFlag){
			javaFile.append(ApltContanst.TABS).append("@Override").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append("public ").append(returnType).append(" ").append(methodName).append("(")
					.append(buffer.length() <= 0 ? "" : buffer.substring(0, buffer.length() - 2)).append(")")
					.append("{").append(ApltContanst.NEWLINE);
			//增加方法体
			appendInterfaceMethodBody(javaFile, methodName, javaFileName, columns, isIndex);
			javaFile.append(ApltContanst.TABS).append("}").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
		} else {
			javaFile.append(ApltContanst.TABS).append(returnType).append(" ").append(methodName).append("(")
					.append(buffer.length() <= 0 ? "" : buffer.substring(0, buffer.length() - 2)).append(")")
					.append(ApltContanst.LINE_SUFFIX);
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
		if (methodName.indexOf(ApltContanst.SELECTONE) != -1) {
			returnType = javaFileName;
		} else if (methodName.indexOf(ApltContanst.SELECTALL) != -1) {
			returnType = "List<" + javaFileName + ">";
		} else if (methodName.indexOf(ApltContanst.DELETEONE) != -1 || methodName.indexOf(ApltContanst.UPDATEONE) != -1
				|| methodName.indexOf(ApltContanst.INSERTONE) != -1) {
			returnType = "int";
		} else if (methodName.indexOf(ApltContanst.BATCHUPDATE) != -1 || methodName.indexOf(ApltContanst.BATCHINSERT) != -1
				|| methodName.indexOf(ApltContanst.BATCHDELETE) != -1) {
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
			if(methodName.startsWith(ApltContanst.SELECTONE)){
				String firstLetter = CommonUtils.firstLetterLowerCase(javaFileName);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS)
						.append("Map<String, Object> map = new HashMap<String, Object>()").append(ApltContanst.LINE_SUFFIX);
				for(String param : columns){
					javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("map.put(\"").append(param)
							.append("\", ").append(param).append(")").append(ApltContanst.LINE_SUFFIX);
				}
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(javaFileName).append(" ")
				.append(firstLetter).append(" = new ").append(javaFileName).append("()").append(ApltContanst.LINE_SUFFIX);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(firstLetter).append(" = (").append(javaFileName)
				.append(")getSqlSessionTemplate().").append(getSessionTypeName(methodName)).append("(\"").append(javaFileName).append(".")
				.append(methodName).append("\"").append(", map)").append(ApltContanst.LINE_SUFFIX);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("if (").append(firstLetter).append(" == null && nullException)")
				.append("throw GitWebException.GIT_NOTFOUNT(\"").append(CommonUtils.humpToUnderLine(javaFileName)).append("\", CommonUtils.toString(map))")
				.append(ApltContanst.LINE_SUFFIX);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("return ").append(firstLetter).append(ApltContanst.LINE_SUFFIX);
			} else {
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS)
				.append("Map<String, Object> map = new HashMap<String, Object>()").append(ApltContanst.LINE_SUFFIX);
				for(String param : columns){
					javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("map.put(\"").append(param)
					.append("\", ").append(param).append(")").append(ApltContanst.LINE_SUFFIX);
				}
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("return getSqlSessionTemplate().").append(getSessionTypeName(methodName))
				.append("(\"").append(javaFileName).append(".").append(methodName).append("\"").append(", map)").append(ApltContanst.LINE_SUFFIX);
			}
		} else {
			if(methodName.startsWith(ApltContanst.BATCHINSERTXML) || methodName.startsWith(ApltContanst.BATCHUPDATEXML)){
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("BatchInsertByXML(list, \"list\", ")
						.append(interfaceMethodMap.get(ApltContanst.BATCHCOMMITSIZE)).append(", new BatchOperator<Integer, Map<String, Object>>() {")
						.append(ApltContanst.NEWLINE);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("@Override").append(ApltContanst.NEWLINE);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("public Integer call(Map<String, Object> map) {").append(ApltContanst.NEWLINE);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("return getSqlSessionTemplate().")
				.append(getSessionTypeName(methodName)).append("(\"").append(javaFileName).append(".").append(methodName)
				.append("\", map)").append(ApltContanst.LINE_SUFFIX);
//				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("return null").append(ApltContanst.LINE_SUFFIX);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("}").append(ApltContanst.LINE_SUFFIX);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("})").append(ApltContanst.LINE_SUFFIX);
			} else if((methodName.startsWith(ApltContanst.BATCHINSERT) || methodName.startsWith(ApltContanst.BATCHUPDATE)
					|| methodName.startsWith(ApltContanst.BATCHDELETE)) && !methodName.endsWith("XML")){
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("batchExcutor(\"").append(javaFileName).append(".");
				
				if(methodName.startsWith(ApltContanst.BATCHINSERT)){
					javaFile.append(methodName).append("\", list, \"insert\", ")
							.append(interfaceMethodMap.get(ApltContanst.BATCHCOMMITSIZE)).append(")").append(ApltContanst.LINE_SUFFIX);
				} else if(methodName.startsWith(ApltContanst.BATCHUPDATE)){
					javaFile.append(methodName).append("\", list, \"update\", ")
							.append(interfaceMethodMap.get(ApltContanst.BATCHCOMMITSIZE)).append(")").append(ApltContanst.LINE_SUFFIX);
				} else if(methodName.startsWith(ApltContanst.BATCHDELETE)){
					javaFile.append(ApltContanst.DELETEONE + 1).append("\", list, \"delete\", ")
					.append(interfaceMethodMap.get(ApltContanst.BATCHCOMMITSIZE)).append(")").append(ApltContanst.LINE_SUFFIX);
				}
				
			} else {
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("return getSqlSessionTemplate().")
						.append(getSessionTypeName(methodName)).append("(\"").append(javaFileName).append(".")
						.append(methodName).append("\", ").append(CommonUtils.firstLetterLowerCase(javaFileName))
						.append(")").append(ApltContanst.LINE_SUFFIX);
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
		if(methodName.indexOf(ApltContanst.SELECTONE) != -1){
			sessionTypeName = ApltContanst.SELECTONE;
		} else if(methodName.indexOf(ApltContanst.SELECTALL) != -1){
			sessionTypeName = "selectList";
		} else if(methodName.indexOf(ApltContanst.DELETEONE) != -1){
			sessionTypeName = "delete";
		} else if(methodName.indexOf(ApltContanst.UPDATEONE) != -1 || methodName.indexOf(ApltContanst.BATCHUPDATE) != -1){
			sessionTypeName = "update";
		} else if(methodName.indexOf(ApltContanst.INSERTONE) != -1 || methodName.indexOf(ApltContanst.BATCHINSERT) != -1){
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
			System.out.println(">>>>>>>> methodName : " + methodName);
			System.out.println(">>>>>>>> params : " + CommonUtils.toString(params));
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
				if(methodName.contains("batchUpdateXML")){
					if (isOneIndex.get().size() > 0) {
						for(String[] unqiue : isOneIndex.get()){
							if(unqiue.length == 1){
								appendBatchUpdateSql(javaFile, methodName, dto, javaFileName, tableName, unqiue[0]);
							}
						}
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
		selectBuffer.append(ApltContanst.TABS).append("<sql id=\"Base_Select_List\">").append(ApltContanst.NEWLINE)
		.append(ApltContanst.TABS).append(ApltContanst.TABS);
		//insert-column
		StringBuffer insertCBuffer = new StringBuffer();
		insertCBuffer.append(ApltContanst.TABS).append("<sql id=\"Base_Insert_Column\">").append(ApltContanst.NEWLINE);
		insertCBuffer.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(ApltContanst.NEWLINE);
		//insert-value
		StringBuffer insertVBuffer = new StringBuffer();
		insertVBuffer.append(ApltContanst.TABS).append("<sql id=\"Base_Insert_Values\">").append(ApltContanst.NEWLINE);
		insertVBuffer.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(ApltContanst.NEWLINE);
		
		javaFile.append(ApltContanst.TABS).append("<resultMap type=\"").append(ApltContanst.PACKAGE_MODEL).append(".").append(javaFileName)
				.append("\" id=\"BaseResultMap\">").append(ApltContanst.NEWLINE);
		for(int i = 0, size = fields.size(); i < size; i ++){
			String field = fields.get(i);
			String orginal = orginalColumns.get(field);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<result column=\"").append(orginal)
					.append("\" property=\"").append(field).append("\"/>").append(ApltContanst.NEWLINE);
			//select
			selectBuffer.append(orginal).append(",");
			if((i % 5) == 0 && i > 0 && i < (size - 1)){
				selectBuffer.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS);
			}
			//insert-column
			insertCBuffer.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("<if test=\"").append(field).append(" != null\">")
			.append(orginal).append(",</if>").append(ApltContanst.NEWLINE);
			//insert-value
			insertVBuffer.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("<if test=\"").append(field).append(" != null\">")
			.append("#{" + field + "}").append(",</if>").append(ApltContanst.NEWLINE);
		}
		javaFile.append(ApltContanst.TABS).append("</resultMap>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
		javaFile.append(selectBuffer.substring(0, selectBuffer.length() - 1));
		javaFile.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append("</sql>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
		javaFile.append(insertCBuffer).append(ApltContanst.TABS).append(ApltContanst.TABS).append("</trim>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</sql>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
		javaFile.append(insertVBuffer).append(ApltContanst.TABS).append(ApltContanst.TABS).append("</trim>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</sql>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
	}
	
	public static void appendSelectSql(StringBuffer javaFile, String methodName, List<String> params, String tableName,
			Map<String, String> orginalColumns, String javaFileName) {
		StringBuffer buffer = new StringBuffer();
		javaFile.append(ApltContanst.TABS).append("<select id=\"").append(methodName)
				.append("\" parameterType=\"java.util.HashMap\" resultMap=\"BaseResultMap\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("select").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<include refid=\"" + javaFileName + ".Base_Select_List\"/>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("from ").append(tableName).append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS).append("where ");
		for(int i = 0, size = params.size(); i < size; i ++){
			String param = params.get(i);
			if(i == 0){
				buffer.append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			} else {
				buffer.append(ApltContanst.AND).append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			}
			if((i % 3 == 0) && i > 0  && i < (size - 1)){
				buffer.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS);
			}
		}
		javaFile.append(buffer).append(getSqlSuffix(methodName)).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</select>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
	}
	
	public static void appendInsertSql(StringBuffer javaFile, String methodName, List<String> params, String tableName,
			Map<String, String> orginalColumns, String javaFileName) {
		javaFile.append(ApltContanst.TABS).append("<insert id=\"").append(methodName).append("\" parameterType=\"")
				.append(ApltContanst.PACKAGE_MODEL).append(".").append(javaFileName).append("\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("insert into ").append(tableName).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<include refid=\"" + javaFileName + ".Base_Insert_Column\"/>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("values ").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<include refid=\"" + javaFileName + ".Base_Insert_Values\"/>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</insert>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
	}
	
	public static void appendUpdateSql(StringBuffer javaFile, String methodName, CodeDto dto, List<String> params,
			String tableName, String javaFileName) {
		List<String> fields = dto.getFields();
		Map<String, String> orginalColumns = dto.getOrginalColumns();
//		List<StringBuffer> uniqueIndexs = dto.getUniqueIndexs();
		/*for(StringBuffer unqiue : uniqueIndexs){
			String[] strs = unqiue.toString().split("\\|");
			List<String> fields_bak = fields;
			fields_bak.removeAll(Arrays.asList(strs));
			StringBuffer buffer = new StringBuffer();
			javaFile.append(ApltContanst.TABS).append("<update id=\"").append(methodName).append("\" parameterType=\"")
			.append(ApltContanst.PACKAGE_MODEL).append(".").append(javaFileName).append("\">").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("update ").append(tableName).append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<trim prefix=\"set\" suffixOverrides=\",\">").append(ApltContanst.NEWLINE);
			for(int i = 0, size = fields_bak.size(); i < size; i ++){
				String field = fields_bak.get(i);
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("<if test=\"").append(field).append(" != null\">")
				.append(orginalColumns.get(field)).append(" = #{").append(field).append("},</if>").append(ApltContanst.NEWLINE);
			}
			
			for(int i = 0, length = strs.length; i < length; i ++){
				String s = strs[i];
				if(i == 0){
					buffer.append(orginalColumns.get(s)).append(" = #{").append(s).append("}");
				} else {
					buffer.append(ApltContanst.AND).append(orginalColumns.get(s)).append(" = #{").append(s).append("}");
				}
				
				if((i % 3 == 0) && i > 0  && i < (length - 1)){
					buffer.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS);
				}
			}
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("</trim>").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("where ").append(buffer).append(getSqlSuffix(methodName)).append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append("</update>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
		}*/
		System.out.println("update methodName : " + methodName);
		String[] strs = mapU.get(methodName);
		List<String> fields_bak = fields;
		fields_bak.removeAll(Arrays.asList(strs));
		StringBuffer buffer = new StringBuffer();
		javaFile.append(ApltContanst.TABS).append("<update id=\"").append(methodName).append("\" parameterType=\"")
				.append(ApltContanst.PACKAGE_MODEL).append(".").append(javaFileName).append("\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("update ").append(tableName).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<trim prefix=\"set\" suffixOverrides=\",\">").append(ApltContanst.NEWLINE);
		for(int i = 0, size = fields_bak.size(); i < size; i ++){
			String field = fields_bak.get(i);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("<if test=\"").append(field).append(" != null\">")
					.append(orginalColumns.get(field)).append(" = #{").append(field).append("},</if>").append(ApltContanst.NEWLINE);
		}

		for(int i = 0, length = strs.length; i < length; i ++){
			String s = strs[i];
			if(i == 0){
				buffer.append(orginalColumns.get(s)).append(" = #{").append(s).append("}");
			} else {
				buffer.append(ApltContanst.AND).append(orginalColumns.get(s)).append(" = #{").append(s).append("}");
			}

			if((i % 3 == 0) && i > 0  && i < (length - 1)){
				buffer.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS);
			}
		}
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("</trim>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("where ").append(buffer).append(getSqlSuffix(methodName)).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</update>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
	}
	
	public static void appendBatchInsertSql(StringBuffer javaFile, String methodName, CodeDto dto, String javaFileName,
			String tableName) {
		StringBuffer buffer = new StringBuffer();
		List<String> fields = dto.getFields();
		Map<String, String> orignalColumns = dto.getOrginalColumns();
		javaFile.append(ApltContanst.TABS).append("<insert id=\"").append(methodName).append("\" parameterType=\"java.util.List\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("insert into ").append(tableName).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("(");
		buffer.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("(");
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
				javaFile.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS);
				buffer.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS);
			}
		}
		javaFile.append(")").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("values").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">").append(ApltContanst.NEWLINE);
		javaFile.append(buffer).append(")").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("</foreach>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</insert>").append(ApltContanst.NEWLINE);
	}
	
	public static void appendBatchUpdateSql(StringBuffer javaFile, String methodName, CodeDto dto, String javaFileName,
			String tableName, String unique){
		List<String> fields = dto.getFields();
		Map<String, String> orginalColumns = dto.getOrginalColumns();
		String index_orginal = orginalColumns.get(unique);
		javaFile.append(ApltContanst.TABS).append("<update id=\"").append(methodName).append("\" parameterType=\"java.util.List\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("update ").append(tableName).append(" set").append(ApltContanst.NEWLINE);
		List<String> fields_bak = fields;
		fields_bak.remove(unique);
		for(int i = 0, size = fields_bak.size(); i < size; i ++){
			String field_bak = fields_bak.get(i);
			String orginal_colunm = orginalColumns.get(field_bak);
			if(i == (size - 1)){
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<trim prefix=\"").append(orginal_colunm)
				.append("=case\" suffix=\"end\">").append(ApltContanst.NEWLINE);
			} else {
				javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<trim prefix=\"").append(orginal_colunm)
				.append("=case\" suffix=\"end,\">").append(ApltContanst.NEWLINE);
			}
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS)
			.append("<foreach collection=\"list\" item=\"item\" index=\"index\">").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("<if test=\"item.")
			.append(field_bak).append(" != null\">").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("when ")
			.append(index_orginal).append(" = #{item.").append(unique).append("} then #{item.")
			.append(field_bak).append("}").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("</if>").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("</foreach>").append(ApltContanst.NEWLINE);
			javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("</trim>").append(ApltContanst.NEWLINE);
		}
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("where").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(index_orginal).append(" in").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("<foreach collection=\"list\" item=\"item\" index=\"index\"")
		.append(" separator=\",\" open=\"(\" close=\")\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(ApltContanst.TABS).append("#{item.").append(unique).append("}").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("</foreach>").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</update>").append(ApltContanst.NEWLINE);
	}
	
	public static void appendDeleteSql(StringBuffer javaFile, String methodName, List<String> params, String tableName,
			Map<String, String> orginalColumns, String javaFileName) {
		StringBuffer buffer = new StringBuffer();
		javaFile.append(ApltContanst.TABS).append("<delete id=\"").append(methodName).append("\" parameterType=\"")
				.append(ApltContanst.PACKAGE_MODEL).append(".").append(javaFileName).append("\">").append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("delete from ").append(tableName).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append("where").append(ApltContanst.NEWLINE);
		for(int i = 0, size = params.size(); i < size; i ++){
			String param = params.get(i);
			if(i == 0){
				buffer.append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			} else {
				buffer.append(ApltContanst.AND).append(orginalColumns.get(param)).append(" = #{").append(param).append("}");
			}
			if((i % 3 == 0) && i > 0  && i < (size - 1)){
				buffer.append(ApltContanst.NEWLINE).append(ApltContanst.TABS).append(ApltContanst.TABS);
			}
		}
		javaFile.append(ApltContanst.TABS).append(ApltContanst.TABS).append(buffer).append(getSqlSuffix(methodName)).append(ApltContanst.NEWLINE);
		javaFile.append(ApltContanst.TABS).append("</delete>").append(ApltContanst.NEWLINE).append(ApltContanst.NEWLINE);
	}
	
	public static String getSqlSuffix(String methodName){
		String flag = methodName.substring(methodName.length() - 1, methodName.length());
		String suffix = "";
		if(flag.equals("R")){
			suffix = " and valid_status = '0'";
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
			tabs_info += ApltContanst.TABS;
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

	public static class UniqueIndexDto{
		private String[] columns;

		public String[] getColumns() {
			return columns;
		}

		public void setColumns(String[] columns) {
			this.columns = columns;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			} else if (this.getClass() != obj.getClass()) {
				return false;
			} else if (obj instanceof UniqueIndexDto) {
				UniqueIndexDto dto = (UniqueIndexDto) obj;
				for(int i = 0; i < columns.length; i++){
					if (CommonUtils.notEquals(this.columns[i], dto.getColumns()[i])) {
						return false;
					}
				}
				return true;
			}
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			int hash = 0;
			for(int i = 0; i < columns.length; i++){
				hash |= columns[i].hashCode() << 4;
				hash |= hash << 8;
			}
			return hash;
		}
	}
}
