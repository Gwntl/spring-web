package org.mine.aplt.batch;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.mine.model.BatchJobDetailConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

/**  
 * @Description: 批量执行类
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
	
	public static volatile boolean exitFlag = false;
	
	private LinkedBlockingQueue<BatchJobDetailConf> queue = new LinkedBlockingQueue<>(1<<8);
	
	private Thread thread = null;
	
	private Lock lock = new ReentrantLock();
	/**
	 * 停止当前线程(使用变量加interrupt的方法)
	 */
	public void stop(){
		exitFlag = true;
		thread.interrupt();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(thread == null){
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(!Thread.currentThread().isInterrupted()){
						synchronized (queue) {
							try {
								BatchJobDetailConf conf = queue.take();
								call(conf);
							} catch (InterruptedException e) {
								//当标记为true时,表示时当前需要中断该线程.
								if(exitFlag){
									logger.warn("批量线程已被停止,若需要请重新启动.....");
									return;
								}
							}
						}
						try{
							//sleep被中断时,会将中断状态重置
							TimeUnit.SECONDS.sleep(1);
						} catch(InterruptedException e){
							logger.warn("(s)批量线程已被停止,若需要请重新启动.....");
							//中断状态被重置,需要重新调用中断方法,来停止while循环.
							thread.interrupt();
						}
					}
				}
			});
			thread.start();
		} else {
			if(thread.isAlive()){
				throw new RuntimeException("系统异常,请检查----");
			}
		}
	}
	
	public void restart(){
		try{
			lock.lock();
			if(thread.isAlive()){
				logger.warn("批量线程启动中,无需重启......");
			} else {
				thread.start();
			}
			exitFlag = false;
		}finally{
			lock.unlock();
		}
	}
	
	public void call(BatchJobDetailConf conf){
		//线程池的中断调用返回值Future中的cancle(boolean)方法, true-所有线程中断,false-当前线程中断. 
		//内部调用interrupt方法来实现中断,需要程序中获取中断状态来判断是否需要中断当前线程.
	}
}
