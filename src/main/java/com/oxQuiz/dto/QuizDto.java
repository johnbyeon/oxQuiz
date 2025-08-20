package com.oxQuiz.dto;

import com.oxQuiz.config.MessageInitialize;
import com.oxQuiz.config.QuizInitialize;
import com.oxQuiz.entity.QuizEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizDto implements QuizInitialize, MessageInitialize {
    private Long id;

    @NotBlank(message = QUESTION + REQUIRED_INPUT)
    @Size(max = QUESTION_SIZE, message = QUESTION+ QUESTION_SIZE+ IMPOSSIBLE_LENGTH_EXCEEDED)
    private String question;

    private Boolean answer;
    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static QuizDto fromEntity(QuizEntity entity) {
        return new QuizDto(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer()
        );
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static QuizEntity toDto(QuizDto dto) {
        QuizEntity entity = new QuizEntity();
        entity.setId(dto.getId());
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());
        return entity;
    }
}
