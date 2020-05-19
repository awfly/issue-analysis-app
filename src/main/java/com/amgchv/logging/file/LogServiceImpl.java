package com.amgchv.logging.file;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
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

    @Override
    public String getLogsStartedFrom(String startDate) {
        StringBuilder stringBuilder = new StringBuilder();
        int startIndex = 0;
        try (Stream<String> stream = Files.lines(Paths.get(LOG_PATH))) {
            String[] stringArray = stream.toArray(String[]::new);
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i].startsWith(startDate)) {
                    startIndex = i;
                    break;
                }
            }

            for (int i = startIndex; i < stringArray.length; i++){
                stringBuilder.append(stringArray[i]).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
