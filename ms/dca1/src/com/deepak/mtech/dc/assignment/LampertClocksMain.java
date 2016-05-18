package com.deepak.mtech.dc.assignment;

public class LampertClocksMain {

	public static void main(String[] args) throws InterruptedException {
		LampertClocksMain main = new LampertClocksMain();
		int numThreads = 5;
		if (args.length > 0) {
			try {
				numThreads = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		main.simulate(numThreads);
	}

	private void simulate(final int numThreads) throws InterruptedException {
		MessageBroker broker = new MessageBroker();
		for (int i = 0; i < numThreads; i++) {
			int priority =  ((int) (Math.random()* 10)) % numThreads;
			LampertClockThread thread = new LampertClockThread(i+1
					+ "-lampertProcess", priority);
			broker.addThread(thread);
			if (i == 1 || i % 3 ==0) {
				//lessen sleep of some of the threads, so it fires more events.
				thread.setSleepTime(3*i);
			}
		}
		broker.startThreads();
		
		// stop with Ctl C or it will run for 10 minutes
		int counter = 0;
		System.out.println("Waiting for simulation to play itself out...");
		while (counter++ < 10) {
			Thread.sleep(1 * 60 * 1000);
		}

		broker.stopThreads();
		System.out.println("Exiting simulation");
	}
}
