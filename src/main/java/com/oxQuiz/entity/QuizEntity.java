package com.oxQuiz.entity;

import com.oxQuiz.config.MessageInitialize;
import com.oxQuiz.config.QuizInitialize;
import com.oxQuiz.config.UserInitialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizEntity implements QuizInitialize, UserInitialize, MessageInitialize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(length = QUESTION_SIZE, nullable = false)
    private String question;

    @NotNull
    @Column(nullable = false)
    private Boolean answer;

    @NotNull
    @Size(max = NICK_NAME_SIZE, message = NICK_NAME + NICK_NAME_SIZE + IMPOSSIBLE_LENGTH_EXCEEDED)
    private String writer;

}
