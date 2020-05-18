package com.threadPool;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkPoolDemo extends RecursiveTask<Long>{

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8397915198288939147L;
	
	private static final long THRESHOLD = 100;
	
	private long start;
	private long end;
	
	public ForkPoolDemo(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		long sum = 0;
		boolean canComp = end - start > THRESHOLD;
		if (canComp) {
			for(long i = start; i <= end; i++){
				sum += i;
			}
		} else {
			ArrayList<ForkPoolDemo> demos = new ArrayList<>();
			long step = (start + end) / 10;
			//分任务执行
			long pos = start;
			for (int i = 0; i < 10; i++) {
				long curEnd = pos + step;
				if(curEnd > end) curEnd = end;
				ForkPoolDemo demo = new ForkPoolDemo(pos, curEnd);
				pos = pos + step +1;
				demos.add(demo);
				demo.fork();
			}
			for(ForkPoolDemo demo : demos){
				sum += demo.join();
			}
			
		}
		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		ForkPoolDemo demo = new ForkPoolDemo(0, 101);
		ForkJoinTask<Long> task = pool.submit(demo);
		try {
			long result = task.get();
			System.out.println(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
