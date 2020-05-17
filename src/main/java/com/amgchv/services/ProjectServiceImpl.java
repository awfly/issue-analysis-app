package com.amgchv.services;

import com.amgchv.models.Project;
import com.amgchv.repositories.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectJpaRepository projectJpaRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectJpaRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectJpaRepository.findById(id).get();
    }

    @Override
    public void createProject(Project project) {
        projectJpaRepository.save(project);
    }
}
