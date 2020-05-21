package com.amgchv.services;

import com.amgchv.models.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    void createProject(Project project);

    void deleteProjectByProjectName(String projectName);
}
