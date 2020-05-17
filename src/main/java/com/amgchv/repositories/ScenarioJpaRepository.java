package com.amgchv.repositories;

import com.amgchv.models.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioJpaRepository extends JpaRepository<Scenario, Long> {
}
