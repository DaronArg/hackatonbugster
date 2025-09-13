package com.ganatan.backend_java.modules.topic;

import com.ganatan.backend_java.shared.models.BaseEntity;
import com.ganatan.backend_java.modules.project.Project;
import com.ganatan.backend_java.modules.deck.Deck;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@Setter
public class Topic extends BaseEntity<String> {

    @Column(nullable = false)
    private String title;

    private String description;

    // Relación ManyToOne con Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // Relación OneToMany con Deck
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deck> decks;
}