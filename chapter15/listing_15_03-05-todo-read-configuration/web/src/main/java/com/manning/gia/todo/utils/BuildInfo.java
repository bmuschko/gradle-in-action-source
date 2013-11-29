package com.manning.gia.todo.utils;

import java.util.Date;

public class BuildInfo {
    private String version;
    private Date timestamp;

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}