/**
 * ElectionMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;

import com.deepak.studyrelated.ms.server.NodeInfo;

/**
 * @author Deepak Abraham
 */
public class NodeRemovedRemoteMessage extends AbstractRemoteMessage {

    private static final long serialVersionUID = 1L;
    private final NodeInfo nodeRemoved;
    private String senderNodeId;

    /**
     * @param localServerInfo
     * @param messageType
     */
    public NodeRemovedRemoteMessage(NodeInfo nodeRemoved, String senderNodeId) {
        super(RemoteMessageType.NODE_REMOVED);
        this.nodeRemoved = nodeRemoved;
        this.senderNodeId = senderNodeId;
    }

    public NodeInfo getNodeRemoved() {
        return nodeRemoved;
    }

    public String getSenderNodeId() {
        return senderNodeId;
    }
}
