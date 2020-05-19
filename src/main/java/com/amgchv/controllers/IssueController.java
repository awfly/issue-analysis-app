package com.amgchv.controllers;

import com.amgchv.models.Issue;
import com.amgchv.models.Testcase;
import com.amgchv.models.TestcaseProcess;
import com.amgchv.security.UserPrincipal;
import com.amgchv.services.IssueService;
import com.amgchv.services.TestcaseProcessService;
import com.amgchv.services.TestcaseService;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    private final TestcaseProcessService testcaseProcessService;
    private final TestcaseService testcaseService;
    private final JiraRestClient jiraRestClient;
    private final RestOperations restOperations;

    @GetMapping("/issues/")
    public String list(Model model) {
        List<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);
        return "issue/list";
    }

    @GetMapping("/issue/new")
    public String newIssue() {
        return "issue/newIssue";
    }

    @GetMapping("/issue/newJiraIssue")
    public String newJiraIssueIssue() {
        return "issue/newJiraIssue";
    }

    @PostMapping("/issue/newJiraIssue")
    public String createJiraIssue(@RequestParam("subject") String subject,
                                  @RequestParam("description") String description,
                                  @RequestParam("stacktrace") String stacktrace) {

        IssueRestClient issueRestClient = jiraRestClient.getIssueClient();
        IssueInput newIssue = new IssueInputBuilder()
                .setProjectKey("SD")
                .setSummary(subject)
                .setDescription(description)
                .setIssueTypeId(10005L)
                .setFieldInput(new FieldInput("customfield_10027", stacktrace))
                .build();
        String issueId = issueRestClient.createIssue(newIssue).claim().getKey();
        return "redirect:https://jira4cloud.atlassian.net/browse/" + issueId;
    }

    @GetMapping("/issue/deleteJiraIssue")
    public String deleteJiraIssue() {
        return "issue/deleteJiraIssue";
    }

    @PostMapping(value = "/issue/deleteJiraIssue")
    public String deleteIssue(@RequestParam String key) {
        restOperations.delete("/rest/api/3/issue/" + key);
        return "issue/deleteJiraIssue";
    }

    @PostMapping("/issues/")
    public String create(Issue issue, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        issueService.createIssue(issue, userPrincipal);
        return "redirect:/issues/";
    }

    @GetMapping("/issues/issue/{id}")
    public String issue(Model model, @PathVariable String id) {
        Issue issue = issueService.getIssueById(id);
        model.addAttribute("issue", issue);
        return "issue/issue";
    }

    @GetMapping("/issue/new/{testcase}")
    public String createReport(@PathVariable Testcase testcase, @AuthenticationPrincipal UserPrincipal userPrincipal,
                               Model model) {
        TestcaseProcess testcaseProcess = testcaseProcessService.getTestcaseProcessByUserAndTestcase(userPrincipal.getUser(), testcase);
        LocalDateTime startDate = testcaseProcess.getStartDate();
        model.addAttribute("startDate", startDate.truncatedTo(ChronoUnit.MINUTES).toString().replace('T', '_'));
        return "issue/newJiraIssue";
    }
}
