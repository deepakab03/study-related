/**
 * HandShakeMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;


/**
 * @author Deepak Abraham
 * 
 */
public class HeartBeatRemoteMessage extends AbstractRemoteMessage {

	private static final long serialVersionUID = 1L;
	
	public HeartBeatRemoteMessage() {
		super (RemoteMessageType.HEARTBEAT);
	}

}
