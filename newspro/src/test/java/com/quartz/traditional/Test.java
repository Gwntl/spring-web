package com.quartz.traditional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;

public class Test implements InitializingBean{

	@PostConstruct
	public void init_method(){
		System.out.println("init-method");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("after");
	}
}
