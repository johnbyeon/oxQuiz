package com.oxQuiz.dto;

import com.oxQuiz.config.UserInitialize;
import com.oxQuiz.entity.QuizHistoryEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizHistoryDto implements UserInitialize {
    @NotNull
    private Long id;
    @NotNull
    @Column(length = ID_SIZE, nullable = false)
    private String userId;
    @NotNull
    @Column(nullable = false)
    private Long quizID;
    @NotNull
    @Column(nullable = false)
    private Boolean isCorrect;

    public static QuizHistoryDto fromEntity(QuizHistoryEntity entity) {
        return new QuizHistoryDto(
                entity.getId(),
                entity.getUserId(),
                entity.getQuizID(),
                entity.getIsCorrect());
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static QuizHistoryEntity toDto(QuizHistoryDto dto) {
        QuizHistoryEntity entity = new QuizHistoryEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setQuizID(dto.getQuizID());
        entity.setIsCorrect(dto.getIsCorrect());
        return entity;
    }
}
