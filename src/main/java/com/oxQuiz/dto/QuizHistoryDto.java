package com.oxQuiz.dto;

import com.oxQuiz.config.UserInitialize;
import com.oxQuiz.entity.QuizHistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizHistoryDto implements UserInitialize {
    @NotNull
    private Long id;
    @NotNull
    @Column(length = ID_SIZE, nullable = false)
    private String userId;
    @NotNull
    private Long questionId;
    @NotNull
    @Column(nullable = false)
    private String question;
    @NotNull
    private Boolean answer;
    @NotNull
    @Column(nullable = false)
    private Boolean isCorrect;
    @NotNull
    private Timestamp quizTime;

    public static QuizHistoryDto fromEntity(QuizHistoryEntity entity) {
        return new QuizHistoryDto(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestionId(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getIsCorrect(),
                entity.getQuizTime());
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static QuizHistoryEntity toDto(QuizHistoryDto dto) {
        QuizHistoryEntity entity = new QuizHistoryEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setQuestionId(dto.getQuestionId());
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());
        entity.setIsCorrect(dto.getIsCorrect());
        entity.setQuizTime(dto.getQuizTime());
        return entity;
    }
}
