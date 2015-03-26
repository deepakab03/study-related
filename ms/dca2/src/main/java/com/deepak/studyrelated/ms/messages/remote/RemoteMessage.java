/**
 * HandShakeMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;

import com.deepak.studyrelated.ms.messages.Message;


/**
 * Server to server messages
 * @author Deepak Abraham
 * 
 */
public interface RemoteMessage extends Message<RemoteMessageType> {

	RemoteMessageType messageType();
}
