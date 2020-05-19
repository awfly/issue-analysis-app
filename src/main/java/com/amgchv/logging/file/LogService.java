package com.amgchv.logging.file;

public interface LogService {
    String getAllLogs();

    String getLogsStartedFrom(String startDate);
}
