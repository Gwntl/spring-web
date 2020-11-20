package com.quartz.traditional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Test {
	private static Map<String, List<String>> map = new ConcurrentHashMap<>();
	public static void main(String[] args) {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory());
		List<String> list = new LinkedList<>();
		for (int i = 0; i < 20; i++) {
			list.add("value_" + i);
		}
		map.put("key", list);
		poolExecutor.submit(new ForeachWorker());
		poolExecutor.submit(new ForeachWorker(true));
		poolExecutor.shutdown();
	}

	public static class ForeachWorker implements Runnable {
		private boolean flag;

		public ForeachWorker() {
			this(false);
		}

		public ForeachWorker(boolean flag) {
			this.flag = flag;
		}

		@Override
		public void run() {
			if (flag) {
				synchronized (map) {
					handling();
				}
			} else {
				handlingForeach();
			}
		}

		public void handling() {
			List<String> list = map.get("key");
			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				String s = iterator.next();
				System.out.println(Thread.currentThread().getName() + "_" + s);
				iterator.remove();
			}
		}

		public void handlingForeach() {
			List<String> list = map.get("key");
			for (String s : list) {
				System.out.println(Thread.currentThread().getName() + "_" + s);
			}
		}
	}
}
