package com.amgchv.controllers;

import com.amgchv.logging.file.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "public")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping(value = "/stacktrace")
    public String getStacktrace(@RequestParam String startDate) {
        String replacedDate  = startDate.replace('_', ' ');
        return logService.getLogsStartedFrom(replacedDate);
    }
}
