package com.java.base.thread;

public class ThreadB implements Runnable{

	private Service service;
	
	public ThreadB(Service service) {
//		super();
		this.service = service;
	}
	
	@Override
	public void run() {
//		service.notCurrSynPrintb(service, "bb");
//		service.printb("bb");
//		service.staticPrintB("bb");
//		service.noCurrStaticPrintB("bb");
		service.noSynPrintb("testbbb");
	}

}
