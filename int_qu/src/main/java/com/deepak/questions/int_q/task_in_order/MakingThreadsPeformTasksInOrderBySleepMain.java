package com.deepak.questions.int_q.task_in_order;

import java.util.Random;

public class MakingThreadsPeformTasksInOrderBySleepMain {

	public static void main(String[] args) throws InterruptedException {
		OrderDictatorBySleep orderDictator = new OrderDictatorBySleep();
		TaskRunner t1 = new TaskRunner(new Task("t1 task"), orderDictator, 4);
		TaskRunner t2 = new TaskRunner(new Task("t2 task"), orderDictator, 3);
		TaskRunner t3 = new TaskRunner(new Task("t3 task"), orderDictator, 2);
		TaskRunner t4 = new TaskRunner(new Task("t4 task"), orderDictator, 1);
		
		Random random = new Random();
		t1.start();
		sleepForABit(random);
		t2.start();
		sleepForABit(random);
		t3.start();
		sleepForABit(random);
		t4.start();
	}

	public static void sleepForABit(Random random) throws InterruptedException {
		System.out.println("\nSleeping for a bit in thread " + Thread.currentThread().getName());
		Thread.sleep(random.nextInt(5) * 1000);
	}
}
