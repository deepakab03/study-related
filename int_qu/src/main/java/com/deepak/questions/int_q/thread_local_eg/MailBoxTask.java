package com.deepak.questions.int_q.thread_local_eg;

import java.util.concurrent.ThreadLocalRandom;

public class MailBoxTask implements Runnable {

	private final String message;

	private static final MailBoxSubTask subTask = new MailBoxSubTask();
	
	public MailBoxTask(String message) {
		this.message = message;
	}

	public void run() {
		final MailBoxInstanceCreator mailBoxInstanceCreator = MailBoxInstanceCreator.getInstance();
		try {
			//EVERY object that uses mailbox, should use the same mailBox when accessed by this thread
			//simulating time consuming work
			final int sleepTime = ThreadLocalRandom.current().nextInt(5);
			System.out.format("%n%s: Doing some time consuming, sleeping for %d seconds", Thread.currentThread().getName(), sleepTime);
			Thread.sleep(sleepTime * 1000);
			
			MailBox mailBox = mailBoxInstanceCreator.createInstance();
			mailBox.postMessage(message);
			
			//image a deeply nested hierarcy where many objects of different types use the same Mailbox for the same thread
			subTask.doSubTask();
			//both doSubtask and above mailBox use the same instance of mailBox
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
