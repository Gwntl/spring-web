package org.mine.aplt.codeGenerator;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class CodeUtil {

	private static final Logger logger = LoggerFactory.getLogger(CodeUtil.class);
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/msbadb?serverTimezone=UTC";
	private static final String USERNAME = "msba";
	private static final String PASSWORD = "msba";
	public static Map<String, String> importResource = new ConcurrentHashMap<String, String>();
	public static ThreadLocal<Map<String, String>> dateField = new ThreadLocal<Map<String, String>>(){
		protected Map<String, String> initialValue() {
	        return new HashMap<String, String>();
	    }
	};
	
	private static Object obj = new Object();
	private static Connection conn = null;
	
	static{
		importResource.put("BigDecimal", "java.math.");
		try{
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e){
			logger.error("加载数据库插件失败!!!!");
		}
	}
	
	public static Connection getConnect(){
		try {
			if(conn == null){
				synchronized(obj){
					if(conn == null){
						conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("{}, 获取数据库连接失败!!!!", Thread.currentThread().getName());
		}
		return conn;
	}
	
	public static void destory(){
		try{
			if(conn != null){
				synchronized(obj){
					if(!conn.isClosed()){
						conn.close();
					}
				}
			}
		} catch(SQLException e){
			logger.error("{} ,关闭数据库连接失败!!!!", Thread.currentThread().getName());
		}
	}
	
	public static void tableInfos(String tableName){
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			
			logger.debug("{}", metaData.getClass());
			//获取Table表(不是系统表之类的), 去掉new String[]{"TABLE"}便是获取数据库中所有表
//			ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
			//获取指定表明
//			ResultSet resultSet = metaData.getTables("msbadb", null, tableName, new String[]{"TABLE"});
//			logger.debug("条数: {}", resultSet.getRow());
//			while(resultSet.next()){
//				logger.debug("数据库中表名为: {}, 注释为: {}", resultSet.getString(3), resultSet.getString(5));
//			}
			logger.debug("数据库中表名为: {}, 注释为: {}", tableName, getTableDefintion(tableName));
			//获取主键
//			ResultSet primary = metaData.getPrimaryKeys("msbadb", null, tableName);
//			while(primary != null && primary.next()){
//				for(int i =  1; i < 11; i++){
//					logger.debug("{} = {}", i, primary.getString(i));
//				}
//			}
			
//			ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, false, false);
//			ResultSetMetaData data = indexInfo.getMetaData();
//			while(indexInfo.next()){
//				for(int i = 1, count = data.getColumnCount(); i < count; i ++){
					//打印返回值
//					System.out.println(data.getColumnName(i) + " = " + indexInfo.getObject(i));
//				}
//				logger.debug("数据库中表名为: {}, 索引名: {}, 索引为: {}",
//						indexInfo.getString(3), indexInfo.getString(6), indexInfo.getString(9));
//			}

			ResultSet primaryKeys = metaData.getPrimaryKeys("msbadb", null, tableName);
			//TABLE_CAT
			//TABLE_SCHEM
			//TABLE_NAME
			//COLUMN_NAME
			//KEY_SEQ
			while (primaryKeys.next()) {
				logger.debug("TABLE_CAT : {}, TABLE_SCHEM : {}, TABLE_NAME : {}, COLUMN_NAME : {}, KEY_SEQ : {}",
						primaryKeys.getString(1), primaryKeys.getString(2), primaryKeys.getString(3), primaryKeys.getString(4), primaryKeys.getString(5));
			}
			
		} catch (SQLException e) {
			logger.error("获取数据库信息失败!!!!错误信息: {}", MineException.getStackTrace(e));
		}
	}
	
	/**
	 * 根据表名获取表注释
	 * @param tableName
	 * @return
	 */
	public static String getTableDefintion(String tableName){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?");
			PreparedStatement statement = conn.prepareStatement(buffer.toString());
			statement.setString(1, tableName);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			logger.error("error message : {}", GitWebException.getStackTrace(e));
		}
		return null;
	}
	
	/**
	 * 根据表名获取对应表信息
	 * @param tableName
	 * @return
	 */
	public static CodeDto columnInfos(String tableName, String catalog){
		CodeDto dto = null;
		try{
			dto = new CodeDto();
			Map<String, String> orginalColumns = dto.getOrginalColumns();
			//首字母大写
			List<String> columns = dto.getColumns();
			Map<String, Object> types = dto.getTypes();
			Map<String, Object> remarks = dto.getRemarks();
			Map<String, Object> defaults = dto.getDefaults();
			//首字母小写
			List<String> fields = dto.getFields();
			Map<String, Object> firstLowerTypes = dto.getTypes();
			Map<String, Object> firstLowerRemarks = dto.getRemarks();
			Map<String, Object> firstLowerDefaults = dto.getDefaults();
			//索引对象
			Map<String, StringBuffer> uniqueIndex = Collections.synchronizedMap(new LinkedHashMap<>());
			Map<String, StringBuffer> normalIndex = Collections.synchronizedMap(new LinkedHashMap<>());
			List<String> impoertResource = dto.getImportResource();
			HashSet<String> hashSet = new HashSet<>();
			//用来判断主键存在并且存在同一个唯一索引时, 报错
			HashSet<String> uniqueSet = new HashSet<>();
			
			DatabaseMetaData metaData = conn.getMetaData();
			
//			ResultSet resultSetTable = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
//			while(resultSetTable.next()){
//				String comment = resultSetTable.getString(5);
//				dto.setTableComment(comment);
//				logger.debug("数据库中表名为: {}, 注释为: {}", resultSetTable.getString(3), comment);
//			}
			String comment = getTableDefintion(tableName);
			dto.setTableComment(comment);
			logger.debug("数据库中表名为: {}, 注释为: {}", tableName, comment);
			
			
			ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
			while(resultSet.next()){
				String orginalStr = resultSet.getString(4);
				String column = CommonUtils.underLineToHump(orginalStr,false);

				if (CommonUtils.equalIgnoreCase(column, "innodbID")) {
					continue;
				}

				String field = CommonUtils.underLineToHump(orginalStr,true);
				String type = typeConversion(resultSet.getInt(5));
				if(resultSet.getInt(5) == 93){
					dateField.get().put(field, type);
				}
				String reamrk = resultSet.getString(12);
				String default_info = resultSet.getString(13);
				fields.add(field);
				columns.add(column);
				orginalColumns.put(field, orginalStr);
				types.put(column, type);
				remarks.put(column, reamrk);
				if(default_info != null){
					defaults.put(column, default_info);
				}
				//首字母小写的
				firstLowerTypes.put(field, type);
				firstLowerRemarks.put(field, reamrk);
				if(default_info != null){
					firstLowerDefaults.put(field, default_info);
				}
				String resource = importResource(type);
				if(hashSet.add(resource) && resource != null){
					impoertResource.add(resource);
				}
				logger.debug("数据库中表名为: {}, 列名为: {}, 列的数据类型: {}, 默认值: {}, 注释为: {}", 
						resultSet.getString(3), column, type, default_info, reamrk);
			}
			
			//查询出所有索引
			ResultSet indexInfo = metaData.getIndexInfo(null, null, tableName, false, false);
			while(indexInfo.next()){
				boolean indexType = indexInfo.getBoolean(4);
				String indexName = indexInfo.getString(6);
				String columnName = CommonUtils.underLineToHump(indexInfo.getString(9),true);

				if (CommonUtils.equalIgnoreCase(columnName, "innodbID")) {
					continue;
				}

				logger.debug("表名: {}, 索引类型: {}, 索引名: {}, 列名: {}",
						tableName, indexType ? "普通" : "唯一", indexName, columnName);
				if(indexType){
					//普通索引
					if(normalIndex.containsKey(indexName)){
						normalIndex.get(indexName).append("|").append(columnName);
					} else {
						StringBuffer buffer = new StringBuffer();
						buffer.append(columnName);
						normalIndex.put(indexName, buffer);
					}
				} else {
					//唯一索引
					if(uniqueIndex.containsKey(indexName)){
						uniqueIndex.get(indexName).append("|").append(columnName);
					} else {
						StringBuffer buffer = new StringBuffer();
						buffer.append(columnName);
						uniqueIndex.put(indexName, buffer);
					}
					if (!uniqueSet.add(uniqueIndex.get(indexName).toString())) {
						logger.error("唯一索引建立异常,请检查主键和唯一索引是否冲突!!!");
						throw GitWebException.GIT_DB_CREATE(tableName);
					}
				}
			}

			//增加List类型
			dto.setOrginalColumns(orginalColumns);
			dto.setColumns(columns);
			dto.setFields(fields);
			dto.setTypes(types);
			dto.setRemarks(remarks);
			dto.setDefaults(defaults);
			dto.setFirstLowerTypes(firstLowerTypes);
			dto.setFirstLowerRemarks(firstLowerRemarks);
			dto.setFirstLowerDefaults(firstLowerDefaults);
			dto.setUniqueIndexs(mapToList(uniqueIndex));
			dto.setNormalIndexs(mapToList(normalIndex));
		} catch(SQLException e){
			logger.error("获取数据库表信息失败!!!!错误信息: {}", MineException.getStackTrace(e));
		}
		return dto;
	}
	
	public static <T> List<T> mapToList(Map<String, T> map){
		if(CommonUtils.isEmpty(map)){
			return null;
		}
		List<T> list = new ArrayList<>();
		Iterator<Entry<String, T>> iterator =  map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, T> entry = iterator.next();
			list.add(entry.getValue());
		}
		logger.debug(">>>>>>list: {}, size : {}", CommonUtils.toString(list), list.size());
		return list;
	}
	
	/**
	 * 根据表结构生成MODEL类
	 * @param dto
	 * @param tableName
	 */
	public static void createJavaFile(CodeDto dto, String tableName){
		BufferedWriter writer = null;
		try{
			String javaFileName = CommonUtils.underLineToHump(tableName, false);
			File file = new File(ApltContanst.APPLICATION_PATH + ApltContanst.MODEL_PATH + javaFileName + ".java");
			if(!file.exists()){
				file.createNewFile();
			}
			StringBuffer javaFile = new StringBuffer();
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));
			writer.write(appendClassInfo(dto, javaFile, javaFileName, tableName));
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
			throw GitWebException.GIT1001("写文件失败------");
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 写入类信息
	 * @param dto
	 * @param javaFile
	 * @param javaFileName
	 * @param tableName
	 * @return
	 */
	public static String appendClassInfo(CodeDto dto, StringBuffer javaFile, String javaFileName, String tableName) {
		javaFile.append("package ").append(ApltContanst.PACKAGE_MODEL).append(ApltContanst.LINE_SUFFIX).append(ApltContanst.NEWLINE);
		CodeGeneration.appendClassAnnotation(javaFile, dto, javaFileName, tableName);
		javaFile.append("public class ").append(javaFileName).append(" {").append(ApltContanst.NEWLINE);
		CodeGeneration.createField(javaFile, dto);
		javaFile.append(ApltContanst.NEWLINE);
		CodeGeneration.addNoArguConstructor(javaFile, javaFileName, dto);
		javaFile.append(ApltContanst.NEWLINE);
		CodeGeneration.createSetterOrGetterField(javaFile, dto);
		javaFile.append(ApltContanst.NEWLINE);
		CodeGeneration.addToStringMethod(javaFile, javaFileName, dto.getFields());
		javaFile.append("}");
		return javaFile.toString();
	}
	
	
	/**
	 * 创建Dao相关文件
	 * @param dto
	 * @param tableName
	 * @param daoImplFlag true-创建DaoImpl false-创建Dao文件 
	 */
	public static void createDaoJavaFile(CodeDto dto, String tableName, boolean daoImplFlag){
		BufferedWriter writer = null;
		try{
			String javaFileName = CommonUtils.underLineToHump(tableName, false);
			File file = new File(ApltContanst.APPLICATION_PATH + (daoImplFlag ? ApltContanst.DAOIMPL_PATH : ApltContanst.DAO_PATH) + javaFileName
					+ (daoImplFlag ? "DaoImpl.java" : "Dao.java"));
			if(!file.exists()){
				file.createNewFile();
			}
			StringBuffer javaFile = new StringBuffer();
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));
			writer.write(appendDaoClassInfo(dto, javaFile, javaFileName, daoImplFlag));
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
			throw GitWebException.GIT1001("写文件失败------");
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 添加Dao文件信息
	 * @param dto
	 * @param javaFile
	 * @param javaFileName
	 * @param daoImplFlag
	 * @return
	 */
	public static String appendDaoClassInfo(CodeDto dto, StringBuffer javaFile, String javaFileName, boolean daoImplFlag){
		String javaDaoFileName = javaFileName + (daoImplFlag ? "DaoImpl" : "Dao");
		javaFile.append("package ").append(daoImplFlag ? ApltContanst.PACKAGE_DAOIMPL : ApltContanst.PACKAGE_DAO)
				.append(ApltContanst.LINE_SUFFIX).append(ApltContanst.NEWLINE);
		importSource(javaFile, javaFileName, dto.getImportResource(), daoImplFlag);
		CodeGeneration.appendClassAnnotation(javaFile, javaDaoFileName, "");
		javaFile.append(daoImplFlag ? "@Repository" : "").append(ApltContanst.NEWLINE);
		javaFile.append(daoImplFlag ? "public class " : "public interface ").append(javaDaoFileName);
		if(daoImplFlag){
			javaFile.append(" extends BaseDaoSupport implements ").append(javaFileName + "Dao");
		}
		javaFile.append(" {").append(ApltContanst.NEWLINE);
		CodeGeneration.addInterfaceMethod(javaFile, javaDaoFileName, javaFileName, dto, daoImplFlag);
		javaFile.append("}");
		return javaFile.toString();
	}
	
	/**
	 * 需要导入的资源包
	 * @param javaFile
	 * @param javaFileName
	 * @param importResource
	 * @param daoImplFlag
	 */
	public static void importSource(StringBuffer javaFile, String javaFileName, List<String> importResource, boolean daoImplFlag){
		for(String resource : importResource){
			javaFile.append("import ").append(resource).append(ApltContanst.LINE_SUFFIX);
		}
		javaFile.append("import java.util.List").append(ApltContanst.LINE_SUFFIX);
		javaFile.append("import ").append(ApltContanst.PACKAGE_MODEL).append(".").append(javaFileName).append(ApltContanst.LINE_SUFFIX);
		if(daoImplFlag){
			javaFile.append("import java.util.Map").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import java.util.HashMap").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import org.mine.aplt.exception.GitWebException").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import org.mine.aplt.support.dao.BaseDaoSupport").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import org.mine.aplt.support.dao.BatchOperator").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import org.mine.aplt.util.CommonUtils").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import ").append(ApltContanst.PACKAGE_DAO).append(".").append(javaFileName + "Dao").append(ApltContanst.LINE_SUFFIX);
			javaFile.append("import org.springframework.stereotype.Repository").append(ApltContanst.LINE_SUFFIX);
		}
		javaFile.append(ApltContanst.NEWLINE);
	}
	
	public static void createMyBatisMapperXml(CodeDto dto, String tableName){
		BufferedWriter writer = null;
		try{
			String javaFileName = CommonUtils.underLineToHump(tableName, false);
			File file = new File(ApltContanst.APPLICATION_PATH + ApltContanst.MAPPER_XML_PATH + javaFileName + "Mapper.xml");
			if(!file.exists()){
				file.createNewFile();
			}
			StringBuffer javaFile = new StringBuffer();
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));
			writer.write(appendMapperXmlInfo(dto, javaFile, javaFileName, tableName));
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
			throw GitWebException.GIT1001("写文件失败------");
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String appendMapperXmlInfo(CodeDto dto, StringBuffer javaFile, String javaFileName, String tableName){
		javaFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(ApltContanst.NEWLINE);
		javaFile.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">")
		.append(ApltContanst.NEWLINE);
		javaFile.append("<mapper namespace=\"").append(javaFileName).append("\">").append(ApltContanst.NEWLINE);
		CodeGeneration.addMapperXmlInfo(dto, javaFile, javaFileName, tableName);
		javaFile.append("</mapper>");
		return javaFile.toString();
	}
	
	//获取表列信息
	public static void columnInfos(){
		try{
			String sql = "select * from batch_trigger_conf";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSetMetaData metaData = preparedStatement.getMetaData();
			int count = metaData.getColumnCount();
			for(int i = 0; i < count; i++){
				logger.debug("表列名: {}", metaData.getColumnName(i + 1));
			}
		} catch(SQLException e){
			logger.error("获取数据库表信息失败!!!!错误信息: {}", MineException.getStackTrace(e));
		}
	}
	
	
	public static String typeConversion(int id){
		String type = "";
		switch (id) {
		case 93:
			type = "TIMESTAMP";
			break;
		case 12:
			type = "VARCHAR";
			break;
		case 3:
			type = "DECIMAL";
			break;
		case 4:
			type = "INTEGER";
			break;
		case -5:
			type = "BIGINT";
			break;
		default:
			type = id + "";
			break;
		}
		
		if(type.equalsIgnoreCase("VARCHAR") || type.equalsIgnoreCase("TIMESTAMP")){
			type = "String";
		} else if(type.equalsIgnoreCase("BIGINT")){
			type = "Long";
		} else if(type.equalsIgnoreCase("INTEGER")){
			type = "Integer";
		} else if(type.equalsIgnoreCase("DECIMAL")){
			type = "BigDecimal";
		}
		
		return type;
	}
	
	public static String importResource(String type){
		String str = null;
		if(importResource.containsKey(type)){
			str = importResource.get(type) + type;
		}
		return str;
	}
	
	public static void main(String[] args) {
		getConnect();
//		String[] tables = {"batch_queue_conf", "batch_trigger_conf", "batch_job_group_conf", 
//				"batch_job_detail_conf","scheduler_runner_history"};
//		String[] tables = { "batch_queue_definition", "batch_task_definition", "batch_job_definition",
//				"batch_step_definition", "batch_trigger_definition", "batch_task_execution_log_register",
//				"batch_task_execution_log_history", "batch_step_execution_log_register",
//				"batch_step_execution_log_history", "batch_timing_task_log_register", "batch_timing_task_log_history",
//				"batch_timing_job_log_register", "batch_timing_job_log_history"};
//		String[] tables = {"batch_task_execute", "batch_job_execute", "batch_queue_execute"};
//		String[] tables = {"db_optimistic_lock"};
		String[] tables = {"batch_step_log", "batch_step_log_history"};
//		String[] tables = {"batch_job_log", "batch_job_log_history"};
//		String[] tables = {"batch_task_log", "batch_task_log_history"};
		for(String tableName : tables){
			CodeDto codeDto = columnInfos(tableName, "msbadb");
			createJavaFile(codeDto, tableName);
			createDaoJavaFile(codeDto, tableName, false);
			createDaoJavaFile(codeDto, tableName, true);
			createMyBatisMapperXml(codeDto, tableName);
			CodeGeneration.clear();
		}
		
//		tableInfos("t_user");
//		getTableDefintion("t_user");
		destory();
	}
}
