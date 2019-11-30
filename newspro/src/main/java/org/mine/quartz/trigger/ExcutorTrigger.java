package org.mine.quartz.trigger;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineBizException;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobGroupConfDao;
import org.mine.dao.BatchQueueConfDao;
import org.mine.dao.BatchTriggerConfDao;
import org.mine.model.BatchJobGroupConf;
import org.mine.model.BatchQueueConf;
import org.mine.model.BatchTriggerConf;
import org.mine.quartz.ExcutorBase;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ExcutorTrigger extends CronTriggerImpl implements InitializingBean{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1379631809538539447L;
	
	private static final Logger logger = LoggerFactory.getLogger(ExcutorTrigger.class);
	
	@Autowired
	private BatchQueueConfDao queueConfDao;
	@Autowired
	private BatchTriggerConfDao triggerConfDao;
	@Autowired
	private BatchJobGroupConfDao jobGroupConfDao;

	/* 
	 * 此处将quartz需要的定时作业配置在数据库中, 每次加载时,从数据库中加载定时作业.
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		loadTimingData();
	}
	
	/**
	 * 加载定时任务
	 * @return
	 */
	public Map<JobDetailImpl, ExcutorTrigger> loadTimingData(){
//		Map<JobDetailImpl, ExcutorTrigger> map = null;
		try{
//			map = new LinkedHashMap<>();
			//查询出需定时执行的任务
			List<BatchQueueConf> queueConfs = queueConfDao.selectAll1(JobExcutorEnum.AUTO_RUN);
			for(int i = 0, size = queueConfs.size(); i < size; i++){
				BatchQueueConf queueConf = queueConfs.get(i);
				Long jobGroupId = queueConf.getQueueJobGroupId();
				BatchJobGroupConf groupConf = jobGroupConfDao.selectOne1(jobGroupId);
				
				if(groupConf.getJobGroupNumber() > 1){
					throw GitWebException.GIT_CONFIGUARTION("batch_job_group_conf", "job_group_number", "请联系技术人员或业务人员修复!!!");
				}
				
				if(CommonUtils.isEmpty(groupConf.getJobGroupTriggerId())){
					throw GitWebException.GIT1002("batch_job_group_cof.job_group_trigger_id 触发器ID");
				}
				
				JobDetailImpl detailImpl = new JobDetailImpl();
				detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + groupConf.getJobGroupId());
				detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
				detailImpl.setJobClass(ExcutorBase.getExcutorJob(groupConf.getJobGroupIsconcurrent()));
				JobDataMap dataMap = new JobDataMap();
				dataMap.put("savelog", groupConf.getJobGroupSavelog());
				dataMap.put("group_id", jobGroupId);
				detailImpl.setJobDataMap((JobDataMap)CommonUtils.initMapValue(dataMap, groupConf.getJobGroupInitValue()));
				//确保job执行完后不从jobstore中移除
				detailImpl.setDurability(true);
				
				BatchTriggerConf triggerConf = triggerConfDao.selectOne1R(groupConf.getJobGroupTriggerId());
				if(CommonUtils.isEmpty(triggerConf.getTriggerCrontrigger())){
					throw GitWebException.GIT1002("batch_trigger_conf.trigger_crontrigger 触发器参数");
				}
				ExcutorTrigger trigger = new ExcutorTrigger();
				trigger.setName(ApltContanst.DEFAULT_TRIGGER_NAME + triggerConf.getTriggerId());
				trigger.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
				trigger.setCronExpression(triggerConf.getTriggerCrontrigger());
				if(CommonUtils.isNotEmpty(triggerConf.getTriggerStartTime())){
					trigger.setStartTime(CommonUtils.stringToDate(triggerConf.getTriggerStartTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				if(CommonUtils.isNotEmpty(triggerConf.getTriggerEndTime())){
					trigger.setEndTime(CommonUtils.stringToDate(triggerConf.getTriggerEndTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				/*
				 * Spring当使用bean配置触发器的形式的时候,将jobdetail放至trigger的dataMap中.
				 * 当注册时若发现未配置Job信息, Spring会从Map中自动获取jobDetail的值,注册进Schedule中.
				 * */
//				getJobDataMap().put("jobDetail", detail);
				ExcutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detailImpl, trigger);
				
//				map.put(detailImpl, trigger);
			}
		} catch(ParseException | SchedulerException e){
			logger.error("加载Quartz信息失败: {}" + MineBizException.getStackTrace(e));
		}
		
		return null;
	}
}
