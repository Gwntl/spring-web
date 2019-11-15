package com.threadPool;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/quartz/asyncExcutor.xml"})
public class ThreadPoolExcutorsTest {

	@Autowired
	private ThreadPoolTaskExecutor excutor;
	
	@Test
	public void test() {
//		fail("Not yet implemented");
		String s = "ssss";
		if(s.equalsIgnoreCase("ssss")){
			CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("异步任务处理成功");
				}
			}, excutor);
			
			future.thenAccept(new Consumer<Void>() {
				
				@Override
				public void accept(Void t) {
					// TODO Auto-generated method stub
					System.out.println("推送处理完成通知");
				}
			});
		}
		System.out.println("处理完成");
	}

}
