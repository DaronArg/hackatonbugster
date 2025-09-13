package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.modules.project.dto.ProjectStats;
import com.ganatan.backend_java.modules.project.dto.VoiceConfig;
import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

    @Transactional(readOnly = true)
    public Page<Project> getProjectsByUser(String userId, Pageable pageable) {
        return repository.findByCreatedBy(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Project> searchProjectsByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional
    public Project updateVoiceConfig(String id, VoiceConfig voiceConfig) {
        Project project = getItemById(id);
        // Assuming Project entity has fields for voice config
        // project.setVoiceId(voiceConfig.voiceId());
        // project.setStability(voiceConfig.stability());
        // project.setSimilarityBoost(voice.similarityBoost());
        return repository.save(project);
    }

    @Transactional(readOnly = true)
    public Project getProjectWithDetails(String id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Project not found"));
    }

    @Transactional
    public Project cloneProject(String id, String newName) {
        Project originalProject = getProjectWithDetails(id);
        Project clonedProject = new Project();
        clonedProject.setName(newName);
        // copy other properties
        return repository.save(clonedProject);
    }

    @Transactional(readOnly = true)
    public ProjectStats getProjectStatistics(String id) {
        Project project = getProjectWithDetails(id);
        // Implement logic to calculate statistics
        return new ProjectStats(0, 0, 0, null, 0);
    }
}