package com.indev.fsklider.graph.results;

public class DialogResult {
    private String estate;
    private String reason;
    private String name;
    private String source;
    private String number;
    private boolean isSale;
    private boolean isSupport;
    private boolean isTransfer;

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isSupport() {
        return isSupport;
    }

    public void setSupport(boolean support) {
        isSupport = support;
    }

    public boolean isTransfer() {
        return isTransfer;
    }

    public void setTransfer(boolean transfer) {
        isTransfer = transfer;
    }
}
