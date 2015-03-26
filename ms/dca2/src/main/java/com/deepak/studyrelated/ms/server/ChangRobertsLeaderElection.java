/**
 * ChangRobertsLeaderElection.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.exception.RemoteServiceNotAvailableProcessException;
import com.deepak.studyrelated.ms.messages.remote.ElectedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.ElectionRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HandShakeRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HeartBeatRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NewNodesAddedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NodeRemovedRemoteMessage;
import com.deepak.studyrelated.ms.remote.ProcessCommunicatorRemote;
import com.deepak.studyrelated.ms.remote.RemoteServiceLookup;

/**
 * @author Deepak Abraham
 *
 */
public class ChangRobertsLeaderElection implements LeaderElectionCircularConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ChangRobertsLeaderElection.class);
    
    private SortedSet<NodeInfo> nodesInRingTopology = new TreeSet<>();
    private NodeInfo thisServeNode;
    private NodeInfo leader;
    private NodeInfo nextNode;

    @SuppressWarnings("unused")
    private boolean relectionTrigger;

    private static final HeartBeatRemoteMessage heartBeatRemoteMessage = new HeartBeatRemoteMessage();
    
    public ChangRobertsLeaderElection() {
    }

    /* (non-Javadoc)
     * @see com.deepak.studyrelated.ms.server.LeaderElection#electedLeader()
     */
    @Override
    public synchronized NodeInfo electedLeader() {
        return leader;
    }

    /* (non-Javadoc)
     * @see com.deepak.studyrelated.ms.server.LeaderElection#addRemoteServer(com.deepak.studyrelated.ms.server.ServerInfo)
     */
    @Override
    public synchronized void addRemoteNode(NodeInfo serverInfo) {
        addToNodesInRingSet(serverInfo);
    }
    
    private void addToNodesInRingSet(final NodeInfo serverInfo) {
        nodesInRingTopology.add(serverInfo);
        rePopulateNextNode();
    }
    
    private void rePopulateNextNode() {
        nextNode = null;
        getNextNode();
    }

    @Override
    public synchronized void addLocalNode(NodeInfo serverInfo) {
        this.thisServeNode = serverInfo;
        addToNodesInRingSet(serverInfo);
    }

    /* (non-Javadoc)
     * @see com.deepak.studyrelated.ms.server.LeaderElection#addRemoteServers(java.util.List)
     */
    @Override
    public synchronized void addRemoteNodes(Collection<NodeInfo> serverInfoCollection) {
        nodesInRingTopology.addAll(serverInfoCollection);
        rePopulateNextNode();
    }

    @Override
    public synchronized void printAvailableConfiguration() {
        StringBuilder sb = new StringBuilder("\nEach server is output in format 'logicalName/Uid'."
                + "Local server identified with prefix '+' and leader by '*'\n");
        int counter = 0;
        NodeInfo firstNodeInfo = null;
        for (NodeInfo nodeInfo: nodesInRingTopology) {
            if (counter == 0) {
               firstNodeInfo = nodeInfo; 
            }
            appendNodeInfo(sb, nodeInfo);
            sb.append(" ---> ");
            counter++;
        }
        appendNodeInfo(sb, firstNodeInfo);
        logger.info(sb.toString());
    }

    private void appendNodeInfo(StringBuilder sb, NodeInfo nodeInfo) {
        if (nodeInfo.equals(thisServeNode)) {
            sb.append("+");
        } 
        if(nodeInfo.equals(leader)) {
            sb.append("*");
        }
        nodeInfo.addShortString(sb);
    }

    @Override
    public synchronized void setLeader(NodeInfo serverInfo) {
        this.leader = serverInfo;
    }

    @Override
    public synchronized NodeInfo getNextNode() {
        if (nextNode == null) {
            final Iterator<NodeInfo> itr = nodesInRingTopology.iterator();
            NodeInfo firstNodeInfo = null;
            int counter = 0;
            while (itr.hasNext()) {
              final NodeInfo nodeInfo = itr.next();
              if (counter == 0) {
                  firstNodeInfo = nodeInfo;
              }
              if (nodeInfo.equals(thisServeNode)) {
                  if (itr.hasNext()) {
                      nextNode = itr.next();
                  } else {
                      nextNode = firstNodeInfo; //since the cluster of nodes should form a ring and this is the last node
                  }
                  break;
              }
              
              counter++;
            }
        }
        return nextNode;
    }

    @Override
    public synchronized void startElection() {
        logger.info("Starting election from this node: {} !", thisServeNode.getNodeId());
        NodeInfo nodeInfo = getNextNode();
        thisServeNode.setParticipant(true);
        try {
            final ProcessCommunicatorRemote remoteProcCommunicator = lookupRemoteService(getNextNode());
            remoteProcCommunicator.sendElectionMessage(new ElectionRemoteMessage(thisServeNode));
        } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e) {
            logger.info("StackTrace:", e);
            logger.warn("It appears node {} has DIED", nodeInfo);
            // shouldn't happen for simple test
        }
    }

    private ProcessCommunicatorRemote lookupRemoteService(NodeInfo nodeInfo) throws RemoteException,
            RemoteServiceNotAvailableProcessException, AccessException, NotBoundException {
        RemoteServiceLookup lookup = new RemoteServiceLookup(nodeInfo);
        final ProcessCommunicatorRemote remoteProcCommunicator = lookup.lookupRemoteProcessCommunicator();
        return remoteProcCommunicator;
    }
    
    @Override
    public synchronized void processIncomingElectionMsg(ElectionRemoteMessage msg) {
        try {
            if (msg.isUidGreaterThan(thisServeNode)) {
                //1. unconditionally fwd the msg if the msg uid is greater than this server nodes uid
                final ProcessCommunicatorRemote remoteProcCommunicator = lookupRemoteService(getNextNode());
                remoteProcCommunicator.sendElectionMessage(msg);
                thisServeNode.setParticipant(true);
                logger.debug("Leader election, In conditon 1: unconditionally fwd the msg if the msg uid is greater than this server nodes uid");
            } else if (msg.isUidLesserThan(thisServeNode)) {
                /*
                 * 2. If the UID in the election message is smaller, and the process is not yet a participant, the process
                 * replaces the UID in the message with its own UID, sends the updated election message
                 */
                if (!thisServeNode.isParticipant()) {
                    thisServeNode.setParticipant(true);
                    logger.debug("Leader election, In conditon 2: Msg has lower uid this current node & this node is not a participant, "
                            + "Overriding uid of msg {} with self uid{}", msg.getUid(), thisServeNode.getUid() );
                    msg.setUid(thisServeNode.getUid());
                    final ProcessCommunicatorRemote remoteProcCommunicator = lookupRemoteService(getNextNode());
                    remoteProcCommunicator.sendElectionMessage(msg);
                } else {
                    /*
                     * 3. If the UID in the election message is smaller, and the process is already a participant (i.e.,
                     * the process has already sent out an election message with a UID at least as large as its own
                     * UID), the process discards the election message.
                     */
                    logger.info("Leader election, In conditon 3: Discarding election msg from {}, as this node was already a participant",
                            msg.getNodeInfo());
                }
            } else {
                /*
                 * 4. If the UID in the incoming election message is the same as the UID of the process, that process
                 * starts acting as the leader.
                 */
                leader = thisServeNode;
                thisServeNode.setParticipant(false);
                logger.info("Leader election, In conditon 4: received back own uid, so current node is the leader: {}",
                        msg.getNodeInfo());
                sendElectedMessageToNextNode(new ElectedRemoteMessage(thisServeNode));
            }

        } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e) {
            logger.info("StackTrace:", e);
            logger.warn("It appears node {} has DIED", getNextNode());
            //shouldn't happen for simple test
        }
        
        
    }

    private void sendElectedMessageToNextNode(ElectedRemoteMessage msg) {
        try {
            final ProcessCommunicatorRemote remote = lookupRemoteService(getNextNode());
            remote.sendElectedMessage(msg);
        } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e) {
            // shouldn't happen in our small scenario, so ignore relection
            logger.info("StackTrace:", e);
        }
    }

    @Override
    public synchronized SortedSet<NodeInfo> getLinkedNodes() {
        return new TreeSet<>(nodesInRingTopology);
    }

    @Override
    public synchronized NodeInfo getLocalNode() {
        return thisServeNode;
    }

    @Override
    public synchronized void processHandShakeMessage(HandShakeRemoteMessage handShakeRemoteMessage) {
        addRemoteNodes(handShakeRemoteMessage.getCollectionOfAvailableNodesInSender());
        final NewNodesAddedRemoteMessage newNodesAddedRemoteMessage = new NewNodesAddedRemoteMessage(thisServeNode, new TreeSet<>(nodesInRingTopology));
        for (NodeInfo nodeInfo: nodesInRingTopology) {
            if (nodeInfo == thisServeNode) {
                continue;
            }
            try {
                final ProcessCommunicatorRemote remote = lookupRemoteService(nodeInfo);
                remote.sendNewNodesAddedMessage(newNodesAddedRemoteMessage);
            } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e) {
                //shouldn't happen at the moment for small config
               logger.info("StackTrace:", e);
            }
        }
    }

    @Override
    public synchronized void processIncomingElectedMsg(ElectedRemoteMessage msg) {
        if (msg.getNodeInfo().equals(thisServeNode)) {
            logger.info("Received the elected message back, discarding this elected message");
            logger.info("ELCTION PROCESS OVER!!");
            return;
        }
        thisServeNode.setParticipant(false);
        setLeader(msg.getNodeInfo());
        sendElectedMessageToNextNode(msg);
    }

    @Override
    public synchronized void processNewNodesAddedMsg(NewNodesAddedRemoteMessage msg) {
        addRemoteNodes(msg.getNodes());
    }

    @Override
    public synchronized void sendHeartBeatMsgToNextNodeTriggeringRelectionIfLeaderIsDown() {
        NodeInfo nextNodeLocal = null;
        try {
            nextNodeLocal = getNextNode();
            final ProcessCommunicatorRemote remote = lookupRemoteService(nextNodeLocal);
            remote.sendHeartBeatMessage(heartBeatRemoteMessage);
        } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e) {
            logger.info("StackTrace:", e);
            logger.warn("Node {} appears to have died", nextNodeLocal);
            removeFromNodeRing(nextNodeLocal);
            nextNode = null;
            getNextNode();//to populate new next node
            if (nextNodeLocal.equals(leader)) {
                logger.info("Node that dies was also the leader, so relection will be triggered eventually");
                relectionTrigger = true;
            }
            try {
                final ProcessCommunicatorRemote remote = lookupRemoteService(getNextNode());
                remote.sendNodeRemovedMsg(new NodeRemovedRemoteMessage(nextNodeLocal, thisServeNode.getNodeId()));
            } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e1) {
                //shouldnt happen for simple test
                logger.info("StackTrace:", e);
            }
        }
    }

    private void removeFromNodeRing(NodeInfo nextNodeLocal) {
        nodesInRingTopology.remove(nextNodeLocal);
        rePopulateNextNode();
    }

    @Override
    public synchronized void processNodeRemovedMsg(NodeRemovedRemoteMessage msg) {
        if (msg.getSenderNodeId().equals(thisServeNode.getNodeId())) {
            logger.info("Received back node removal msg, triggering relection");
            relectionTrigger = false;
            startElection();
            return;
        }
        removeFromNodeRing(msg.getNodeRemoved());
        try {
            final ProcessCommunicatorRemote remote = lookupRemoteService(getNextNode());
            remote.sendNodeRemovedMsg(msg);
        } catch (RemoteException | RemoteServiceNotAvailableProcessException | NotBoundException e) {
            //shouldn't happen for simple test
            logger.info("StackTrace:", e);
        }
    }
}
