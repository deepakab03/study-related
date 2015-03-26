package com.deepak.studyrelated.ms.server;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadCommunicator<T> {

    private Queue<T> commandQueue = new LinkedList<T>();
    
    public ThreadCommunicator() {
    }

    public synchronized void sendMsg(T msg) {
        commandQueue.add(msg);
    }
    
    public synchronized T pollMsg() {
        if (commandQueue.isEmpty()) {
            return null;
        }
        
        return commandQueue.poll();
    }
}
