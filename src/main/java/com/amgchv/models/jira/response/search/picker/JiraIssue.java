package com.amgchv.models.jira.response.search.picker;

import com.amgchv.models.jira.response.search.jql.Field;
import lombok.Data;

@Data
public class JiraIssue {
    private String id;
    private String key;
    private String keyHtml;
    private String img;
    private String summary;
    private String summaryText;
    private Field fields;
}
