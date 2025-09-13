package com.ganatan.backend_java.modules.flashcard;

import com.ganatan.backend_java.shared.models.BaseEntity;
import com.ganatan.backend_java.modules.deck.Deck;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "flashcards")
@Getter
@Setter
public class Flashcard extends BaseEntity<String> {

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    @Column(name = "last_reviewed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReviewed;

    @Column(name = "review_count")
    private int reviewCount;

    // Campos para algoritmo de repetición espaciada
    @Column(name = "easiness_factor")
    private double easinessFactor;

    @Column(name = "interval_days")
    private int interval;

    @Column(name = "next_review_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextReviewDate;

    // Relación ManyToOne con Deck
    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;
}