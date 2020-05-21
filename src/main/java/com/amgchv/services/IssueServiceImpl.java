package com.amgchv.services;

import com.amgchv.models.Issue;
import com.amgchv.models.Testcase;
import com.amgchv.models.User;
import com.amgchv.repositories.IssueJpaRepository;
import com.amgchv.repositories.UserJpaRepository;
import com.amgchv.security.UserPrincipal;
import com.atlassian.jira.rest.client.api.domain.CimIssueType;
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
        User user = userJpaRepository.findByAccount(account.getUsername());
        Testcase testcase = testcaseService.getTestcaseById(Long.valueOf(testcaseId));
        issue.setPostedBy(user);
        issue.setTestcase(testcase);
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
}
