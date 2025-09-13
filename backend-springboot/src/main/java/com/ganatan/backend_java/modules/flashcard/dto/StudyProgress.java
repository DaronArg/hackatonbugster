package com.ganatan.backend_java.modules.flashcard.dto;

import java.time.LocalDate;
import java.util.List;

public record StudyProgress(
        int totalFlashcards,
        int studiedToday,
        int dueToday,
        int streak,
        double averageAccuracy,
        long totalStudyTime,
        List<WeeklyProgress> weeklyProgress
) {

    public record WeeklyProgress(LocalDate date, int cardsStudied, long timeSpent) {
    }
}
