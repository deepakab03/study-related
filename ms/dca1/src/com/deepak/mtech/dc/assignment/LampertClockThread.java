package com.deepak.mtech.dc.assignment;

import static java.lang.Math.max;
import static java.lang.String.format;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import com.deepak.mtech.dc.assignment.event.Event;
import com.deepak.mtech.dc.assignment.event.InternalEvent;
import com.deepak.mtech.dc.assignment.event.MsgEvent;

public class LampertClockThread extends Thread {

	private MessageBroker msgBroker;
	private AtomicBoolean stopThread = new AtomicBoolean(false);
	private BlockingDeque<MsgEvent> incomingMsgQueue = new LinkedBlockingDeque<>();

	private int sleepTime = 0;
	private int logicalClock = 0;
	private final int threadOrderIndex;

	public LampertClockThread(final String threadName,
			final int threadOrderIndex) {
		super(threadName);
		this.threadOrderIndex = threadOrderIndex;
	}

	@Override
	public void run() {
		print("Starting thread...");
		while (!stopThread.get()) {
			generateRandomEvent();
			sleepForABit();

			if (incomingMsgQueue.isEmpty()) {
				continue;
			}
			takeMsgFromQueue();
		}
		
		print("Exiting thread");
	}

	private void takeMsgFromQueue() {
		try {
			MsgEvent incomingEvent = incomingMsgQueue.take();
			int oldLogicalClock = logicalClock;
			applyRule2(incomingEvent);
			applyRule1();
			print(format("On incoming event from %s, clock changed form %d to %d",
					incomingEvent.getSource().toString(), oldLogicalClock,
					logicalClock));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void generateRandomEvent() {
		int oldLogicalClock = logicalClock;
		Event event = null;
		if (isRandomInternalEvent()) {
			event = new InternalEvent(this);
		} else {
			event = new MsgEvent(this, logicalClock);
		}
		applyRule1();
		print(format("For event: %s, logical time incremented from %d to %d", event
				.getClass().getSimpleName(), oldLogicalClock, logicalClock));
		if (event instanceof MsgEvent) {
			msgBroker.sendEventRandomlyToAnotherThread((MsgEvent) event);
		}
	}

	private void sleepForABit() {
		int sleepTime = ((int) (Math.random() * 1000)) % 30;
		if (this.sleepTime != 0) {
			sleepTime = this.sleepTime;
		}
		print("Sleeping for seconds " + sleepTime);
		try {
			Thread.sleep(sleepTime * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void applyRule1() {
		logicalClock++;

	}

	private void applyRule2(final MsgEvent msgEvent) {
		logicalClock = max(logicalClock, msgEvent.getSendClock());
	}

	private boolean isRandomInternalEvent() {
		int randomInt = (int) (Math.random() * 100) % 2;
		return randomInt == 0;
	}

	public void addToQueue(MsgEvent event) {
		incomingMsgQueue.add(event);
	}

	private void print(String msg) {
		System.out.println(format("[%s:%d] %s", getName(), threadOrderIndex, msg));
	}

	public void setStopThread(boolean stopThread) {
		this.stopThread.set(stopThread);
	}

	public void setMsgBroker(MessageBroker msgBroker) {
		this.msgBroker = msgBroker;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

}
