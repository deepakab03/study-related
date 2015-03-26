package com.deepak.studyrelated.ms.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.exception.RemoteServiceNotAvailableProcessException;
import com.deepak.studyrelated.ms.messages.local.LocalMsg;
import com.deepak.studyrelated.ms.messages.local.LogicalNameLocalMsg;
import com.deepak.studyrelated.ms.messages.local.RemoteServerInfoLocalMsg;
import com.deepak.studyrelated.ms.messages.local.RmiRegistryPortNumLocalMsg;
import com.deepak.studyrelated.ms.messages.remote.ElectedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.ElectionRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HandShakeRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NewNodesAddedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NodeRemovedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.RemoteMessage;
import com.deepak.studyrelated.ms.remote.LocalRegistryBinder;
import com.deepak.studyrelated.ms.remote.ProcessCommunicatorRemote;
import com.deepak.studyrelated.ms.remote.RemoteServiceLookup;

/**
 * @author Deepak Abraham
 */
public class ServerThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ServerThread.class);

    private int rmiPort;
    private ThreadCommunicator<LocalMsg> localClientThreadIncomingMsgCommunicator;
    private ThreadCommunicator<RemoteMessage> incomingRmiRequestThreadCommunicator;
    
    private LocalRegistryBinder localRegistryBinder;
    private LeaderElectionCircularConfiguration leaderElection = new ChangRobertsLeaderElection();
    
    private boolean serverInitialised = false;
    private final AtomicBoolean stopProcess = new AtomicBoolean(false);

    private NodeInfo localServerInfo;
    
    private Timer timerToTriggerElectionTimer = new Timer();
    private TimerTask startElectionIfNotStartedTask = new TimerTask() {
        
        @Override
        public void run() {
            if (leaderElection.electedLeader() == null) {
                logger.info("No leader elected till now so will start leader election");
                leaderElection.startElection();
            }
        }
    };
    
    public ServerThread(ThreadCommunicator<LocalMsg>  localClientThreadIncomingMsgCommunicator) {
        super("serverThread");
        this.localClientThreadIncomingMsgCommunicator = localClientThreadIncomingMsgCommunicator;
        incomingRmiRequestThreadCommunicator = new ThreadCommunicator<>();
        
        timerToTriggerElectionTimer.schedule(startElectionIfNotStartedTask, 5 * 60 * 1000);
    }
    
    public void run() {
        logger.info("Starting server thread");
        
        while (!stopProcess.get()) {
           
            if (!serverInitialised) {
                readInitialInformationToSetupServer();
            } else {
                processMsgsFromLocalClientThread();
                processMsgsFromRemoteProcesses();
                sendHeartBeatMsgToNextNode();
            }
            
            sleepForABit();
        }
        
        localRegistryBinder.stopRegistry();
        logger.info("Exiting server thread");
    }

    private void sendHeartBeatMsgToNextNode() {
        leaderElection.sendHeartBeatMsgToNextNodeTriggeringRelectionIfLeaderIsDown();
    }

    private void processMsgsFromRemoteProcesses() {
        RemoteMessage msg = incomingRmiRequestThreadCommunicator.pollMsg();
        if (msg == null) {
            return;
        }
        switch (msg.messageType()) {
        case HANDSHAKE:
            final HandShakeRemoteMessage handShakeRemoteMessage = (HandShakeRemoteMessage) msg;
            logger.info("received msg: {}" + handShakeRemoteMessage.toString());
            leaderElection.processHandShakeMessage(handShakeRemoteMessage);
            break;
        case ELECTION:
            leaderElection.processIncomingElectionMsg((ElectionRemoteMessage) msg);
            break;
        case ELECTED:
            leaderElection.processIncomingElectedMsg((ElectedRemoteMessage) msg);
            break;
        case NEW_NODES_ADDED:
            leaderElection.processNewNodesAddedMsg((NewNodesAddedRemoteMessage) msg);
            break;
        case NODE_REMOVED:
            leaderElection.processNodeRemovedMsg((NodeRemovedRemoteMessage) msg);
            break;
        default:
            logger.warn("Don't know how to handle remote msg {}", msg.getClass()
                    .getSimpleName());
        }
    }

    private void processMsgsFromLocalClientThread() {
        LocalMsg msg = localClientThreadIncomingMsgCommunicator.pollMsg();
        if (msg != null) {
            try {
                switch(msg.messageType()) {
                case REMOTE_SERVER_INFO:
                    RemoteServiceLookup serviceLookup = new RemoteServiceLookup((RemoteServerInfoLocalMsg) msg);
                    ProcessCommunicatorRemote remoteProcess = serviceLookup.lookupRemoteProcessCommunicator();
                    HandShakeRemoteMessage remoteHandShake = remoteProcess.handShake(new HandShakeRemoteMessage(localServerInfo, leaderElection.getLinkedNodes()));
                    logger.info("For handshake sent, received handshake reply : {}", remoteHandShake);
                    leaderElection.processHandShakeMessage(remoteHandShake);
                    break;
                case OUTPUT_RING_CONFIGURATION:
                    leaderElection.printAvailableConfiguration();
                    break;
                case TRIGGER_ELECTION:
                    leaderElection.startElection();
                    break;
                 default:
                     logger.info("Don't know how to handle local msg {}", msg.getClass().getSimpleName());
                }
                
            } catch (RemoteException | RemoteServiceNotAvailableProcessException e) {
                logger.info("StackTrace:", e);
                logger.warn("Remote service is down, please input details after it is brought up");
            } catch (NotBoundException e) {
                logger.info("StackTrace:", e);
                logger.warn("Remote process is not brought up correctly as processCommunicator service is not bound!");
            }
        }
    }

    private void readInitialInformationToSetupServer() {
        LocalMsg msg = localClientThreadIncomingMsgCommunicator.pollMsg();
        if (msg == null ) {
            return;
        }
        switch (msg.messageType()) {
        case LOGICAL_NAME:
            this.localServerInfo = new NodeInfo((LogicalNameLocalMsg) msg);
            break;
        case RMI_REGISTRY_PORT_NUM:
            createAndBindLocalProcess((RmiRegistryPortNumLocalMsg) msg);
            serverInitialised = true;
            break;
        case EMPTY_MSG:
            break;
        default:
            logger.info("Don't know how to initial local handle msg {}", msg.getClass().getSimpleName());
        }
    }
    
    private void createAndBindLocalProcess(final RmiRegistryPortNumLocalMsg portNumMsg) {
        try {
//            TODO check for clashes if another process on localhost has started
            rmiPort = portNumMsg.getPort();
            localRegistryBinder = new LocalRegistryBinder(rmiPort, incomingRmiRequestThreadCommunicator);
            localRegistryBinder.bindProcessCommunicator(leaderElection);
            localServerInfo.setRmiPort(rmiPort);
            leaderElection.addLocalNode(localServerInfo);
        } catch (RemoteException e) {
            logger.info("StackTrace:", e);
        }
    }

    private void sleepForABit() {
        try {
            Thread.sleep(1 * 2 * 1000);
        } catch (InterruptedException e) {
            logger.info("StackTrace:", e);
        }
    }

    public void stopProcessing() {
        stopProcess.set(true);
    }

    public void setLocalClientThreadIncomingMsgCommunicator(ThreadCommunicator<LocalMsg> threadCommunicator) {
        this.localClientThreadIncomingMsgCommunicator = threadCommunicator;
    }

}
