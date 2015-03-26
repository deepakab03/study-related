/**
 * HandShakeMessage.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.client.ClientThread;
import com.deepak.studyrelated.ms.messages.local.LocalMsg;
import com.deepak.studyrelated.ms.server.ServerThread;
import com.deepak.studyrelated.ms.server.ThreadCommunicator;

/**
 * @author Deepak Abraham
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) throws RemoteException, InterruptedException {
        final ThreadCommunicator<LocalMsg> threadCommunicator = new ThreadCommunicator<>();
        ServerThread serverThread = new ServerThread(threadCommunicator);
        ClientThread clientThread = new ClientThread(threadCommunicator);
        
        serverThread.start();
        clientThread.start();
        
        logger.info("Waiting for exit signal from user");
        while (!clientThread.shouldStopProcess()) {
            Thread.sleep(2 * 1000);
        }
        
        serverThread.stopProcessing();
        while (clientThread.isAlive() && serverThread.isAlive()) {
            logger.info("Waiting for client and server threads to terminate..");
            Thread.sleep(1 * 1000);
        }
        logger.info("Stopped client and server threads, exiting process..");
        System.exit(0);
    }
}
