drop table batch_queue_conf;
create table batch_queue_conf(
	queue_id BIGINT(8) NOT NULL COMMENT '队列ID',
	queue_name VARCHAR(32) COMMENT '队列名',
	queue_job_group_id BIGINT(8) NOT NULL COMMENT 'JOB作业组ID',
	queue_auto_flag INT(1) NOT NULL DEFAULT '0' COMMENT '是否自动执行 0-是 1-否',
	vaild_status VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '有效状态',
	queue_remark VARCHAR(500) COMMENT '备注'
) COMMENT '执行队列定义表';
create UNIQUE INDEX batch_queue_conf_index1 ON batch_queue_conf(queue_id);
create INDEX batch_queue_conf_index2 ON batch_queue_conf(queue_auto_flag);


drop table batch_trigger_conf;
create table batch_trigger_conf(
	trigger_id BIGINT(8) NOT NULL COMMENT '触发器ID',
	trigger_name varchar(32) COMMENT '触发器名',
	trigger_start_time DATETIME COMMENT '触发器开始时间',
	trigger_end_time DATETIME COMMENT '触发器结束时间',
	trigger_crontrigger varchar(32) NOT NULL COMMENT '触发器设置',
	trigger_maintenance_date VARCHAR(8) NOT NULL COMMENT '维护日期',
	vaild_status VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '有效状态',
	trigger_remark varchar(500) COMMENT '备注'
	) COMMENT '定时任务触发器定义表';

-- drop index batch_trigger_conf_index1 on batch_trigger_conf;
create UNIQUE INDEX batch_trigger_conf_index1 on batch_trigger_conf (trigger_id);


-- 作业组中是否并发是根据组来定义的, 当组内存在多个任务数的时候,为一个存在顺序执行关系的队列.
-- 定时任务组中暂时只支持存在一个任务数.并发的作业组是针对于定时任务来确定的.
DROP TABLE batch_job_group_conf;
CREATE TABLE batch_job_group_conf(
	job_group_id BIGINT(8) NOT NULL COMMENT 'JOB作业组ID',
	job_group_name VARCHAR(32) COMMENT 'JOB作业组名称',
	job_group_savelog VARCHAR(1) DEFAULT '0' COMMENT '是否记录日志 0-是 1-否',
	job_group_jobPath VARCHAR(100) NOT NULL COMMENT '该JOB执行类',
	job_group_init_value VARCHAR(400) COMMENT '初始化值,定时任务时设置',
	job_group_trigger_id BIGINT(8) COMMENT '触发器ID,定时任务时设置',
	job_group_rerun VARCHAR(1) COMMENT '是否支持重跑 0-是 1-否',
	job_group_number INT(2) COMMENT '任务数',
	job_group_maintenance_date VARCHAR(8) COMMENT 'JOB作业组维护日期',
	vaild_status VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '有效状态',
	job_group_remark VARCHAR(500) COMMENT '备注'
) COMMENT 'JOB作业组定义表';

CREATE UNIQUE INDEX batch_job_group_conf_index1 on batch_job_group_conf(job_group_id);

DROP TABLE batch_job_detail_conf;
CREATE TABLE batch_job_detail_conf(
	jobdetail_id BIGINT(8) NOT NULL COMMENT 'JOB任务作业ID',
	jobdetail_name VARCHAR(32) COMMENT 'JOB作业名称',
	jobdetail_provider VARCHAR(64) COMMENT 'JOB作业执行类信息',
	jobdetail_init_value VARCHAR(400) COMMENT '初始化值',
	jobdetail_group_id BIGINT(8) NOT NULL COMMENT 'JOB作业组ID',
	jobdetail_actuator VARCHAR(100) NOT NULL COMMENT '执行器',
	vaild_status VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '有效状态',
	jobdetail_remark VARCHAR(500) COMMENT '备注'
) COMMENT 'JOb任务作业定义';
create UNIQUE INDEX batch_job_detail_conf_index1 on batch_job_detail_conf (jobdetail_id);
create INDEX batch_job_detail_conf_index2 on batch_job_detail_conf (jobdetail_group_id);

drop table scheduler_runner_history;
create table scheduler_runner_history(
	runner_id BIGINT(8) NOT NULL COMMENT '运行序号',
	job_id BIGINT(8) NOT NULL COMMENT 'JOB任务ID',
	start_time DATETIME COMMENT '开始时间',
	end_time DATETIME COMMENT '结束时间',
	job_status VARCHAR(12) COMMENT 'JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态',
	job_errmsg VARCHAR(500) COMMENT 'JOB任务错误信息',
	job_maintenance_date VARCHAR(8) COMMENT '运行日期',
	vaild_status VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '有效状态'
)COMMENT '队列运行历史';
create unique index scheduler_runner_history_index1 on scheduler_runner_history(runner_id,job_id);
create index scheduler_runner_history_index2 on scheduler_runner_history(job_id, job_status,job_maintenance_date);

-- 更改表名注释
#alter table batch_run_jobdetail COMMENT 'JOb任务实体定义';
-- 更改字段类型
#alter table batch_run_queue modify COLUMN queue_end_time DATETIME;
-- 删除索引
#drop index batch_run_queue_index1 on batch_run_queue;


















-- 序列表
DROP TABLE IF EXISTS sequence;
CREATE TABLE sequence(
	seq_name VARCHAR(32) NOT NULL COMMENT '序列名称',
	current_val BIGINT(12) DEFAULT '0' COMMENT '当前值',
	increment_val BIGINT(4) DEFAULT '1' COMMENT '增长值',
	min_val BIGINT(12) DEFAULT '0' NOT NULL COMMENT '最小值',
	max_val BIGINT(12) DEFAULT '999999999999' NOT NULL COMMENT '最大值',
	PRIMARY KEY (seq_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
CREATE UNIQUE INDEX sequence_index_1 on sequence(seq_name);

-- 删除函数
drop function if exists nextVal;
-- 创建函数
CREATE FUNCTION nextVal(sequence_name VARCHAR(32))
RETURNS BIGINT
LANGUAGE SQL
DETERMINISTIC MODIFIES SQL DATA
BEGIN
DECLARE last_val BIGINT(12);
DECLARE max_val BIGINT(12);
DECLARE incr_val BIGINT(12);
DECLARE cur_val BIGINT(12);

set incr_val = (select increment_val from sequence where seq_name = sequence_name);
set max_val = (select max_val from sequence where seq_name = sequence_name);
set last_val = (select current_val from sequence where seq_name = sequence_name);
set cur_val = last_val + incr_val;
update sequence set current_val = cur_val where seq_name = sequence_name;
IF(cur_val >= max_val) THEN
	update sequence set current_val = min_val where seq_name = sequence_name;
END IF;
RETURN cur_val;
END;





