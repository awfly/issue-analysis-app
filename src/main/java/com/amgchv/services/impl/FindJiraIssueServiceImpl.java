package com.amgchv.services.impl;

import com.amgchv.models.jira.response.search.jql.IssueJqlSearchResponse;
import com.amgchv.models.jira.response.search.picker.SearchResponse;
import com.amgchv.services.FindJiraIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindJiraIssueServiceImpl implements FindJiraIssueService {

    private static final String SEARCH_ISSUE_ENDPOINT = "/rest/api/3/issue/picker?query={query}&currentProjectId={projectId}";
    private static final String SEARCH_BY_JQL_ISSUE_ENDPOINT = "/rest/api/3/search?jql=text~\"{exception}\"&fields=summary";

    private final RestOperations restOperations;

    @Override
    public List<String> getIssuesFromJiraByExceptions(List<String> exceptions, String projectId) {
        return getSearchByJqlResults(exceptions, projectId);
    }

    @Override
    public List<String> getIssuesFromJiraByKeywords(List<String> keywords, String projectId) {
        return getSearchResults(keywords, projectId);
    }

    private List<String> getSearchResults(List<String> searchElements, String projectId) {
        List<String> issuesFromJiraByKeywords = new ArrayList<>();

        for (String keyword : searchElements) {
            SearchResponse searchResponse = restOperations.getForObject(SEARCH_ISSUE_ENDPOINT,
                    SearchResponse.class, keyword, projectId);
            if (searchResponse != null) {
                searchResponse.getSections()
                        .forEach(section -> section.getIssues().stream().map(jiraIssue ->
                                jiraIssue.getKey() + " : (" + jiraIssue.getSummaryText() + ")")
                                .forEach(issuesFromJiraByKeywords::add));
            }
        }

        return issuesFromJiraByKeywords;
    }


    private List<String> getSearchByJqlResults(List<String> searchElements, String projectId) {
        List<String> issuesFromJiraByKeywords = new ArrayList<>();

        for (String element : searchElements) {
            IssueJqlSearchResponse searchResponse = restOperations.getForObject(SEARCH_BY_JQL_ISSUE_ENDPOINT,
                    IssueJqlSearchResponse.class, element, projectId);
            if (searchResponse != null) {
                searchResponse.getIssues().stream().map(issue ->
                        issue.getKey() + " : (" + issue.getFields().getSummary() + ")")
                        .forEach(issuesFromJiraByKeywords::add);
            }
        }

        return issuesFromJiraByKeywords;
    }
}