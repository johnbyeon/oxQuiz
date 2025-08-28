package com.oxQuiz.dto;

import com.oxQuiz.config.MessageInitialize;
import com.oxQuiz.config.QuizInitialize;
import com.oxQuiz.config.UserInitialize;
import com.oxQuiz.entity.QuizEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDto implements QuizInitialize, UserInitialize,MessageInitialize {
    private Long id;

    @NotBlank(message = QUESTION + REQUIRED_INPUT)
    @Size(max = QUESTION_SIZE, message = QUESTION+ QUESTION_SIZE+ IMPOSSIBLE_LENGTH_EXCEEDED)
    private String question;

    private Boolean answer;

    @NotNull
    @Size(max = NICK_NAME_SIZE, message = NICK_NAME + NICK_NAME_SIZE + IMPOSSIBLE_LENGTH_EXCEEDED)
    private String writer;

    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static QuizDto fromEntity(QuizEntity entity) {
        return new QuizDto(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getWriter()
        );
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static QuizEntity toDto(QuizDto dto) {
        QuizEntity entity = new QuizEntity();
        entity.setId(dto.getId());
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());
        entity.setWriter(dto.getWriter());
        return entity;
    }
}
