package com.amgchv.services;

import com.amgchv.models.Testcase;
import com.amgchv.models.TestcaseProcess;
import com.amgchv.models.User;
import com.amgchv.repositories.TestcaseProcessJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TestcaseProcessServiceImpl implements TestcaseProcessService {

    private final TestcaseProcessJpaRepository testcaseProcessJpaRepository;

    @Override
    public void startTestCase(User user, Testcase testcase) {
        TestcaseProcess testcaseProcess = new TestcaseProcess(user, testcase, LocalDateTime.from(LocalDateTime.now()));
        testcaseProcessJpaRepository.save(testcaseProcess);
    }

    @Override
    public void stopTestcase(User user, Testcase testcase) {
        TestcaseProcess testcaseProcess = testcaseProcessJpaRepository.findByUserAndTestcase(user, testcase);
        testcaseProcessJpaRepository.deleteById(testcaseProcess.getTestcaseProcessId());
    }

    @Override
    public TestcaseProcess getTestcaseProcessByUserAndTestcase(User user, Testcase testcase) {
        return testcaseProcessJpaRepository.findByUserAndTestcase(user, testcase);
    }

    @Override
    public void deleteById(Long testcaseProcessId) {
        testcaseProcessJpaRepository.deleteById(testcaseProcessId);
    }
}
