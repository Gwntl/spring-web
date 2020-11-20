package org.mine.quartz.run.job;

import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.model.BatchJobLog;
import org.mine.model.BatchStepDefinition;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.dto.JobOperatorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class JobCall implements Runnable, InitializingBean {
	static final Logger logger = LoggerFactory.getLogger(JobCall.class);

	/**
	 * JOB实例执行信号.0-执行中, 1-停止, 2-取消.
	 */
	protected static Map<String, AtomicInteger> executingFlagMap = new ConcurrentHashMap<>(1 << 10);

	/**
	 * 判断指定的JOB实例是否处于执行中
	 * @return the executingFlagMap as executingFlagMap
	 */
	public static boolean isExecutingFlag(String jobInstance) {
		if (CommonUtils.isEmpty(jobInstance)) {
			throw GitWebException.GIT1002("JOB实例");
		}
		if (executingFlagMap.containsKey(jobInstance)) {
			return executingFlagMap.get(jobInstance).get() == 0;
		} else {
			logger.error("{}执行标志不存在, 或已被消除.", jobInstance);
			return false;
		}
	}

	/**
	* 获取当前执行状态
	* @param jobInstance
	* @return: java.lang.Integer
	* @Author: wntl
	* @Date: 2020/9/28
	*/
	public static Integer getExecutingValue(String jobInstance) {
		return executingFlagMap.get(jobInstance).get();
	}

	public Map<String, Object> stepExecutionInfo(BatchStepDefinition stepDefinition, ExecuteTaskDto taskDto) {
		Map<String, Object> map = new HashMap<>();
		CommonUtils.mergeMaps(map, taskDto.getQueueInitValue(), taskDto.getTaskInitValue(), taskDto.getJobInitValue(), taskDto.getStepInitValue());
		map.put("STEP_ID", stepDefinition.getStepId());
		map.put("JOB_ID", taskDto.getJobId());
		map.put("TASK_ID", taskDto.getTaskId());
		map.put("QUEUE_ID", taskDto.getQueueId());
		map.put("EXECUTION_INSTANCE", taskDto.getExecutionInstance());
		map.put("DEFAULT", "0");
		map.put("TRAN_DATE", CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		return map;
	}
	/**
	* 执行下一个step
	* @param executionInstance JOB执行实例
	* @param deliverValueMap 输入值
	* @return: void
	* @Author: wntl
	* @Date: 2020/9/27
	*/
	abstract void poolExecute(String executionInstance, Map<String, Object> deliverValueMap);
	/**
	* 停止JOB
	* @param jobInstance JOB执行实例
	* @return: boolean
	* @Author: wntl
	* @Date: 2020/9/28
	*/
	abstract JobOperatorDto stopJob(String jobInstance);
	/**
	* 取消运行中的job, 需执行器中响应中断.</br>
	* 当执行器中使用sleep方法时, 在捕获异常之后若需要执行其他逻辑时, 需要再执行一次 interrupt()方法. 由于sleep会清除中断状态.</br>
	* 需考虑分段事务提交.</br>
	* @param jobInstance JOB执行实例
	* @return: boolean JOB实例
	* @Author: wntl
	* @Date: 2020/9/27
	*/
	abstract JobOperatorDto cancelJob(String jobInstance);
	/**
	* 重启Job
	* @param jobInstance JOB执行实例
	* @return: boolean
	* @Author: wntl
	* @Date: 2020/9/27
	*/
	abstract boolean restartJob(String jobInstance);
	/**
	* 取消时数据处理.
	* @param executionInstance JOB执行实例
	* @return: org.mine.model.BatchJobLog
	* @Author: wntl
	* @Date: 2020/9/27
	*/
	protected BatchJobLog dataOperatorHandling(String executionInstance) {
		return null;
	}

	/**
	 * 内部执行节点
	 * @author: wntl
	 * @date: 2020年4月29日 下午8:06:05
	 */
	static class InnerStepNode {
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
	static class InnerThread implements Callable<CallableResultDto> {
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
				GitContext.init(MDCCache.get(obj.get("STEP_ID").toString()));
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
