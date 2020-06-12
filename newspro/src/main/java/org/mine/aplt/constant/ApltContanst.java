package org.mine.aplt.constant;

public class ApltContanst {
	/**
	 * QUEUE默认name前缀
	 */
	public static final String DEFAULT_QUEUE_NAME = "excutor_queue_";
	/**
	 * QUEUE默认组别
	 */
	public static final String DEFAULT_QUEUE_GROUP = "excutor_queue_groups";
	/**
	 * JOB默认name前缀
	 */
	public static final String DEFAULT_JOB_NAME = "excutor_job_";
	/**
	 * JOB默认组别
	 */
	public static final String DEFAULT_JOB_GROUP = "excutor_job_groups";
	/**
	 * TRIGGER默认name前缀
	 */
	public static final String DEFAULT_TRIGGER_NAME = "excutor_trigger_";
	/**
	 * TRIGGER默认组别
	 */
	public static final String DEFAULT_TRIGGER_GROUP = "excutor_trigger_groups";
	/**
	 * 异步任务执行结果-SUCCESS
	 */
	public static final String ASYNC_RESULT_SUCCESS = "0";
	/**
	 * 异步任务执行结果-FAIL
	 */
	public static final String ASYNC_RESULT_FAIL = "1";
	/**
	 * 定时任务
	 */
	public static final String JOB_HIST_TYPE_Q = "Q";
	/**
	 * 批量任务
	 */
	public static final String JOB_HIST_TYPE_B = "B";
	
	/**
	 * yyyyMMdd
	 * PATTERN
	 */
	public static final String PATTERNW = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
			+ "(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|"
			+ "(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]"
			+ "|[2468][048]|[3579][26])00))0229)$";
	
	/**
	 * yyyyMMdd HH:mm:ss
	 * PATTERNT
	 */
	public static final String PATTERNT = "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
			+ "(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|"
			+ "(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]"
			+ "|[2468][048]|[3579][26])00))0229))"
			+ "\\s([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * UNDERLINEPATTERNT
	 */
	public static final String UNDERLINEPATTERNT = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
			+ "-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|"
			+ "(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]"
			+ "|[2468][048]|[3579][26])00))-02-29)"
			+ "\\s([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
	
	/*------------------------DAO层代码生成工具相关常量BEGIN-----------------------*/
	/**
	 * app路径
	 * APPLICATION_PATH
	 */
	public static final String APPLICATION_PATH = System.getProperty("user.dir");
	public static final String MODEL_PATH = "/src/main/java/org/mine/model/";
	public static final String PACKAGE_MODEL = "org.mine.model";
	public static final String DAO_PATH = "/src/main/java/org/mine/dao/";
	public static final String PACKAGE_DAO = "org.mine.dao";
	public static final String DAOIMPL_PATH = "/src/main/java/org/mine/dao/impl/";
	public static final String PACKAGE_DAOIMPL = "org.mine.dao.impl";
	public static final String MAPPER_XML_PATH = "/src/main/resources/config/mybatis/mapper/";
	public static final String LINE_SUFFIX = ";\n";
	public static final String NEWLINE = "\n";
	public static final String TABS = "\t";
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
	public static final String BATCHDELETE = "batchDelete";
	public static final String SELECTALL = "selectAll";
	public static final String SELECTALLR = "selectAllR";
	public static final String SELECTALLL = "selectAllL";
	public static final String BATCHCOMMITSIZE = "batchCommitSize";
	public static final String AND = " and ";
	/*------------------------DAO层代码生成工具相关常量END-----------------------*/
}
