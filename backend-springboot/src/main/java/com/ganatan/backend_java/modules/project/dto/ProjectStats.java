package com.ganatan.backend_java.modules.project.dto;

import java.time.LocalDate;

public record ProjectStats(
        long topicsCount,
        long decksCount,
        long flashcardsCount,
        LocalDate lastStudiedDate,
        long totalStudyTime
) {
}
