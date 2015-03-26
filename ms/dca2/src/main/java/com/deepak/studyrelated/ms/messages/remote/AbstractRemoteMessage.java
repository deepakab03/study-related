/**
 * HandShakeMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;

import java.io.Serializable;

/**
 * @author Deepak Abraham
 * 
 */
public class AbstractRemoteMessage implements RemoteMessage, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final RemoteMessageType messageType;

	public AbstractRemoteMessage(RemoteMessageType messageType) {
		this.messageType = messageType;
	}

	public RemoteMessageType messageType() {
		return messageType;
	}

}
