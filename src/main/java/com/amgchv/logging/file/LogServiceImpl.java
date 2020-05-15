package com.amgchv.logging.file;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class LogServiceImpl implements LogService {

    private static final String LOG_PATH = "logs/spring-boot-logger-log4j2.log";

    @Override
    public String getAllLogs() {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(LOG_PATH))) {
            stream.forEach(line -> stringBuilder.append(line).append('\n'));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
