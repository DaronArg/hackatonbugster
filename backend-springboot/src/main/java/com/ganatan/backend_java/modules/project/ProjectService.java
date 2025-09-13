package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.config.ChatGptRestTemplate;
import com.ganatan.backend_java.modules.miniO.MiniOServiceImpl;
import com.ganatan.backend_java.shared.exceptions.AlreadyExistsException;
import com.ganatan.backend_java.shared.services.GenericService;
import io.minio.GetObjectResponse;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class ProjectService extends GenericService<Project, String> {

    private final ProjectRepositoryInterface repository;

    private final MiniOServiceImpl miniOService;

    private final ChatGptRestTemplate chatGptRestTemplate;

    @Value("${minio.config.permitted-file-extensions}")
    private String[] permittedFileExtensions;


    @Value("${app.url}")
    private String appUrl;

    public ProjectService(ProjectRepositoryInterface repository,
                          MiniOServiceImpl miniOService,
                          ChatGptRestTemplate chatGptRestTemplate) {
        super(repository);
        this.repository = repository;
        this.miniOService = miniOService;
        this.chatGptRestTemplate = chatGptRestTemplate;
    }

    public Project createItem(ProjectRequestDTO project) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (repository.existsByName(project.getName())) {
            throw new AlreadyExistsException("Project with name '" + project.getName() + "' already exists");
        }

        // Nombre del archivo en MinIO
        String objectName = project.getName() + "/" + Objects.requireNonNull(project.getFile().getOriginalFilename());

        // Guardar archivo en MinIO
        miniOService.saveFile(
                project.getFile().getOriginalFilename(),
                project.getName(),
                project.getFile()
        );

        // Construir la URL pública del archivo
        String fileUrl = appUrl + "/minio/" + objectName;

        // Crear la entidad
        Project entity = new Project(project);
        entity.setContentType(project.getFile().getContentType());
        entity.setFileUrl(fileUrl);

        return super.createItem(new Project(project));
    }
}
