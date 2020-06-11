package com.amgchv.services.impl;

import com.amgchv.logging.file.LogService;
import com.amgchv.models.Issue;
import com.amgchv.models.Testcase;
import com.amgchv.models.User;
import com.amgchv.repositories.IssueJpaRepository;
import com.amgchv.repositories.UserJpaRepository;
import com.amgchv.security.UserPrincipal;
import com.amgchv.services.FindJiraIssueService;
import com.amgchv.services.IssueService;
import com.amgchv.services.TestcaseService;
import com.amgchv.ticket.description.TicketDescriptionGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueJpaRepository issueJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final TestcaseService testcaseService;
    private final LogService logService;
    private final FindJiraIssueService findJiraIssueService;

    @PreAuthorize("hasAuthority('readIssue')")
    @Transactional(readOnly = true)
    public List<Issue> findAll() {
        return issueJpaRepository.findAll();
    }

    @PreAuthorize("hasAuthority('writeIssue')")
    @Transactional
    public void createIssue(Issue issue, UserPrincipal account) {
        User user = userJpaRepository.findByAccount(account.getUsername());
        issue.setPostedBy(user);
        issueJpaRepository.save(issue);
    }

    @Override
    public void createIssue(Issue issue, UserPrincipal account, String testcaseId) {
        Testcase testcase = testcaseService.getTestcaseById(Long.valueOf(testcaseId));
        List<String> exceptions = logService.getExceptionsFromLog(issue.getStacktrace());
        String projectId = testcase.getScenario().getProject().getJiraProjectKey();
        User user = userJpaRepository.findByAccount(account.getUsername());
        issue.setPostedBy(user);
        issue.setTestcase(testcase);
        issue.setExceptions(exceptions);
        List<String> similarIssueByExceptions = findJiraIssueService.getIssuesFromJiraByExceptions(issue.getExceptions(), projectId);
        List<String> similarIssueByKeywords = findJiraIssueService.getIssuesFromJiraByKeywords(issue.getKeywordsAsList(), projectId);
        issue.setSimilarIssuesByException(similarIssueByExceptions);
        issue.setSimilarIssuesByKeywords(similarIssueByKeywords);
        String changedDescription = TicketDescriptionGenerator.addAdditionalField(issue.getDescription(), issue);
        issue.setDescription(changedDescription);
        issueJpaRepository.save(issue);
    }

    @Override
    @Transactional
    public Issue getIssueById(String id) {
        return issueJpaRepository.findById(Long.valueOf(id)).get();
    }

    @Override
    public void deleteIssueById(Long issueId) {
        issueJpaRepository.deleteById(issueId);
    }

    @Override
    public void updateSentToJiraStatus(Long issueId, String jiraIssueId) {
        Issue issue = issueJpaRepository.findById(issueId).get();
        issue.setPassedToJira(true);
        issue.setJiraIssueId(jiraIssueId);
        issueJpaRepository.save(issue);
    }
}
