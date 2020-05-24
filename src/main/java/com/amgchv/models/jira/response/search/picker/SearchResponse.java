package com.amgchv.models.jira.response.search.picker;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    List<SearchResult> sections;
}
