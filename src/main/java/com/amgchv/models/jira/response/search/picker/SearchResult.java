package com.amgchv.models.jira.response.search.picker;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private String label;
    private String sub;
    private String id;
    private List<JiraIssue> issues;
}
