package com.deepak.questions.int_q.future_cancel;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Note <code>Thread.currentThread().isInterrupted()</code> should be used in
 * preference to <code>Thread.interrutped()</code> as the latter clear the
 * interrupted flag (if called in succession, first time it will return true and
 * false the next time)
 * 
 * @author abrahd2
 *
 */
public class CallableThatDoesSomeProcessing implements Callable<String> {

	private AtomicBoolean stopProcessing = new AtomicBoolean();
	private Object waiter = new Object();

	public String call() throws Exception {
		try {

			System.out.println("Random call executing, interrupt flag: " + Thread.currentThread().isInterrupted());
			// simpleSleep();
			// cotinuousProcessing();
			// cotinuousProcessingWithInterruptFlagCheck();
			// objectWaiting();
			 blockingOperation();

		} catch (Throwable t) {
			t.printStackTrace();
		}

		System.out.println("Processing done!");
		return "done";
	}

	/**
	 * Demonstrates that the cancel flag works in throwing the exception when
	 * thread is asleep only
	 * 
	 * @throws InterruptedException
	 */
	void simpleSleep() throws InterruptedException {
		try {
			while (!stopProcessing.get()) {
				System.out.println("Doing some processing");
				Thread.sleep(1 * 1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Task Interrupted, with interrupt flag: " + Thread.currentThread().isInterrupted());
			Thread.sleep(5 * 1000);
			System.out.println("Interrupt flag after sleep :" + Thread.currentThread().isInterrupted());
			e.printStackTrace();
		}
	}

	/**
	 * Demos that as long as thread is actively doing something, it will not be
	 * interrupted! Further if the
	 */
	void cotinuousProcessing() {
		try {
			System.out.println("Doing some processing");
			int[] intArray = new int[100];
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				System.out.println("Doing Interation " + i);
				while (intArray[i]++ < Integer.MAX_VALUE) {
					continue;
				}
				if (i != 0 && i % 20 == 0) {
					System.out.println("Sleeping after iteration " + i);
					Thread.sleep(1 * 1000);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Task Interrupted, with interrupt flag: " + Thread.currentThread().isInterrupted());
			e.printStackTrace();
		}
	}

	/**
	 * Demos that as long as thread is actively doing something, it will not be
	 * interrupted, unless it checks the thread interrupt flag
	 */
	void cotinuousProcessingWithInterruptFlagCheck() {
		try {
			System.out.println("Doing some processing");
			int[] intArray = new int[100];
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				System.out.println("Doing Interation " + i);
				while (intArray[i]++ < Integer.MAX_VALUE) {
					continue;
				}
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("Thread interrupted flag set!!, breaking");
					break;
				}
				if (i != 0 && i % 50 == 0) {
					System.out.println("Sleeping after iteration " + i);
					Thread.sleep(1 * 1000);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Task Interrupted, with interrupt flag: " + Thread.currentThread().isInterrupted());
			e.printStackTrace();
		}
	}

	void objectWaiting() throws InterruptedException {
		synchronized (waiter) {
			System.out.println("Waiting on object");
			waiter.wait();
		}
	}

	public void blockingOperation() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Reading a line:");
			String line = null;
			while (!("e".equalsIgnoreCase(line) || "stop".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line))) {
				line = scanner.nextLine();
				System.out.println("Line read: " + line);
				 if (Thread.currentThread().isInterrupted()) {
					 System.out.println("Thread was interrupted, stopping read of line");
					 break;
				 }
			}
		}
	}

	public void stopProcessing() {
		stopProcessing.set(true);
	}

}
