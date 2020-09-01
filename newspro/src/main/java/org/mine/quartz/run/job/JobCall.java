package org.mine.quartz.run.job;

import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.model.BatchStepDefinition;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.JobTaskCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class JobCall implements Runnable, InitializingBean {
	static final Logger logger = LoggerFactory.getLogger(JobTaskCallable.class);
	
	public Map<String, Object> stepExecutionInfo(BatchStepDefinition stepDefinition, ExecuteTaskDto taskDto) {
		Map<String, Object> map = new HashMap<>();
		CommonUtils.mergeMaps(map, taskDto.getQueueInitValue(), taskDto.getTaskInitValue(), taskDto.getJobInitValue(), taskDto.getStepInitValue());
		map.put("STEPID", stepDefinition.getStepId());
		map.put("JOBID", taskDto.getJobId());
		map.put("TASKID", taskDto.getTaskId());
		map.put("QUEUEID", taskDto.getQueueId());
		map.put("EXECUTIONINSTANCE", taskDto.getExecutionInstance());
		map.put("DEFAULT", "0");
		map.put("TRANDATE", CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		return map;
	}
	
	abstract void poolExecute(String jobHistoryId, Map<String, Object> deliverValueMap);
	
	/**
	 * 内部执行节点
	 * @author: wntl
	 * @date: 2020年4月29日 下午8:06:05
	 */
	class InnerStepNode{
		/**
		 * 执行器
		 * @Fields executor
		 */
		BaseServiceTaskletExecutor executor;
		/**
		 * 分组信息
		 * @Fields list
		 */
		List<Map<String, Object>> list;
		
		InnerStepNode(BaseServiceTaskletExecutor executor, List<Map<String, Object>> list) {
			this.executor = executor;
			this.list = list;
		}
		/**
		 * 执行器
		 * @return the executor
		 */
		public BaseServiceTaskletExecutor getExecutor() {
			return executor;
		}
		/**
		 * 执行器
		 * @param executor the executor to set
		 */
		public void setExecutor(BaseServiceTaskletExecutor executor) {
			this.executor = executor;
		}
		/**
		 * 分组信息
		 * @return the list
		 */
		public List<Map<String, Object>> getList() {
			return list;
		}
		/**
		 * 分组信息
		 * @param list the list to set
		 */ 
		public void setList(List<Map<String, Object>> list) {
			this.list = list;
		}
		@Override
		public String toString() {
			return "InnerNode [executor=" + executor.getClass() + ", list=" + CommonUtils.toString(list) + "]";
		}
		
	}
	
	/**
	 * 内部执行器
	 * @ClassName: InnerThread
	 * @author: wntl
	 * @date: 2020年4月29日 下午8:36:27
	 */
	class InnerThread implements Callable<CallableResultDto>{
		/**
		 * 执行器
		 * @Fields executor
		 */
		BaseServiceTaskletExecutor executor;
		/**
		 * 执行参数
		 * @Fields obj
		 */
		Map<String, Object> obj;
		
		InnerThread(BaseServiceTaskletExecutor executor, Map<String, Object> obj) {
			this.executor = executor;
			this.obj = obj;
		}
		@Override
		public CallableResultDto call() throws Exception {
			CallableResultDto dto = new CallableResultDto();
			try{
				GitContext.init(MDCCache.get(obj.get("STEPID").toString()));
				dto.setMap(executor.handler(obj));
				dto.setResult(true);
			} catch(Throwable e) {
				logger.error("error message : {}", MineException.getStackTrace(e));
				dto.setResult(false);
				dto.setMseeage(e.getMessage());
			}
			return dto;
		}
	}
	
}
