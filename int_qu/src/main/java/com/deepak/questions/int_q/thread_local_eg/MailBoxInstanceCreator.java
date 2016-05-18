package com.deepak.questions.int_q.thread_local_eg;

public class MailBoxInstanceCreator {

	private static final ThreadLocal<MailBox> MAIL_BOX_THREAD_LOCAL = new ThreadLocal<MailBox>() {
		@Override
		protected MailBox initialValue() {
			return new MailBox();
		}
	};
	
	private static final MailBoxInstanceCreator CREATOR = new MailBoxInstanceCreator();
	
	public MailBox createInstance() {
//		return new MailBox();
		 return MAIL_BOX_THREAD_LOCAL.get();
	}

	public static MailBoxInstanceCreator getInstance() {
		return CREATOR;
	}
}
