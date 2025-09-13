package com.ganatan.backend_java.modules.flashcard;

import lombok.Data;

@Data
public class CreateFlashcardDto {
    
    private String question;
    private String answer;
    private String deckId;
    private DifficultyLevel difficulty; // Opcional, por defecto será MEDIUM
    
    // Constructor vacío para Spring
    public CreateFlashcardDto() {}
    
    // Constructor con parámetros básicos
    public CreateFlashcardDto(String question, String answer, String deckId) {
        this.question = question;
        this.answer = answer;
        this.deckId = deckId;
    }
    
    // Constructor completo
    public CreateFlashcardDto(String question, String answer, String deckId, DifficultyLevel difficulty) {
        this.question = question;
        this.answer = answer;
        this.deckId = deckId;
        this.difficulty = difficulty;
    }
}