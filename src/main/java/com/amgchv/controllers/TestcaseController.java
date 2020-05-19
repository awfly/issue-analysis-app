package com.amgchv.controllers;

import com.amgchv.models.Testcase;
import com.amgchv.models.TestcaseProcess;
import com.amgchv.models.User;
import com.amgchv.security.UserPrincipal;
import com.amgchv.services.TestcaseProcessService;
import com.amgchv.services.TestcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TestcaseController {

    private final TestcaseService testcaseService;
    private final TestcaseProcessService testcaseProcessService;

    @PostMapping(value = "testcases/new")
    public String addTestcase(Testcase testcase, String scenarioId) {
        testcaseService.addTestcase(testcase, scenarioId);
        return "redirect:/scenarios/scenario/" + scenarioId;
    }

    @GetMapping(value = "testcases/testcase/{id}")
    public String testcase(@PathVariable String id, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Testcase testcase = testcaseService.getTestcaseById(Long.valueOf(id));
        boolean inProgress = testcaseProcessService.getTestcaseProcessByUserAndTestcase(userPrincipal.getUser(), testcase) != null;
        model.addAttribute("testcase", testcase);
        model.addAttribute("inProgress", inProgress);
        return "/testcase/testcase";
    }

    @GetMapping(value = "testcases/testcase/start/{id}")
    public String startTestcase(@PathVariable String id, @AuthenticationPrincipal UserPrincipal currentUser) {
        Testcase testcase = testcaseService.getTestcaseById(Long.valueOf(id));
        User user = currentUser.getUser();
        testcaseProcessService.startTestCase(user, testcase);
        return "redirect:/testcases/testcase/" + id;
    }

    @GetMapping(value = "testcases/testcase/passed/{id}")
    public String passedTestcase(@PathVariable String id, @AuthenticationPrincipal UserPrincipal currentUser) {
        Testcase testcase = testcaseService.getTestcaseById(Long.valueOf(id));
        User user = currentUser.getUser();
        testcaseProcessService.stopTestcase(user, testcase);
        return "redirect:/testcases/testcase/" + id;
    }
}