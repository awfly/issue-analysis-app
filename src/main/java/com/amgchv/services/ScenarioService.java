package com.amgchv.services;

import com.amgchv.models.Project;
import com.amgchv.models.Scenario;

public interface ScenarioService {
    void addScenario(Scenario scenario, String projectId);

    Scenario getScenarioById(Long id);

    void deleteScenarioById(Long id);
}
