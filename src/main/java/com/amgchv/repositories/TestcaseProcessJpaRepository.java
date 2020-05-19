package com.amgchv.repositories;

import com.amgchv.models.Testcase;
import com.amgchv.models.TestcaseProcess;
import com.amgchv.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestcaseProcessJpaRepository extends JpaRepository<TestcaseProcess, Long> {
    TestcaseProcess findByUserAndTestcase(User user, Testcase testcase);
}