package com.manning.gia.todo.utils;

import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BuildInfoReader {
    public BuildInfo read() {
        Properties props = new Properties();

        try {
            props.load(getClass().getResourceAsStream("/build-info.properties"));
            BuildInfo buildInfo = new BuildInfo();
            buildInfo.setVersion(props.getProperty("version"));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            buildInfo.setTimestamp(formatter.parse(props.getProperty("timestamp")));
            return buildInfo;
        } catch (Exception e) {
            // Ignore it
        }

        return null;
    }
}