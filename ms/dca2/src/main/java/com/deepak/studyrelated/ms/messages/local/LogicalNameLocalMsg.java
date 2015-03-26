package com.deepak.studyrelated.ms.messages.local;

import org.apache.commons.lang3.StringUtils;

import com.deepak.studyrelated.ms.exception.InvalidInputProcessException;

public class LogicalNameLocalMsg extends LocalMsg {

    private String logicalName;

    public LogicalNameLocalMsg(final String logicalName) throws InvalidInputProcessException {
        super(LocalMsgType.LOGICAL_NAME);
        this.logicalName = StringUtils.trimToEmpty(logicalName).toUpperCase();
        if ("".equals(logicalName)) {
            throw new InvalidInputProcessException("Logical name cannot be empty");
        }
    }

    public String getLogicalName() {
        return logicalName;
    }

}
