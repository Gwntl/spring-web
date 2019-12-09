package com.java.base.thread;

public class ThreadA implements Runnable{

	private Service service;
	
	public ThreadA(Service service) {
//		super();
		this.service = service;
	}
	
	@Override
	public void run() {
		
//		service.notCurrSynPrinta("aa");
//		service.printa("aa");
//		service.staticPrintA("aa");
//		service.noCurrStaticPrintA("aa");
//		service.noSynPrinta("testaaa");
	}
}
