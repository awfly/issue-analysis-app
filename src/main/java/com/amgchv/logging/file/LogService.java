package com.amgchv.logging.file;

import java.util.List;

public interface LogService {
    String getAllLogs();

    String getLogsStartedFrom(String startDate);

    List<String> getExceptionsFromLog(String log);
}
