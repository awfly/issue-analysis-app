package com.amgchv.services;

import java.util.List;

public interface FindJiraIssueService {
    List<String> getIssuesFromJiraByExceptions(List<String> exceptions, String projectId);
    List<String> getIssuesFromJiraByKeywords(List<String> keywords, String projectId);

}
