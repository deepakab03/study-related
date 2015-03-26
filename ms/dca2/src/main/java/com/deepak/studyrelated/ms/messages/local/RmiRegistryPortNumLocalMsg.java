package com.deepak.studyrelated.ms.messages.local;

public class RmiRegistryPortNumLocalMsg extends LocalMsg {

    private int port;

    public RmiRegistryPortNumLocalMsg(final int port) {
        super(LocalMsgType.RMI_REGISTRY_PORT_NUM);
        this.port = port;
    }

    public int getPort() {
        return port;
    }

}
