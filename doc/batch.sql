-- 该批量执行内容为: queue-group-task-job-step
-- 一个queue对应掉哟个group, 一个group对应多个task, 一个task对应多个job, 一个task对应多个step
-- 每个job之间可以并发执行, queue、group、task、step仅可以串行执行.
-- 最小单位为step,创建step的目的是为了防止一个task内存在一组任务(step)需要串行执行并且与当前运行的任务并发执行.

drop table if exists batch_queue_definition;
create table if not exists batch_queue_definition (
	queue_id bigint(20) not null comment "队列ID",
	queue_name varchar(32) comment "对列名称",
	queue_timingtask_flag int(1) not null default "1" comment "是否为定时任务. 0-是,1-否",
	queue_execution_num int(3) not null comment "队列执行序号",
	create_date varchar(8) not null comment "创建时间",
	vaild_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量执行队列定义表(串行)";
create unique index batch_queue_definition_index1 on batch_queue_definition(queue_id);
create index batch_queue_definition_index2 on batch_queue_definition(queue_timingtask_flag);

drop table if exists batch_group_definition;
create table if not exists batch_group_definition(
	group_id bigint(20) not null comment "任务执行组ID",
	group_name varchar(32) comment "任务执行组名称",
	group_associate_queue_id bigint(20) not null comment "任务组关联队列ID",
	group_execution_num int(3) not null comment "组别执行序号",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量任务组定义表(串行)";
create unique index batch_group_definition_index on batch_group_definition(group_id);
create index batch_group_definition_index1 on batch_group_definition(group_associate_queue_id);

drop table if exists batch_task_definition;
create table if not exists batch_task_definition(
	task_id bigint(20) not null comment "任务ID",
	task_name varchar(32) comment "任务名称",
	task_associate_group_id bigint(20) comment "关联任务组ID",
	task_skip_flag int(1) default "1" comment "可跳过标志. 0-是, 1-否",
	task_init_value varchar(500) comment "任务初始值",
	task_execution_num int(3) not null comment "任务执行序号",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量任务定义表";
create unique index batch_task_definition_index1 on batch_task_definition(task_id);
create index batch_task_definition_index2 on batch_task_definition(task_associate_group_id);

drop table if exists batch_task_execute;
create table if not exists batch_task_execute(
	execute_task_id bigint(20) not null comment "执行任务ID",
	execute_task_name varchar(32) comment "执行任务名称",
	execute_job_id bigint(20) comment "执行作业ID", 
	execute_job_num int(3) not null comment "作业执行序号",
	execute_job_one_time int(1) default "1" comment "是否一次性作业. 0-是, 1-否",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量任务运行表";
create index batch_task_execute_index1 on batch_task_execute(execute_task_id);

drop table if exists batch_job_definition;
create table if not exists batch_job_definition(
	job_id bigint(20) not null comment "作业ID",
	job_name varchar(32) comment "作业名称",
	job_associate_trigger_id varchar(20) comment "关联定时器ID",
	job_executor varchar(200) not null comment "作业执行器",
	job_init_value varchar(500) comment "作业初始化值",
	job_skip_flag int(1) not null default "1" comment "可跳过标志. 0-是, 1-否",
	job_run_muti_flag int(1) not null default "0" comment "多次执行标志, 0-是, 1-否",
	job_time_delay_flag int(1) default "1" comment "延时执行标志, 0-是, 1-否",
	job_time_delay_value varchar(8) comment "延时时间",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量作业定义表";
create unique index batch_job_definition_index1 on batch_job_definition(job_id);

drop table if exists batch_job_execute;
create table if not exists batch_job_execute(
	execute_job_id bigint(20) not null comment "执行作业ID",
	execute_job_name varchar(32) not null comment "执行作业名称",
	execute_step_id bigint(20) not null comment "关联步骤ID",
	execute_step_num int(3) not null comment "步骤执行序号",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量作业执行表(并发)";
create index batch_job_execute_index1 on batch_job_execute(execute_job_id);

drop table if exists batch_step_definition;
create table if not exists batch_step_definition(
	step_id bigint(20) not null comment "作业步骤ID",
	step_name varchar(32) comment "作业步骤名称",
	step_actuator varchar(200) not null comment "步骤执行器",
	step_log_mdc_value varchar(30) not null comment "日志文件名",
	step_init_value varchar(500) comment "初始化值",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否, D-已废弃",
	remark varchar(500) comment "备注"
) comment "批量执行步骤定义表(串行)";
create unique index batch_step_definition_index1 on batch_step_definition(step_id);


drop table batch_trigger_definition;
create table batch_trigger_definition(
	trigger_id bigint(20) NOT NULL COMMENT "触发器ID",
	trigger_name varchar(32) COMMENT "触发器名",
	trigger_start_time DATETIME COMMENT "触发器开始时间",
	trigger_end_time DATETIME COMMENT "触发器结束时间",
	trigger_crontrigger varchar(32) NOT NULL COMMENT "触发器设置",
	create_date varchar(8) not null comment "创建时间",
	vaild_status VARCHAR(1) NOT NULL default "0" COMMENT "有效状态",
	trigger_remark varchar(500) COMMENT "备注"
	) COMMENT "定时任务触发器定义表";
create UNIQUE INDEX batch_trigger_definition_index1 on batch_trigger_definition (trigger_id);

drop table if exists batch_task_execution_log_register;
create table if not exists batch_task_execution_log_register(
	task_execution_id bigint(20) not null comment "任务执行ID",
	task_job_id bigint(20) not null comment "执行作业ID",
	task_job_name varchar(32) not null comment "执行作业名称",
	task_job_associate_id bigint(20) not null comment "任务ID",
	task_start_time DATETIME COMMENT "开始时间",
	task_end_time DATETIME COMMENT "结束时间",
	task_job_status VARCHAR(12) COMMENT "任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	task_job_errmsg VARCHAR(500) COMMENT "任务错误信息",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "批量任务执行日志登记表";
create unique index batch_task_execution_log_register_index1 on batch_task_execution_log_register(task_execution_id);

drop table if exists batch_task_execution_log_history;
create table if not exists batch_task_execution_log_history(
	history_task_execution_id bigint(20) not null comment "任务执行ID",
	history_task_job_id bigint(20) not null comment "执行作业ID",
	history_task_job_name varchar(32) not null comment "执行作业名称",
	history_task_start_time DATETIME COMMENT "开始时间",
	history_task_end_time DATETIME COMMENT "结束时间",
	history_task_job_status VARCHAR(12) COMMENT "任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	history_task_job_errmsg VARCHAR(500) COMMENT "任务错误信息",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "批量任务执行日志历史表";
create unique index batch_task_execution_log_history_index1 on batch_task_execution_log_history(history_task_execution_id);

drop table if exists batch_step_execution_log_register;
create table if not exists batch_step_execution_log_register(
	step_execution_id bigint(20) not null comment "任务执行ID",
	step_job_id bigint(20) not null comment "执行步骤ID",
	step_job_name varchar(32) not null comment "执行步骤名称",
	step_start_time DATETIME COMMENT "开始时间",
	step_end_time DATETIME COMMENT "结束时间",
	step_job_status VARCHAR(12) COMMENT "步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	step_job_errmsg VARCHAR(500) COMMENT "步骤错误信息",
	step_associate_job_id bigint(20) comment "关联作业ID",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "批量步骤执行日志登记表";
create unique index batch_step_execution_log_register_index1 on batch_step_execution_log_register(step_execution_id);

drop table if exists batch_step_execution_log_history;
create table if not exists batch_step_execution_log_history(
	history_step_execution_id bigint(20) not null comment "任务执行ID",
	history_step_job_id bigint(20) not null comment "执行步骤ID",
	history_step_job_name varchar(32) not null comment "执行步骤名称",
	history_step_start_time DATETIME COMMENT "开始时间",
	history_step_end_time DATETIME COMMENT "结束时间",
	history_step_job_status VARCHAR(12) COMMENT "步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	history_step_job_errmsg VARCHAR(500) COMMENT "步骤错误信息",
	history_step_associate_job_id bigint(20) comment "关联作业ID",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "批量步骤执行日志历史表";
create unique index batch_step_execution_log_history_index1 on batch_step_execution_log_history(history_step_execution_id);

drop table if exists batch_timing_task_log_register;
create table if not exists batch_timing_task_log_register(
	timing_execution_id bigint(20) not null comment "定时任务执行ID",
	timing_job_id bigint(20) not null comment "定时作业ID",
	timing_job_name varchar(32) not null comment "定时作业名称",
	timing_associate_task_id bigint(20) not null comment "任务ID",
	timing_start_time DATETIME COMMENT "开始时间",
	timing_end_time DATETIME COMMENT "结束时间",
	timing_job_status VARCHAR(12) COMMENT "定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	timing_job_errmsg VARCHAR(500) COMMENT "定时任务错误信息",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "批量定时任务日志登记表";
create unique index batch_timing_task_log_register_index1 on batch_timing_task_log_register(timing_execution_id);


drop table if exists batch_timing_task_log_history;
create table if not exists batch_timing_task_log_history(
	history_timing_execution_id bigint(20) not null comment "定时任务执行ID",
	history_timing_job_id bigint(20) not null comment "定时作业ID",
	history_timing_job_name varchar(32) not null comment "定时作业名称",
	history_timing_associate_task_id bigint(20) not null comment "任务ID",
	history_timing_start_time DATETIME COMMENT "开始时间",
	history_timing_end_time DATETIME COMMENT "结束时间",
	history_timing_job_status VARCHAR(12) COMMENT "定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	history_timing_job_errmsg VARCHAR(500) COMMENT "定时任务错误信息",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "定时批量任务日志历史表";
create unique index batch_timing_task_log_history_index1 on batch_timing_task_log_history(history_timing_execution_id);


drop table if exists batch_timing_step_log_register;
create table if not exists batch_timing_step_log_register(
	step_execution_id bigint(20) not null comment "任务执行ID",
	step_job_id bigint(20) not null comment "执行步骤ID",
	step_job_name varchar(32) not null comment "执行步骤名称",
	step_start_time DATETIME COMMENT "开始时间",
	step_end_time DATETIME COMMENT "结束时间",
	step_job_status VARCHAR(12) COMMENT "步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	step_job_errmsg VARCHAR(500) COMMENT "步骤错误信息",
	step_associate_job_id bigint(20) comment "关联作业ID",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "定时批量步骤执行日志登记表";
create unique index batch_timing_step_log_register_index1 on batch_timing_step_log_register(step_execution_id);

drop table if exists batch_timing_step_log_history;
create table if not exists batch_timing_step_log_history(
	history_step_execution_id bigint(20) not null comment "任务执行ID",
	history_step_job_id bigint(20) not null comment "执行步骤ID",
	history_step_job_name varchar(32) not null comment "执行步骤名称",
	history_step_start_time DATETIME COMMENT "开始时间",
	history_step_end_time DATETIME COMMENT "结束时间",
	history_step_job_status VARCHAR(12) COMMENT "步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态",
	history_step_job_errmsg VARCHAR(500) COMMENT "步骤错误信息",
	history_step_associate_job_id bigint(20) comment "关联作业ID",
	create_date varchar(8) not null comment "创建时间",
	valid_status varchar(1) not null default "0" comment "有效状态. 0-是, 1-否",
	remark varchar(500) comment "备注"
) comment "定时批量步骤执行日志历史表";
create unique index batch_timing_step_log_history_index1 on batch_timing_step_log_history(history_step_execution_id);