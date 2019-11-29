package com.quartz.job;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import com.java.base.thread.GetSynchronizedLock;

@Repository
public class JobDemo01 {
	public void sayHello() {
		try{
			System.out.println(new Date() + " -> 进入Demo01");
//			GetSynchronizedLock.lock.lock();
			System.out.println(new Date() + " -> Demo01获取锁");
			String s = new Date() + " -> Hello, 我是任务 1";
			GetSynchronizedLock.lockMaps.put("demo01", s);
//			GetSynchronizedLock.condition.signal();
			System.out.println(s);
		} finally{
//			GetSynchronizedLock.lock.unlock();
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/quartz/spring-test.xml");
		System.out.println(context.getBean("transActionTemplate").getClass());
	}
}
