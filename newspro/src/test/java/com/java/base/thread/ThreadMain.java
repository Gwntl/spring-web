package com.java.base.thread;

public class ThreadMain {
	public static void main(String[] args) {
//		Service service = new Service();
//		
//		ThreadA a = new ThreadA(service);
//		ThreadB b = new ThreadB(service);
//		new Thread(a).start();
//		new Thread(b).start();
		
//		System.out.println(Integer.MAX_VALUE >> 16);
		
		int i = 10;
		while(i > 0){
			System.out.println(i);
			if(i == 5){
			}
			i--;
		}
		
	}
}
