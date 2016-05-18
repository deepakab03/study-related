package com.deepak.questions.int_q.thread_local_eg;

import java.util.concurrent.ThreadLocalRandom;

public class MailBoxTaskOnlySubTask implements Runnable {

	private final String message;

	private static final MailBoxSubTask subTask = new MailBoxSubTask();
	
	public MailBoxTaskOnlySubTask(String message) {
		this.message = message;
	}

	public void run() {
		try {
			//simulating time consuming work
			final int sleepTime = ThreadLocalRandom.current().nextInt(5);
			System.out.format("%n%s: Doing some time consuming, sleeping for %d seconds", Thread.currentThread().getName(), sleepTime);
			Thread.sleep(sleepTime * 1000);
			
			subTask.doSubTask(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
