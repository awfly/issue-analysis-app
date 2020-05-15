package com.amgchv.repositories;

import com.amgchv.models.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
}
