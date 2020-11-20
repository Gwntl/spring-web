package org.mine.quartz.run;

import org.mine.quartz.dto.ExecuteTaskDto;

import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class JobQueueLogic {

	private JobTaskLogic taskLogic;

	private ExecuteTaskDto taskDto;

	public static ArrayBlockingQueue<queueMonitorDto> queueMonitor;

	public static Map<Long, LinkedList<String>> map = new ConcurrentHashMap<>();

	public static Vector<String> vector;

	public JobQueueLogic(JobTaskLogic taskLogic, ExecuteTaskDto taskDto) {
		this.taskLogic = taskLogic;
		this.taskDto = taskDto;
	}

	public void logic(Map<String, Object> jmap){
		//插入队列执行
//		taskLogic.logic(null);
	}


	static {
		queueMonitor = new ArrayBlockingQueue<>(1);
		vector = new Vector<>(1024);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					queueMonitorDto monitorDto = queueMonitor.take();
					//更新队列的执行状态.并通知下一个队列执行

				} catch (InterruptedException e) {
				}
			}
		}, "QUEUE_MONITOR_THREAD").start();
	}

	static class queueMonitorDto {

	}
}
