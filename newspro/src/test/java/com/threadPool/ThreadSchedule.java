package com.threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSchedule{
	final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	public static void main(String[] args) {
		TimerTask task1 = new TimerTask() {
			
			private int count = 0;
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("任务1执行时间："+sdf.format(new Date()));
				try {
					if(count == 2){
						Thread.sleep(5000);
					}
					count++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				throw new RuntimeException();
			}
		};
		TimerTask task2 = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("任务2执行时间："+sdf.format(new Date()));
			}
		};
		System.out.println("当前时间："+sdf.format(new Date()));
//		Timer timer = new Timer();
//		timer.schedule(task1, new Date(), 4000);
//		timer.schedule(task2, new Date(), 2000);
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
		executorService.scheduleAtFixedRate(task1, 0, 4000, TimeUnit.MILLISECONDS);
//		executorService.scheduleAtFixedRate(task2, 0, 2000, TimeUnit.MILLISECONDS);
	}

	public void lambdaMethod(){
		new Thread(()->{
			System.out.println("aaa");
		});
	}

}
