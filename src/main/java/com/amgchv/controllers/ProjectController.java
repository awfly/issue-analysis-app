package com.amgchv.controllers;

import com.amgchv.models.Project;
import com.amgchv.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value = "projects")
    public String projects(Model model) {
        List<Project> allProjects = projectService.getAllProjects();
        model.addAttribute("projects", allProjects);
        return "/project/projects";
    }

    @GetMapping(value = "projects/project/{id}")
    public String project(Model model, @PathVariable String id) {
        Project project = projectService.getProjectById(Long.valueOf(id));
        model.addAttribute("project", project);
        model.addAttribute("scenarios", project.getScenarios());
        return "/project/project";
    }

    @GetMapping(value = "projects/new")
    public String newProject() {
        return "/project/newProject";
    }

    @PostMapping(value = "projects/new")
    public String createProject(Project project) {
        projectService.createProject(project);
        return "redirect:/projects";
    }
}
