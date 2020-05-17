package com.amgchv.controllers;

import com.amgchv.models.Scenario;
import com.amgchv.services.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping(value = "scenarios/new")
    public String addScenario(Scenario scenario, String projectId) {
        scenarioService.addScenario(scenario, projectId);
        return "redirect:/projects/project/" + projectId;
    }

    @GetMapping(value = "scenarios/scenario/{id}")
    public String scenario(@PathVariable String id, Model model) {
        Scenario scenario = scenarioService.getScenarioById(Long.valueOf(id));
        model.addAttribute("scenario", scenario);
        return "scenarios/scenario";
    }
}
