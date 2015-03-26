package com.deepak.studyrelated.ms.messages.local;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.util.StringTokenizer;

import com.deepak.studyrelated.ms.exception.InvalidInputProcessException;

public class RemoteServerInfoLocalMsg extends LocalMsg {

    private String remoteHost;
    private final int port;

    public RemoteServerInfoLocalMsg(final String remoteHostAndPortSeparatedBySpace) throws InvalidInputProcessException {
        super(LocalMsgType.REMOTE_SERVER_INFO);
        final StringTokenizer tokenizer = new StringTokenizer(remoteHostAndPortSeparatedBySpace);
        if (tokenizer.countTokens() != 2) {
            throw new InvalidInputProcessException("Input doesn'match expected <hostname/IP port>");
        }
        
        final String remoteHostString = trimToEmpty(tokenizer.nextToken());
        this.remoteHost = "l".equalsIgnoreCase(remoteHostString) ? "localhost" : remoteHostString;
        if ("".equals(remoteHost)) {
            throw new InvalidInputProcessException("Remote host cannot be empty");
        }
//        if ("localhost".equalsIgnoreCase(remoteHostString)) {
//            this.remoteHost = "127.127.0.0.1";
//        }
        
        final String portNumberString = tokenizer.nextToken();
        try {
            this.port = Integer.parseInt(portNumberString);
        } catch (NumberFormatException e) {
           throw new InvalidInputProcessException("Port number enterd is not valid: " + portNumberString);
        }
        
       
    }

    public int getRmiPort() {
        return port;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public boolean isRemoteHostLocalHost() {
        return "localhost".equalsIgnoreCase(remoteHost);
    }
}
