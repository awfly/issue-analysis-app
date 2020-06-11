package com.amgchv.services.impl;

import com.amgchv.models.Project;
import com.amgchv.models.jira.response.project.ProjectResponse;
import com.amgchv.repositories.ProjectJpaRepository;
import com.amgchv.services.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final String GET_PROJECTS_ENDPOINT = "/rest/api/3/project?expand=description";
    private final ProjectJpaRepository projectJpaRepository;
    private final RestOperations restOperations;

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

    @Override
    @Transactional
    public void deleteProjectByProjectName(String projectName) {
        projectJpaRepository.deleteByName(projectName);
    }

    @SneakyThrows
    @Override
    public void synchronize() {
        ProjectResponse[] responseJson = restOperations.getForObject(GET_PROJECTS_ENDPOINT, ProjectResponse[].class);
        for (ProjectResponse response : responseJson) {
            if (projectJpaRepository.getByJiraProjectKey(response.getKey()) != null) continue;
            Project project = new Project(response.getName(), response.getKey(), response.getDescription());
            projectJpaRepository.save(project);
        }
    }
}
