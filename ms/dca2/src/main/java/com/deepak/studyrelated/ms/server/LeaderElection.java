/**
 * LeaderElection.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.server;

import java.util.Collection;

import com.deepak.studyrelated.ms.messages.remote.HandShakeRemoteMessage;

/**
 * @author Deepak Abraham
 *
 */
public interface LeaderElection {

    NodeInfo electedLeader();
    
    void addLocalNode(NodeInfo serverInfo);
    
    void addRemoteNode(NodeInfo serverInfo);
    
    void processHandShakeMessage(HandShakeRemoteMessage handShakeRemoteMessage);
    
    void addRemoteNodes(Collection<NodeInfo> serverInfo);
    
    void printAvailableConfiguration();
    
    void setLeader(NodeInfo serverInfo);
    
}
