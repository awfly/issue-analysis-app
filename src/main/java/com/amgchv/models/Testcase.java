package com.amgchv.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Testcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testcaseId;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String prerequisites;

    @NonNull
    private String testcaseSteps;

    @NonNull
    private String expectedResult;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Testcase testcase = (Testcase) o;
        return Objects.equals(testcaseId, testcase.testcaseId) &&
                Objects.equals(name, testcase.name) &&
                Objects.equals(description, testcase.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testcaseId, name, description);
    }
}
