package com.amgchv.models.jira.response.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProjectResponse {
    private String id;
    private String key;
    private String name;
    private String description;
}
