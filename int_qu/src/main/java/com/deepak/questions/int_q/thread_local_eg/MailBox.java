package com.deepak.questions.int_q.thread_local_eg;

import java.util.concurrent.atomic.AtomicInteger;

public class MailBox {

	private static final AtomicInteger instanceCounterAtomic = new AtomicInteger(1);
	private final int mailBoxNumber;
	
	MailBox() {
		mailBoxNumber = instanceCounterAtomic.getAndIncrement();
		System.out.format("%n%s: Creating Mailbox %d", currentThreadName(), mailBoxNumber);
	}
	
	public void postMessage(final String message) {
		System.out.format("%n%s: Got message for Mailbox %d, msg %s", currentThreadName(), mailBoxNumber, message);
	}

	private String currentThreadName() {
		return Thread.currentThread().getName();
	}
}
