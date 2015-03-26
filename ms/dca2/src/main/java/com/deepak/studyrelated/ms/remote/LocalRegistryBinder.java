package com.deepak.studyrelated.ms.remote;

import static com.deepak.studyrelated.ms.remote.ProcessCommunicatorRemote.PROCESS_COMMUNICATOR_REMOTE_NAME;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.messages.remote.RemoteMessage;
import com.deepak.studyrelated.ms.server.LeaderElectionCircularConfiguration;
import com.deepak.studyrelated.ms.server.ThreadCommunicator;

/**
 * @author Deepak Abraham
 * 
 */
public class LocalRegistryBinder {
    private static final Logger logger = LoggerFactory.getLogger(LocalRegistryBinder.class);
    
    public int rmiPort;
    private static boolean registryCreated = false;
    private Registry registry;

    private ThreadCommunicator<RemoteMessage> incomingRmiRequestThreadCommunicator;

    public LocalRegistryBinder(final int rmiPort, final ThreadCommunicator<RemoteMessage> incomingRmiRequestThreadCommunicator) throws RemoteException {
        this.rmiPort = rmiPort;
        this.incomingRmiRequestThreadCommunicator = incomingRmiRequestThreadCommunicator;
        createLocalRegistry();
    }

    public void bindProcessCommunicator(final LeaderElectionCircularConfiguration leaderElection) {
        try {
            logger.info("Binding process communicator to " + rmiPort);
            ProcessCommunicator remote = new ProcessCommunicator(incomingRmiRequestThreadCommunicator, leaderElection);
            ProcessCommunicatorRemote stub = (ProcessCommunicatorRemote) UnicastRemoteObject.exportObject(remote, 0);
            registry.bind(PROCESS_COMMUNICATOR_REMOTE_NAME, stub);
        } catch (RemoteException e) {
             logger.info("StackTrace:", e);;
        } catch (AlreadyBoundException e) {
             logger.info("StackTrace:", e);;
        }
    }

    private void createLocalRegistry() throws RemoteException {
        if (!registryCreated) {
            registry = LocateRegistry.createRegistry(rmiPort);
            registryCreated = true;
            logger.info("Registry created on with address: " + registry.toString());
        } else {
            logger.info("Registry already created");
        }
    }


    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }

    public void stopRegistry() {
        //TODO doesn't stop properly? because of which we need to do System.exit at end of main forcefully?
        try {
            UnicastRemoteObject.unexportObject(registry, false);
        } catch (NoSuchObjectException e) {
           logger.info("StackTrace:", e);
        }
    }
}
