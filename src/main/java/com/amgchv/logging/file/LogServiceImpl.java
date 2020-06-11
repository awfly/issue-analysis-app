package com.amgchv.logging.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private static final String EXCEPTION_PATTERN_REGEXP = "([a-zA-Z.]*([a-zA-Z]Exception))";

    private static final String LOG_PATH = "C:\\univer\\demo\\logs\\spring-boot-logger-log4j2.log";

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
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        StringBuilder stringBuilder = new StringBuilder();
        int startIndex = 0;
        try (Stream<String> stream = Files.lines(Paths.get(LOG_PATH))) {
            String[] stringArray = stream.toArray(String[]::new);
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i].startsWith("2020")) {
                    LocalDateTime dateTime = LocalDateTime.parse(stringArray[i].substring(0, 19));
                    if (startDateTime.isBefore(dateTime)) {
                        startIndex = i;
                        break;
                    }
                }
            }
            if (startIndex == 0) {
                    for (int i = 0; i < stringArray.length; i++) {
                        stringBuilder.append(stringArray[i]).append("\n");
                    }
            } else {
                for (int i = startIndex; i < stringArray.length; i++) {
                    stringBuilder.append(stringArray[i]).append("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public List<String> getExceptionsFromLog(String log) {
        List<String> exceptions = new ArrayList<>();
        Matcher matcher = Pattern.compile(EXCEPTION_PATTERN_REGEXP).matcher(log);
        while (matcher.find()) {
            exceptions.add(matcher.group());
        }
        return exceptions.stream()
                .distinct()
                .collect(Collectors.toList());
    }

}
