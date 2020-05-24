package com.amgchv.services;

import com.amgchv.models.Testcase;
import com.amgchv.models.TestcaseProcess;
import com.amgchv.models.User;

public interface TestcaseProcessService {

    void startTestCase(User user, Testcase testcase);

    void stopTestcase(User user, Testcase testcase);

    TestcaseProcess getTestcaseProcessByUserAndTestcase(User user, Testcase testcase);

    void stopTestcase(Long testcaseProcessId);
}
