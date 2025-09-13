package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.modules.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequestDTO {
    private String name;

    private String description;

    private String voiceId;

    private String voiceCategory;

    private String voiceName;

    private String contentType;

    private List<Topic> topics;

    private MultipartFile file;
}
