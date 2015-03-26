package com.deepak.studyrelated.ms.remote;

import static com.deepak.studyrelated.ms.remote.ProcessCommunicatorRemote.PROCESS_COMMUNICATOR_REMOTE_NAME;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.deepak.studyrelated.ms.exception.RemoteServiceNotAvailableProcessException;
import com.deepak.studyrelated.ms.messages.local.RemoteServerInfoLocalMsg;
import com.deepak.studyrelated.ms.server.NodeInfo;

/**
 * @author Deepak Abraham
 */
public class RemoteServiceLookup {

    private String remoteHost;
    private int remotePort;
    private Registry registry;

    public RemoteServiceLookup(RemoteServerInfoLocalMsg msg) throws RemoteException, RemoteServiceNotAvailableProcessException {
        this.remoteHost = msg.getRemoteHost();
        this.remotePort = msg.getRmiPort();
        
        init();
    }
    
    public RemoteServiceLookup(NodeInfo nodeInfo) throws RemoteException, RemoteServiceNotAvailableProcessException {
        this.remoteHost = nodeInfo.getHostIp().getHostAddress();
        this.remotePort = nodeInfo.getRmiPort();
        
        init();
    }

    private void init() throws RemoteException, RemoteServiceNotAvailableProcessException {
        registry = LocateRegistry.getRegistry(remoteHost, remotePort);       
        
        if (registry == null) {
            throw new RemoteServiceNotAvailableProcessException("Cannot find remote RMI registry details at: " + remoteHost + ", port "+ remotePort);
        }
    }
    
    public ProcessCommunicatorRemote lookupRemoteProcessCommunicator() throws AccessException, RemoteException, NotBoundException {
        return (ProcessCommunicatorRemote) registry.lookup(PROCESS_COMMUNICATOR_REMOTE_NAME);
    }
}
