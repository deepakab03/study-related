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
public class ElectionRemoteMessage extends AbstractRemoteMessage {

    private static final long serialVersionUID = 1L;
    private final NodeInfo localServerInfo;
    
    private int uid;
    
    /**
     * @param localServerInfo 
     * @param messageType
     */
    public ElectionRemoteMessage(NodeInfo localServerInfo) {
        super(RemoteMessageType.ELECTION);
        this.localServerInfo = localServerInfo;
        uid = localServerInfo.getUid();
    }

    public NodeInfo getNodeInfo() {
         return localServerInfo;
    }

     public boolean isUidGreaterThan(NodeInfo otherNodeInfo) {
        return uid > otherNodeInfo.getUid();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isUidLesserThan(NodeInfo otherNodeInfo) {
        return uid < otherNodeInfo.getUid();
    }

}
