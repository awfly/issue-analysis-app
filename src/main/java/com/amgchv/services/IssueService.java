package com.amgchv.services;

import com.amgchv.models.Issue;
import com.amgchv.security.UserPrincipal;

import java.util.List;

public interface IssueService {

    List<Issue> findAll();

    void createIssue(Issue issue, UserPrincipal account);

    void createIssue(Issue issue, UserPrincipal account, String testcaseId);

    Issue getIssueById(String id);

    void deleteIssueById(Long issueId);

    void updateSentToJiraStatus(Long issueId, String jiraIssueId);
}
