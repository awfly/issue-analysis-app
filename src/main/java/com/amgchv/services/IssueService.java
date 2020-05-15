package com.amgchv.services;

import com.amgchv.models.Issue;
import com.amgchv.security.UserPrincipal;

import java.util.List;

public interface IssueService {

    List<Issue> findAll();

    void createIssue(Issue issue, UserPrincipal account);

    Issue getIssueById(String id);

}
