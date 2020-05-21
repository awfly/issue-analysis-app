package com.amgchv.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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

    private String stepsToReproduce;

    private String keywords;

    private String stacktrace;

    @OneToOne
    private Testcase testcase;

    @ManyToOne
    private User postedBy;
}
