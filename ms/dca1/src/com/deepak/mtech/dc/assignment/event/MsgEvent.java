package com.deepak.mtech.dc.assignment.event;

public class MsgEvent extends Event {

	private static final long serialVersionUID = 1L;
	private int sendClock;
	
	public MsgEvent(Object source, int sendClock) {
		super(source);
		this.sendClock = sendClock;
	}

	public int getSendClock() {
		return sendClock;
	}

}
