package com.amgchv.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Issue {
    @Id
    @GeneratedValue
    private Long issueId;

    @NonNull
    private String subject;

    @NonNull
    @Column(length = 2048)
    private String description;

    private String expectedResult;

    private String actualResult;

    private String prerequisites;

    private String stepsToReproduce;

    private String keywords;

    private boolean passedToJira;

    private String jiraIssueId;

    @Column(length = 32000)
    private String stacktrace;

    @ElementCollection
    @CollectionTable(name = "issue_exceptions")
    @Column(length=10000)
    private List<String> exceptions;

    @OneToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Testcase testcase;

    @ManyToOne
    private User postedBy;

    @ElementCollection
    @CollectionTable(name = "similar_issues_by_exception")
    private List<String> similarIssuesByException;

    @ElementCollection
    @CollectionTable(name = "similar_issues_by_keywords")
    private List<String> similarIssuesByKeywords;

    public List<String> getKeywordsAsList(){
        return Arrays.stream(StringUtils.splitPreserveAllTokens(keywords, ",")).map(String::trim).collect(Collectors.toList());

    }
}
