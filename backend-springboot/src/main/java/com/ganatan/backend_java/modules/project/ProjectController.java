package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController extends GenericController<Project, String> {
    public ProjectController(ProjectService service) {
        super(service);
    }
}