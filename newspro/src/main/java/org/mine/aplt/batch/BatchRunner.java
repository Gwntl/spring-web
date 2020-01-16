package org.mine.aplt.batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.mine.aplt.exception.GitWebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

/**  
 * @Description: 批量执行类
 * 批量执行分类: task-->job-->step
 * task:job = 1:n, job:step=1:n
 * @Title:  BatchRunner.java   
 * @Package org.mine.aplt.batch   
 * @author: wntl    
 * @date:   2019年12月2日 下午10:54:31   
 * @version V1.0 
 * @Copyright: 2019 org.mine.* newspro
 */  
@Repository
public class BatchRunner implements InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(BatchRunner.class);
	/**
	 * 任务执行队列
	 */
	private LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>(1);
	/**
	 * 待执行任务存储队列
	 */
	private LinkedList<Long> linkedList = new LinkedList<>();
	/**
	 * 任务执行线程
	 */
	private Thread thread = null;
	/**
	 * 启动下一个线程
	 */
	private Thread preparationNext = null;
	/**
	 * 重入锁
	 */
	private Lock executionLock = new ReentrantLock();
	/**
	 * 当前处理任务
	 */
	private List<Long> currentJobDetailId = new ArrayList<>();
	
	/**
	 * 重入锁
	 */
	private Lock lock = new ReentrantLock();
	/**
	 * 过滤对象
	 */
	private Set<Long> set = getRepeatObj();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(thread == null){
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try{
							Long conf = queue.take();
							batchRun(conf);
						} catch(InterruptedException e){
							logger.error("获取队列值时被中断......");
						}
					}
//					while(!Thread.currentThread().isInterrupted()){
//						try {
//							synchronized (queue) {
//								BatchJobDetailConf conf = queue.take();
//								batchRun(conf);
//							}
//						} catch (InterruptedException e) {
							//当标记为true时,表示时当前需要中断该线程.
//								if(exitFlag){
//									logger.warn("批量线程已被停止,若需要请重新启动.....");
//									return;
//								}
//						}
//						try{
//							//sleep被中断时,会将中断状态重置
//							TimeUnit.SECONDS.sleep(1);
//						} catch(InterruptedException e){
//							logger.warn("(s)批量线程已被停止,若需要请重新启动.....");
//							//中断状态被重置,需要重新调用中断方法,来停止while循环.
//							thread.interrupt();
//						}
//					}
				}
			});
			thread.start();
		}
		
		if(preparationNext == null){
			preparationNext = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try{
							executionLock.lock();
						} finally{
							executionLock.unlock();
						}
					}
				}
			});
		}
	}
	
	/**
	 * 获取过滤对象
	 * @return
	 */
	private Set<Long> getRepeatObj(){
		return new HashSet<>(2<<8);
	}
	
	/**
	 * 将指定顺序队列在批量任务初始化前便全部塞入队列中
	 * @param confs
	 */
	public void offerQueueInfos(List<Long> jobDetailIds){
		try {
			//使用可重入锁,使得只有在任务全部成功入队列时才执行批量任务
			lock.lock();
			//任务入队是否成功
			boolean offerResule = true;
			for(int i = 0, size = jobDetailIds.size(); i < size; i++){
				if(!offerResule){
					break;
				}
				Long jobDetailId = jobDetailIds.get(i);
				//如果已经存在队列中则不存入
				if(set.add(jobDetailId)){
					offerResule = linkedList.add(jobDetailId);
				} else {
					offerResule = false;
					logger.debug("当前任务[{}]重复,请去除重复队列!!", jobDetailId);
				}
			}
			//当入队失败时
			if(!offerResule){
				set = getRepeatObj();
				throw GitWebException.GIT_PUTQUEUE();
			} else {
				if(queue.size() <= 0 && linkedList.size() > 0){
					//当仅为一个任务时,不立即将任务移除,放至任务完成后移除.
					Long jobDetailId = linkedList.removeFirst();
					if (queue.offer(jobDetailId, 1, TimeUnit.SECONDS)) {
						logger.debug("当前任务[{}]进入执行批次时成功!!", jobDetailId);
					} else {
						logger.debug("当前任务[{}]进入执行批次时失败!!", jobDetailId);
					}
				}
			}
		} catch (InterruptedException e) {
			//当任务入队失败后,将任务存储队列置空.
			linkedList.clear();
			logger.error("加入执行队列时出错!!!" + GitWebException.getStackTrace(e));
			throw GitWebException.GIT_PUTQUEUE();
		} finally{
			lock.unlock();
		}
		
	}
	
	/**
	 * 单个批次执行
	 * @param conf
	 * @param insertLocation 插入位置
	 * @param isPriority 是否优先执行
	 * @param allowConcurrency 队列执行中时,是否允许并发
	 */
	public void offerQueueInfos(Long jobDetailId, Long insertLocation, boolean isPriority, boolean allowConcurrency){
		try {
			lock.lock();
			//当允许并发时,当前加入队列已存在则抛错
			if(allowConcurrency && !set.add(jobDetailId)){
				throw GitWebException.GIT1001("该任务[" + jobDetailId + "]已存在与执行队列....");
			}
			//当不允许并发时,队列正在运行则抛错
			else if(!allowConcurrency && set.size() > 0){
				throw GitWebException.GIT1001("任务正在运行中....");
			}
			
			if(insertLocation != null){
				int location = linkedList.indexOf(insertLocation);
				linkedList.add(location + 1, jobDetailId);
			} else{
				if(isPriority){
					linkedList.addFirst(jobDetailId);
				} else {
					linkedList.addLast(jobDetailId);
				}
			}
			queue.offer(linkedList.removeFirst(), 1, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {
			logger.error("加入执行队列时出错!!!" + GitWebException.getStackTrace(e));
			throw GitWebException.GIT_PUTQUEUE();
		} finally{
			lock.unlock();
		}
	}
	
	/**
	 * 批量执行入口
	 * @param conf
	 */
	public void batchRun(Long jobDetailId){
	}
	
	/**
	 * 停止/取消/跳过任务
	 * 当前运行线程停止运行需要业务代码里面通过实现可阻塞的方法或者Thread.isInterrupted()来实现.
	 * 仅仅调用cancel方法无法将线程停止,只是修改了线程的状态.
	 * @param jobDetailId
	 * @return
	 */
	public boolean cancelBatchTask(Long jobDetailId){
		return false;
	}
}
