package com.dragon.spider.thread.manager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {

	private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);		
	
	private static ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(100, 100, 2, TimeUnit.SECONDS, queue,  
            new ThreadPoolExecutor.DiscardOldestPolicy()); 

	private static ThreadPools instance = new ThreadPools();
	private static int taskNumber = 100;

	public static ThreadPools getInstance() {
		return instance;
	}

	public synchronized static int getQueueSize() {
		return queue.size();		
	}

	/**
	 * 实现任务的添加
	 * @param task
	 */
	public synchronized static void doTask(Runnable task) {     
		while (getQueueSize() >= taskNumber) {
			System.out.println("队列已满，等3秒再添加任务");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		taskExecutor.execute(task);
	}	
}
