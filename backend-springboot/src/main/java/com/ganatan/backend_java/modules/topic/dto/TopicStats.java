package com.ganatan.backend_java.modules.topic.dto;

import java.time.LocalDate;

public record TopicStats(
        long decksCount,
        long flashcardsCount,
        LocalDate lastStudiedDate,
        String averageDifficulty,
        double studyProgress
) {
}
