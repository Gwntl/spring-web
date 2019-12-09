select * from batch_queue_conf;

select * from batch_trigger_conf t;

select * from batch_job_group_conf;

select * from batch_job_detail_conf;

select * from sequence;

UPDATE batch_job_group_conf set job_group_init_value = 'transName=testGroup' where job_group_id = '1';
update sequence set current_val = 0 where seq_name = 'batch_sequence_1';

select nextVal('batch_sequence_1');


insert into sequence (seq_name) VALUES ('batch_sequence_1');

delete from batch_queue_conf;
delete from batch_trigger_conf;
delete from batch_job_group_conf;
delete from batch_job_detail_conf;