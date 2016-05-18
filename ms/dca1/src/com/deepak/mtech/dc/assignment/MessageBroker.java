package com.deepak.mtech.dc.assignment;

import java.util.ArrayList;
import java.util.List;

import com.deepak.mtech.dc.assignment.event.MsgEvent;

public class MessageBroker {

	private List<LampertClockThread> threadList = new ArrayList<>(5);

	public MessageBroker() {
	}

	public void sendEventRandomlyToAnotherThread(final MsgEvent event) {
		int size = threadList.size();
		int random = (int) (Math.random() * 1000);
		int index = random % size;

		threadList.get(index).addToQueue(event);
	}

	public void addThread(LampertClockThread thread) {
		threadList.add(thread);
		thread.setMsgBroker(this);
	}

	public void startThreads() {
		System.out.println("Starting threads: " + threadList.size());
		for (LampertClockThread thread : threadList) {
			thread.start();
		}
	}

	public void stopThreads() {
		System.out.println("Stopping threads: " + threadList.size());
		for (LampertClockThread thread : threadList) {
			thread.setStopThread(true);
		}

	}

}
