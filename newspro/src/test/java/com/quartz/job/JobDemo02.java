package com.quartz.job;

import java.util.Date;

import com.java.base.thread.GetSynchronizedLock;

public class JobDemo02 {
	public void sayHello() {
		try{
//			System.out.println(new Date() + " -> 进入Demo02");
//			GetSynchronizedLock.lock.lock();
			System.out.println(new Date() + " -> Demo02获取锁");
			String s = new Date() + " -> Hello, 我是任务 2";
			GetSynchronizedLock.lockMaps.put("demo02", s);
//			GetSynchronizedLock.condition.signal();
			System.out.println(s);
		} finally{
//			GetSynchronizedLock.lock.unlock();
		}
	}
}
