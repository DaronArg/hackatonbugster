package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.models.BaseEntity;
// import com.ganatan.backend_java.modules.user.User;
import com.ganatan.backend_java.modules.topic.Topic;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
public class Project extends BaseEntity<String> {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "voice_id")
    private String voiceId;

    @Column(name = "voice_category")
    private String voiceCategory;

    @Column(name = "voice_name")
    private String voiceName;

    /**
     * The MIME type of the file.
     */
    @Column(name = "content_type")
    private String contentType;
    /**
     * The URL of the file.
     */
    @Column(name = "file_url")
    private String fileUrl;

    // Relación OneToMany con Topic
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    public Project(ProjectRequestDTO project) {
        this.name = project.getName();
        this.description = project.getDescription();
        this.voiceId = project.getVoiceId();
        this.voiceCategory = project.getVoiceCategory();
        this.voiceName = project.getVoiceName();
        this.topics = project.getTopics();
        this.name = project.getName();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}