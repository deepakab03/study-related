package com.deepak.studyrelated.ms.messages.local;

import com.deepak.studyrelated.ms.messages.Message;


/**
 * Local messages sent from the client thread to the server thread.S
 * 
 * @author Deepak Abraham
 */
public abstract class LocalMsg implements Message<LocalMsgType> {

    private final LocalMsgType msgType;

    public LocalMsg(final LocalMsgType msgType) {
        this.msgType = msgType;
    }

    public LocalMsgType messageType() {
        return msgType;
    }
}
