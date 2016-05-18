package com.deepak.questions.int_q.task_in_order;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderDictatorBySleep {

	private AtomicInteger order = new AtomicInteger(1);

	public void shouldRunOrSleep(int expectedOrderInQueue) throws InterruptedException {
		if (order.get() == expectedOrderInQueue) {
			return;
		}
		
		while (order.get() != expectedOrderInQueue) {
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void taskDone() {
		System.out.format("Thread %s has completed its work with current order %d", Thread.currentThread().getName(), order.get());
		order.incrementAndGet();
	}
}
