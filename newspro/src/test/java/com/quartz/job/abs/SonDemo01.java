package com.quartz.job.abs;

public class SonDemo01 extends AbstractDemo{

	public SonDemo01() {
		super();
	}
	
	public SonDemo01(String className, String classPath) {
		super(className, classPath);
	}

	/**
	 * <p>Description: </p>
	 * <p>Title: test</p>
	 * @see com.quartz.job.abs.AbstractDemo#test()
	 */
	@Override
	public void test() {
		System.out.println("==============");
		super.test();
		System.out.println("=====end=======");
	}
	
	public static void main(String[] args) {
		SonDemo01 demo01 = new SonDemo01();
		demo01.test();
	}
}
