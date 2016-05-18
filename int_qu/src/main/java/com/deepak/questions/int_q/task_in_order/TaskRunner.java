package com.deepak.questions.int_q.task_in_order;

public class TaskRunner extends Thread {

	private final Task task;
	private final OrderDictatorBySleep orderDictator;
	private final int expectedOrderInQueue;

	public TaskRunner(Task task, OrderDictatorBySleep orderDictator, int expectedOrderInQueue) {
		this.task = task;
		this.orderDictator = orderDictator;
		this.expectedOrderInQueue = expectedOrderInQueue;
	}

	@Override
	public void run() {
		System.out.format("%nIn run method of thread with expectedOrderInQueue %d", expectedOrderInQueue);

		try {
			orderDictator.shouldRunOrSleep(expectedOrderInQueue);

			task.performTask();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			orderDictator.taskDone();
		}
	}

}
