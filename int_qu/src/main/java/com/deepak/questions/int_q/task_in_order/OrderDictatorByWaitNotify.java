package com.deepak.questions.int_q.task_in_order;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderDictatorByWaitNotify extends OrderDictatorBySleep {

	private AtomicInteger order = new AtomicInteger(1);
	
	@Override
	public synchronized void shouldRunOrSleep(int expectedOrderInQueue) throws InterruptedException {
		if (order.get() == expectedOrderInQueue) {
			return;
		}
		
		while (order.get() != expectedOrderInQueue) {
			wait();
			System.out.format("%nThread %s got notified", Thread.currentThread().getName());
		}
	}

	@Override
	public synchronized void taskDone() {
		System.out.format("Thread %s has completed its work with current order %d", Thread.currentThread().getName(), order.get());
		order.incrementAndGet();
		notifyAll();
		//notify() won't work
	}
}
