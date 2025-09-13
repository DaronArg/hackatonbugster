package com.ganatan.backend_java.modules.deck.dto;

import java.time.LocalDate;

public record DeckInfo(
        long flashcardsCount,
        LocalDate lastStudiedDate,
        String averageDifficulty,
        double studyProgress,
        long dueFlashcardsCount,
        long newFlashcardsCount,
        long reviewFlashcardsCount
) {}
