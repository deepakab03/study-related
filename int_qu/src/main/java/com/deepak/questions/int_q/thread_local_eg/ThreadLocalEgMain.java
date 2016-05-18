package com.deepak.questions.int_q.thread_local_eg;

import java.util.concurrent.Executors;

import java.util.concurrent.ExecutorService;

public class ThreadLocalEgMain {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(5);
		
		int tasks = 1000;
		int counter = 1;
		while (counter <+ tasks) {
			if (counter % 2 == 0) {
				service.execute(new MailBoxTask("Message posted " + counter));
			} else {
				service.execute(new MailBoxTaskOnlySubTask("Sub task Message posted " + counter));
			}
			Thread.sleep(500);
			counter++;
		}
	}
}
