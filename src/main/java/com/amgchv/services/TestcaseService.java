package com.amgchv.services;

import com.amgchv.models.Testcase;

public interface TestcaseService {
    void addTestcase(Testcase testcase, String scenarioId);
    Testcase getTestcaseById(Long id);
}
