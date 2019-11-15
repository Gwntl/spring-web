package com.quartz.job;

import java.util.Date;

public class JobDemo01 {
	public void sayHello() {
		System.out.println(new Date() + " -> Hello, 我是任务 1");
	}
}
