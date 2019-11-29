package com.quartz.job;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTestDemo01 {
	@Test
	public void test() {
//		fail("Not yet implemented");
//		ApplicationContext context = new ClassPathXmlApplicationContext("src/test/resource/config/quartz/quartz.xml");
		
	}
	
	 public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/quartz/spring-test.xml");
	}

}
