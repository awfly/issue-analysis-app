package com.amgchv.models.jira.response.search.jql;

import com.amgchv.models.jira.response.search.picker.JiraIssue;
import lombok.Data;

import java.util.List;

@Data
public class IssueJqlSearchResponse {
    List<JiraIssue> issues;
}
