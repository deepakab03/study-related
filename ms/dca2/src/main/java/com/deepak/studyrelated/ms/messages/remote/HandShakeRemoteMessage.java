/**
 * HandShakeMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;

import java.util.SortedSet;

import com.deepak.studyrelated.ms.server.NodeInfo;

/**
 * @author Deepak Abraham
 * 
 */
public class HandShakeRemoteMessage extends AbstractRemoteMessage {

	private static final long serialVersionUID = 1L;
    
	private final NodeInfo senderNodeInfo;
	
	private final SortedSet<NodeInfo> collectionOfAvailableNodesInSender;
	
	public HandShakeRemoteMessage(final NodeInfo localServerInfo, final SortedSet<NodeInfo> collectionOfAvailableNodesInSender) {
		super (RemoteMessageType.HANDSHAKE);
		this.senderNodeInfo = localServerInfo;
		this.collectionOfAvailableNodesInSender = collectionOfAvailableNodesInSender;
	}

    public NodeInfo getSenderNodeInfo() {
        return senderNodeInfo;
    }
    
    @Override
    public String toString() {
        return "handShake: " + senderNodeInfo.toString();
    }

    public SortedSet<NodeInfo> getCollectionOfAvailableNodesInSender() {
        return collectionOfAvailableNodesInSender;
    }

}
