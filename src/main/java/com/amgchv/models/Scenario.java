package com.amgchv.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scenarioId;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "scenario")
    private Set<Testcase> testcases = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        return Objects.equals(scenarioId, scenario.scenarioId) &&
                Objects.equals(name, scenario.name) &&
                Objects.equals(description, scenario.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scenarioId, name, description);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "scenarioId=" + scenarioId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                ", testcases=" + testcases +
                '}';
    }
}
