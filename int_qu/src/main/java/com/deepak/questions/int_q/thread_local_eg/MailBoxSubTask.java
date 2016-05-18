package com.deepak.questions.int_q.thread_local_eg;

public class MailBoxSubTask {

	public void doSubTask() {
		MailBox mailBox2 = MailBoxInstanceCreator.getInstance().createInstance();
		mailBox2.postMessage("Posting sub task message");
	}

	public void doSubTask(String message) {
		MailBox mailBox2 = MailBoxInstanceCreator.getInstance().createInstance();
		mailBox2.postMessage(message);
	}
}
