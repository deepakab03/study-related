package com.deepak.studyrelated.ms.server;

import static java.lang.String.format;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepak.studyrelated.ms.messages.local.LogicalNameLocalMsg;

public class NodeInfo implements Serializable, Comparable<NodeInfo> {
    private static final Logger logger = LoggerFactory.getLogger(NodeInfo.class);
    
    private static final long serialVersionUID = 1L;
    
    private final String logicalName;
    private final Integer uid = (int) (Math.random() * 1000);
    private Integer rmiPort;
    private InetAddress hostIp;
    private boolean participant = false;
    
    public NodeInfo(LogicalNameLocalMsg logicalNameLocalMsg) {
        this.logicalName = logicalNameLocalMsg.getLogicalName();
        try {
            hostIp = Inet4Address.getByName("127.127.0.1");
        } catch (UnknownHostException e) {
           logger.info("StackTrace:", e);
          
        }
    }

    public String getLogicalName() {
        return logicalName;
    }

    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }

    public int getRmiPort() {
        return rmiPort;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((logicalName == null) ? 0 : logicalName.hashCode());
        result = prime * result + ((uid == null) ? 0 : uid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NodeInfo other = (NodeInfo) obj;
        if (logicalName == null) {
            if (other.logicalName != null) return false;
        } else if (!logicalName.equals(other.logicalName)) return false;
        if (uid == null) {
            if (other.uid != null) return false;
        } else if (!uid.equals(other.uid)) return false;
        return true;
    }
    
    @Override
    public int compareTo(NodeInfo other) {
        int compare = logicalName.compareTo(other.logicalName);
        if (compare == 0) {
            //same logical name given to two in 2 processes
            if (!uid.equals(other.uid) ) {
                compare = uid.compareTo(other.uid);
            } else if (!hostIp.equals(other.hostIp)) {
                logger.trace("Same Uid {}, present for two process, using inetaddr to find which is 'greater'", uid);
                hostIp.getHostAddress().compareTo(other.hostIp.getHostAddress());
            } else {
                logger.trace("IP addr also same, using port number to resolve");
                compare = rmiPort.compareTo(other.rmiPort);
            }
        }
        
        return compare;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerInfo [logicalName=");
        builder.append(logicalName);
        builder.append(", uid=");
        builder.append(uid);
        builder.append(", rmiPort=");
        builder.append(rmiPort);
        builder.append(", hostIp=");
        builder.append(hostIp);
        builder.append("]");
        return builder.toString();
    }
    
    public void addShortString(StringBuilder sb) {
        sb.append(format("%s/%s", logicalName, uid));
    }

    public InetAddress getHostIp() {
        return hostIp;
    }

    public Integer getUid() {
        return uid;
    }

    public boolean isParticipant() {
        return participant;
    }

    public void setParticipant(boolean participant) {
        this.participant = participant;
    }

    public String getNodeId() {
        return getLogicalName() + "/" + getUid();
    }

}
