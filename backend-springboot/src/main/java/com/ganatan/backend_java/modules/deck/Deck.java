package com.ganatan.backend_java.modules.deck;

import com.ganatan.backend_java.shared.models.BaseEntity;
import com.ganatan.backend_java.modules.topic.Topic;
import com.ganatan.backend_java.modules.flashcard.Flashcard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "decks")
@Getter
@Setter
public class Deck extends BaseEntity<String> {

    @Column(nullable = false)
    private String name;

    private String description;

    // Relación ManyToOne con Topic
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    // Relación OneToMany con Flashcard
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flashcard> flashcards;
}