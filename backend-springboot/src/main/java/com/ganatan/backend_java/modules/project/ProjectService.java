package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends GenericService<Project, String> {

    private final ProjectRepositoryInterface repository;

    public ProjectService(ProjectRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Project createItem(Project project) {
        if (repository.existsByName(project.getName())) {
            throw new AlreadyExistsException("Project with name '" + project.getName() + "' already exists");
        }
        return super.createItem(project);
    }
}