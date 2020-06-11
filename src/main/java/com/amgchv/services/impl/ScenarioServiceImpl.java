package com.amgchv.services.impl;

import com.amgchv.models.Project;
import com.amgchv.models.Scenario;
import com.amgchv.repositories.ProjectJpaRepository;
import com.amgchv.repositories.ScenarioJpaRepository;
import com.amgchv.services.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioJpaRepository scenarioJpaRepository;
    private final ProjectJpaRepository projectJpaRepository;

    @Override
    public void addScenario(Scenario scenario, String projectId) {
        Project project = projectJpaRepository.findById(Long.valueOf(projectId)).get();
        scenario.setProject(project);
        scenarioJpaRepository.save(scenario);
    }

    @Override
    public Scenario getScenarioById(Long id) {
        return scenarioJpaRepository.findById(id).get();
    }

    @Override
    public void deleteScenarioById(Long id) {
        scenarioJpaRepository.deleteById(id);
    }
}
