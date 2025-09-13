package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.modules.project.dto.ProjectStats;
import com.ganatan.backend_java.modules.project.dto.VoiceConfig;
import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/projects")
public class ProjectController extends GenericController<Project, String> {

    private final ProjectService projectService;

    public ProjectController(ProjectService service) {
        super(service);
        this.projectService = service;
    }

    @GetMapping("/user/{userId}")
    public Page<Project> getProjectsByUser(@PathVariable String userId, Pageable pageable) {
        return projectService.getProjectsByUser(userId, pageable);
    }

    @GetMapping("/search")
    public Page<Project> searchProjectsByName(@RequestParam String name, Pageable pageable) {
        return projectService.searchProjectsByName(name, pageable);
    }

    @PatchMapping("/{id}/voice")
    public Project updateVoiceConfig(@PathVariable String id, @RequestBody VoiceConfig voiceConfig) {
        return projectService.updateVoiceConfig(id, voiceConfig);
    }

    @GetMapping("/{id}/details")
    public Project getProjectWithDetails(@PathVariable String id) {
        return projectService.getProjectWithDetails(id);
    }

    @PostMapping("/{id}/clone")
    public Project cloneProject(@PathVariable String id, @RequestBody String newName) {
        return projectService.cloneProject(id, newName);
    }

    @GetMapping("/{id}/statistics")
    public ProjectStats getProjectStatistics(@PathVariable String id) {
        return projectService.getProjectStatistics(id);
    }
    @PostMapping("/impl")
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody ProjectRequestDTO item)  {
        try {
            return projectService.createItem(item);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }
    }
}