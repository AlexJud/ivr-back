package com.indev.fsklider.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEvent {
    private EventType type;
    private String callId;
    private String systemText;
    private String abonentText;
    private Map<String, String> tokenList;
    private long timestamp;
    private String decision;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getSystemText() {
        return systemText;
    }

    public void setSystemText(String systemText) {
        this.systemText = systemText;
    }

    public String getAbonentText() {
        return abonentText;
    }

    public void setAbonentText(String abonentText) {
        this.abonentText = abonentText;
    }

    public Map<String, String> getTokenList() {
        return tokenList;
    }

    public void setTokenList(Map<String, String> tokenList) {
        this.tokenList = tokenList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    @Override
    public String toString() {
        return "ResponseEvent{" +
                "type=" + type +
                ", callId='" + callId + '\'' +
                ", systemText='" + systemText + '\'' +
                ", tokenList=" + tokenList +
                ", decision='" + decision + '\'' +
                '}';
    }
}
