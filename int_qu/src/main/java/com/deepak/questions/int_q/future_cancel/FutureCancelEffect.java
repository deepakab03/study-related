package com.deepak.questions.int_q.future_cancel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * When a future is cancelled, what happens. It appears only if the thread is
 * sleeping (or waiting) is the interrupt exception thrown. Otherwise it is
 * processing or waiting for say an input from the user (or say a web service
 * call) it will not be!
 * 
 * @author abrahd2
 *
 */
public class FutureCancelEffect {

	public static void main(String[] args) throws InterruptedException {
		CallableThatDoesSomeProcessing callable = new CallableThatDoesSomeProcessing();

		ExecutorService singleThreadExecutorService = Executors.newSingleThreadExecutor();
		try {
			final Future<String> future = singleThreadExecutorService.submit(callable);

			Thread.sleep(5 * 1000);

			System.out.println("Cancelling future!");
			future.cancel(true);

			Thread.sleep(5 * 1000);
		} finally {
			singleThreadExecutorService.shutdown();
			int counter = 100;
			while (!singleThreadExecutorService.isTerminated() && counter-- > 0) {
				Thread.sleep(5 * 1000);
			}
		}
	}
}
