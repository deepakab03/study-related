/**
 * ElectionMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;

import com.deepak.studyrelated.ms.server.NodeInfo;

/**
 * @author Deepak Abraham
 *
 */
public class ElectedRemoteMessage extends AbstractRemoteMessage {

    private static final long serialVersionUID = 1L;
    private final NodeInfo localServerInfo;
    
    /**
     * @param localServerInfo 
     * @param messageType
     */
    public ElectedRemoteMessage(NodeInfo localServerInfo) {
        super(RemoteMessageType.ELECTED);
        this.localServerInfo = localServerInfo;
    }

    public NodeInfo getNodeInfo() {
         return localServerInfo;
    }

    public String getNodeId() {
        return localServerInfo.getNodeId();
    }
}
