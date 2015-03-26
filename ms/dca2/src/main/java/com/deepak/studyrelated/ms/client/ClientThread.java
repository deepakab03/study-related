package com.deepak.studyrelated.ms.client;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.exception.InvalidInputProcessException;
import com.deepak.studyrelated.ms.exception.ProcessExitException;
import com.deepak.studyrelated.ms.messages.local.LocalMsg;
import com.deepak.studyrelated.ms.messages.local.OutputRingConfigurtion;
import com.deepak.studyrelated.ms.messages.local.RemoteServerInfoLocalMsg;
import com.deepak.studyrelated.ms.messages.local.LogicalNameLocalMsg;
import com.deepak.studyrelated.ms.messages.local.RmiRegistryPortNumLocalMsg;
import com.deepak.studyrelated.ms.messages.local.TriggerElectionStartMsg;
import com.deepak.studyrelated.ms.server.ThreadCommunicator;

/**
 * @author Deepak Abraham
 * 
 */
public class ClientThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ClientThread.class);

    private static final int DEFAULT_RMI_PORT = 5000;

    private final ThreadCommunicator<LocalMsg>  threadCommunicator;
    
    private final AtomicBoolean shouldStopProcess = new AtomicBoolean(false);
    private BufferedReader reader;

    public ClientThread(ThreadCommunicator<LocalMsg> threadCommunicator) {
        super("clientThread");
        this.threadCommunicator = threadCommunicator;
    }

    public void run() {
        logger.info("Starting client thread");
        reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Please enter a unique logical name for this process, eg: A or B or C etc, or enter quit/q to exit");
            String logicalNameMsg = readInputFromUserCheckingForExitMsg();
            threadCommunicator.sendMsg(new LogicalNameLocalMsg(logicalNameMsg));
            logger.info("Please input the port number at which the rmi registry will be bound "
                    + "(Enter to choose default {}) or quit/q to exit", DEFAULT_RMI_PORT);
            String portNumString = readInputFromUserCheckingForExitMsg();
            if (StringUtils.isBlank(portNumString)) {
                portNumString = DEFAULT_RMI_PORT + "";
            }
            int rmiPort = Integer.parseInt(portNumString);
            threadCommunicator.sendMsg(new RmiRegistryPortNumLocalMsg(rmiPort));
            sleepForABit(5);
            
            while (!shouldStopProcess.get()) {
                try {
                    printRepeatingMessageAndWaitForInput();
                    
                } catch (InvalidInputProcessException e) {
                   logger.info("Problem in input: " + e.getMessage() + ", please try again");
                }
            }

        } catch (ProcessExitException e) {
            logger.info(e.getMessage());
        } catch (InvalidInputProcessException e1) {
            logger.error(e1.getMessage());
        } finally {
            shouldStopProcess.set(true);
        }
    }

    private void getRemoteServerInfoAndPassToServerThread() throws ProcessExitException, InvalidInputProcessException {
        printEnterRemoteHostDetailsMsg();
        String otherProcessHostNameAndPort = readInputFromUserCheckingForExitMsg();
        threadCommunicator.sendMsg(new RemoteServerInfoLocalMsg(otherProcessHostNameAndPort));
        //wait 5 seconds server to setup rmi service so that our sysout doesn't interfere with server ms in console
        sleepForABit(5);
    }

    private void sleepForABit(int numSecondsToSleep)  {
        try {
            Thread.sleep( numSecondsToSleep * 1000);
        } catch (InterruptedException e) {
            logger.info("StackTrace:", e);
        }
    }

    private void printRepeatingMessageAndWaitForInput() throws ProcessExitException, InvalidInputProcessException {
        StringBuilder sb = new StringBuilder();
        String userOptionString = "1/2/3";
        sb.append("Enter one of the following " + userOptionString + " :\n");
        sb.append("Enter 1 - to enter details of another node / process\n");
        sb.append("Enter 2 - to print available node configuration at this node\n");
        sb.append("Enter 3 - to trigger leader election among the group of nodes\n");
        logger.info(sb.toString());
        displayProcessExitMsg();
        String optionEntered = readInputFromUserCheckingForExitMsg();
        try {
            int numOption = Integer.parseInt(optionEntered);
            switch (numOption) {
            case 1: //remote host details
                getRemoteServerInfoAndPassToServerThread();
                break;
            case 2:
                threadCommunicator.sendMsg(new OutputRingConfigurtion());
                sleepForABit(5);
                break;
            case 3:
                threadCommunicator.sendMsg(new TriggerElectionStartMsg());
                sleepForABit(20);
                break;
                default:
                    throw new IllegalStateException("Don't know how to handle option " + numOption);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid option number {} entered, please enter {}", userOptionString);
            printRepeatingMessageAndWaitForInput();
        }
    }

    private void printEnterRemoteHostDetailsMsg() {
        logger.info("Enter the hostname or IP of another node and the port number separated by a space, "
                + "eg: 'localhost(or l) 5010' or '192.168.109.2 5020'");
    }

    private void displayProcessExitMsg() {
        logger.info("Or Enter quit/q/exit/e to exit: ");
    }

    private String readInputFromUserCheckingForExitMsg() throws ProcessExitException {
        try {
            System.out.print("Enter input:>");
            String line = trimToEmpty(reader.readLine());
            if ("exit".equalsIgnoreCase(line) || "e".equalsIgnoreCase(line) || "quit".equalsIgnoreCase(line) || "q".equalsIgnoreCase(line)) {
                throw new ProcessExitException("Stopping process for input " + line);
            }
            return line;
        } catch (IOException e) {
            logger.info("StackTrace:", e);
            ;
        }
        return "Error while gettign input";
    }

    public boolean shouldStopProcess() {
        return shouldStopProcess.get();
    }

}
