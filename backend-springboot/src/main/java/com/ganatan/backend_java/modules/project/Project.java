package com.ganatan.backend_java.modules.project;

import com.ganatan.backend_java.shared.models.BaseEntity;
// import com.ganatan.backend_java.modules.user.User;
import com.ganatan.backend_java.modules.topic.Topic;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@EqualsAndHashCode(callSuper = false)
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

    // Relación OneToMany con Topic
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}