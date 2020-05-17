package com.amgchv.services;

import com.amgchv.models.Scenario;
import com.amgchv.models.Testcase;
import com.amgchv.repositories.ScenarioJpaRepository;
import com.amgchv.repositories.TestcaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestcaseServiceImpl implements TestcaseService {

    private final TestcaseJpaRepository testcaseJpaRepository;
    private final ScenarioJpaRepository scenarioJpaRepository;

    @Override
    public void addTestcase(Testcase testcase, String scenarioId) {
        Scenario scenario = scenarioJpaRepository.findById(Long.valueOf(scenarioId)).get();
        testcase.setScenario(scenario);
        testcaseJpaRepository.save(testcase);
    }

    @Override
    public Testcase getTestcaseById(Long id) {
        Testcase testcase = testcaseJpaRepository.findById(id).get();
        return testcase;
    }

}
