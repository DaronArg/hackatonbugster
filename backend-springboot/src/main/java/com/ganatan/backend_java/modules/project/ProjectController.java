package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.controllers.GenericController;
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