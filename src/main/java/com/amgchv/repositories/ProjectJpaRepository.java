package com.amgchv.repositories;

import com.amgchv.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
    void deleteByName(String projectName);
}
