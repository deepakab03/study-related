package com.deepak.studyrelated.ms.server;

import java.util.SortedSet;

import com.deepak.studyrelated.ms.messages.remote.ElectedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.ElectionRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NewNodesAddedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NodeRemovedRemoteMessage;

public interface LeaderElectionCircularConfiguration extends LeaderElection {

    NodeInfo getNextNode();

    void processIncomingElectionMsg(ElectionRemoteMessage msg);

    void startElection();

    SortedSet<NodeInfo> getLinkedNodes();

    NodeInfo getLocalNode();

    void processIncomingElectedMsg(ElectedRemoteMessage msg);

    void processNewNodesAddedMsg(NewNodesAddedRemoteMessage msg);

    void sendHeartBeatMsgToNextNodeTriggeringRelectionIfLeaderIsDown();

    void processNodeRemovedMsg(NodeRemovedRemoteMessage msg);
}
