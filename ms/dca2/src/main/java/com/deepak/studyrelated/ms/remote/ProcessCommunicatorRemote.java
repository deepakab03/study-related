package com.deepak.studyrelated.ms.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.deepak.studyrelated.ms.messages.remote.ElectedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.ElectionRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HandShakeRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.HeartBeatRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NewNodesAddedRemoteMessage;
import com.deepak.studyrelated.ms.messages.remote.NodeRemovedRemoteMessage;

/**
 * @author Deepak Abraham
 */
public interface ProcessCommunicatorRemote extends Remote {

    String PROCESS_COMMUNICATOR_REMOTE_NAME = "processCommunicator";

    HeartBeatRemoteMessage heartBeat() throws RemoteException;

    HandShakeRemoteMessage handShake(HandShakeRemoteMessage handShakeMessage) throws RemoteException;

    void sendElectionMessage(ElectionRemoteMessage electionRemoteMessage) throws RemoteException;

    void sendElectedMessage(ElectedRemoteMessage electedRemoteMessage) throws RemoteException;

    void sendNewNodesAddedMessage(NewNodesAddedRemoteMessage newNodesAddedRemoteMessage) throws RemoteException;

    void sendHeartBeatMessage(HeartBeatRemoteMessage heartbeatremotemessage) throws RemoteException;

    void sendNodeRemovedMsg(NodeRemovedRemoteMessage nodeRemovedRemoteMessage) throws RemoteException;
}
