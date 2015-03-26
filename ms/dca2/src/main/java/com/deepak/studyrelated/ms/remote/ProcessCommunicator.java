package com.deepak.studyrelated.ms.remote;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.messages.remote.ElectedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.ElectionRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HandShakeRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HeartBeatRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NewNodesAddedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NodeRemovedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.RemoteMessage;
import com.deepak.studyrelated.ms.server.LeaderElectionCircularConfiguration;
import com.deepak.studyrelated.ms.server.ThreadCommunicator;

/**
 * @author Deepak Abraham
 */
public class ProcessCommunicator implements ProcessCommunicatorRemote {
    private static final Logger logger = LoggerFactory.getLogger(ProcessCommunicator.class);

    /**
     * Sends incoming RMI requests (Each rmi request is implicitly handled in a separate thread)
     * to the server thread for processing
     */
    private ThreadCommunicator<RemoteMessage> incomingRmiRequestThreadCommunicator;

    private LeaderElectionCircularConfiguration leaderElection;
    
    public ProcessCommunicator(final ThreadCommunicator<RemoteMessage> incomingRmiRequestThreadCommunicator,
            final LeaderElectionCircularConfiguration leaderElection) {
        this.incomingRmiRequestThreadCommunicator = incomingRmiRequestThreadCommunicator;
        this.leaderElection = leaderElection;
    }

    public HeartBeatRemoteMessage heartBeat() throws RemoteException {
        return null;
    }

    @Override
    public HandShakeRemoteMessage handShake(HandShakeRemoteMessage handShakeMessage) {
        logger.debug("Got a handshake msg from: {}", handShakeMessage.getSenderNodeInfo());
        incomingRmiRequestThreadCommunicator.sendMsg(handShakeMessage);
        return new HandShakeRemoteMessage(leaderElection.getLocalNode(), leaderElection.getLinkedNodes());
    }

    @Override
    public void sendElectionMessage(ElectionRemoteMessage electionRemoteMessage) {
        logger.info("Got an election msg with uid {} from: {}", electionRemoteMessage.getUid(), electionRemoteMessage.getNodeInfo());
        incomingRmiRequestThreadCommunicator.sendMsg(electionRemoteMessage);
    }

    @Override
    public void sendElectedMessage(ElectedRemoteMessage electedRemoteMessage) {
        logger.info("Got an elected message from: {}", electedRemoteMessage.getNodeId());
        incomingRmiRequestThreadCommunicator.sendMsg(electedRemoteMessage);
    }

    @Override
    public void sendNewNodesAddedMessage(NewNodesAddedRemoteMessage newNodesAddedRemoteMessage) throws RemoteException {
        logger.info("Got a new nodes added message from: {}", newNodesAddedRemoteMessage.getNodeId());
        incomingRmiRequestThreadCommunicator.sendMsg(newNodesAddedRemoteMessage);
    }

    @Override
    public void sendHeartBeatMessage(HeartBeatRemoteMessage heartBeatRemoteMessage) throws RemoteException {
        logger.trace("Got a heatbeat msg from previous node");
    }

    @Override
    public void sendNodeRemovedMsg(NodeRemovedRemoteMessage nodeRemovedRemoteMessage) throws RemoteException {
        logger.info("Got a node REMOVED message from: {}, that node {} died",
                nodeRemovedRemoteMessage.getSenderNodeId(), nodeRemovedRemoteMessage.getNodeRemoved()
                        .getNodeId());
        incomingRmiRequestThreadCommunicator.sendMsg(nodeRemovedRemoteMessage);
    }

}
