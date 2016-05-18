package com.deepak.questions.int_q.task_in_order;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class OrderDictatorByLock extends OrderDictatorBySleep {

	private AtomicInteger order = new AtomicInteger(1);
	
	private ReentrantLock lock = new ReentrantLock();
	//1. acquire lock
	//2. do operation
	//3. release lock

	@Override
	public void shouldRunOrSleep(int expectedOrderInQueue) throws InterruptedException {
		if (order.get() == expectedOrderInQueue) {
			lock.lock();
			return;
		}
		
		while (true) {
			if (order.get() == expectedOrderInQueue) {
				lock.lock();
				break;
			}
			Thread.sleep(1000);
		}
	}

	@Override
	public void taskDone() {
		System.out.format("Thread %s has completed its work with current order %d", Thread.currentThread().getName(), order.get());
		order.incrementAndGet();
		lock.unlock();
	}
}
