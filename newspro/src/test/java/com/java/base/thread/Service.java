package com.java.base.thread;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Service {
	private String name;
	
	private static String staticName;
	
	private static int staticCount = 0;
	
	public void noSynPrinta(String name){
		this.name = name;
		for(int i = 0; i < 20; i++){
			try {
				DeamonThreadTest.blockingQueue.put(this.name + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("noSyn a, " + Thread.currentThread().getName() + ", name = " + this.name + i);
		}
	}
	
	public void noSynPrintb(String name){
		this.name = name;
		for(int i = 0; i < 20; i++){
			try {
				DeamonThreadTest.blockingQueue.put(this.name + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("noSyn b, " + Thread.currentThread().getName() + ", name = " + this.name + i);
		}
	}
	
	public synchronized void printa(String name){
		this.name = name;
		for(int i = 0; i < 20; i++){
			staticCount++;
			System.out.println("Syn a, " + Thread.currentThread().getName() + ", name = " + this.name + i
					+ ", staticCount : " + staticCount);
		}
	}
	
	public synchronized void printb(String name){
		this.name = name;
		staticName = name;
		for(int i = 0; i < 20; i++){
			System.out.println("Syn b, " + Thread.currentThread().getName() + ", name = " + this.name + i
					+ ", staticName : " + staticName);
		}
	}
	public void notCurrSynPrinta(String name){
		synchronized(obj){
			this.name = name;
			staticName = name;
			for(int i = 0; i < 10; i++){
				System.out.println("notCurrSyn a, " + Thread.currentThread().getName() + ", name = " 
			+ this.name + i + ", staticName : " + staticName);
			}
		}
	}
	
	private Object obj = new Object();
	public void notCurrSynPrintb(Service service, String name){
		synchronized(obj){
			this.name = name;
			for(int i = 0; i < 10; i++){
				System.out.println("notCurrSyn b, " + Thread.currentThread().getName() + ", name = " + this.name + i);
			}
		}
	}
	
	public static synchronized void staticPrintA(String name){
		staticName = name;
		for(int i = 0; i < 10; i++){
			staticCount++;
			System.out.println("static a, " + Thread.currentThread().getName() + ", name = " + staticName + i
					+ ", staticCount : " + staticCount);
		}
	}
	
	public static synchronized void staticPrintB(String name){
		staticName = name;
		for(int i = 0; i < 10; i++){
			staticCount++;
			System.out.println("static b, " + Thread.currentThread().getName() + ", name = " + staticName + i
					+ ", staticCount : " + staticCount);
		}
	}
	
	public static void noCurrStaticPrintA(String name){
		synchronized(Service.class){
			staticName = name;
			for(int i = 0; i < 10; i++){
				staticCount++;
				System.out.println("static a, " + Thread.currentThread().getName() + ", name = " + staticName + i
						+ ", staticCount : " + staticCount);
			}
		}
	}
	
	public static void noCurrStaticPrintB(String name){
		synchronized(Service.class){
			staticName = name;
			for(int i = 0; i < 10; i++){
				staticCount++;
				System.out.println("static b, " + Thread.currentThread().getName() + ", name = " + staticName + i
						+ ", staticCount : " + staticCount);
			}
		}
	}
	
	public static void main(String[] args) throws ParseException {
		String time = "20191106154233";
		System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").parse(time));
		System.out.println(new java.util.Date());
		
	}
}
