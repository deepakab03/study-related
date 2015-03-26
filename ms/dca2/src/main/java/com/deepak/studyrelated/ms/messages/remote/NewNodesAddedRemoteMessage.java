/**
 * NewNodesAddedRemoteMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.messages.remote;

import java.util.SortedSet;

import com.deepak.studyrelated.ms.server.NodeInfo;

/**
 * @author Deepak Abraham
 *
 */
public class NewNodesAddedRemoteMessage extends AbstractRemoteMessage {

    private static final long serialVersionUID = 1L;
    private SortedSet<NodeInfo> nodes;
    private String originatingNodeId;
    /**
     * @param messageType
     */
    public NewNodesAddedRemoteMessage(NodeInfo originatingNode, SortedSet<NodeInfo> nodes) {
        super(RemoteMessageType.NEW_NODES_ADDED);
        this.nodes = nodes;
        originatingNodeId = originatingNode.getNodeId();
    }

    public SortedSet<NodeInfo> getNodes() {
        return nodes;
    }

    public String getNodeId() {
        return originatingNodeId;
    }

}
