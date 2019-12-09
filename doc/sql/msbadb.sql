update batch_trigger_conf set trigger_crontrigger = '0/30 * * * * ?' where trigger_id = '1';
update batch_job_detail_conf set jobdetail_provider = 'org.mine.service.impl.async.DataPrintServiceImpl' where jobdetail_id = '1001';
UPDATE batch_job_group_conf set job_group_init_value = 'transName=testGroup' where job_group_id = '1';
update sequence set current_val = 0 where seq_name = 'batch_sequence_1';

select nextVal('batch_sequence_1');

insert into sequence (seq_name) VALUES ('batch_sequence_1');

INSERT INTO `batch_job_detail_conf`(`jobdetail_id`, `jobdetail_name`, `jobdetail_provider`, `jobdetail_init_value`, `jobdetail_group_id`, `jobdetail_actuator`, `vaild_status`, `jobdetail_remark`) VALUES (1001, '测试JOB', 'org.mine.service.impl.async.DataPrintServiceImpl', '', 1, '', '0', '');


delete from batch_queue_conf;
delete from batch_trigger_conf;
delete from batch_job_group_conf;
delete from batch_job_detail_conf;
delete from scheduler_runner_history;
