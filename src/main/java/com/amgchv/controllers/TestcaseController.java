package com.amgchv.controllers;

import com.amgchv.models.Testcase;
import com.amgchv.services.TestcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TestcaseController {

    private final TestcaseService testcaseService;

    @PostMapping(value = "testcases/new")
    public String addTestcase(Testcase testcase, String scenarioId) {
        testcaseService.addTestcase(testcase, scenarioId);
        return "redirect:/scenarios/scenario/" + scenarioId;
    }

    @GetMapping(value = "testcases/testcase/{id}")
    public String testcase(@PathVariable String id, Model model) {
        Testcase testcase = testcaseService.getTestcaseById(Long.valueOf(id));
        model.addAttribute("testcase", testcase);
        return "/testcase/testcase";
    }
}
