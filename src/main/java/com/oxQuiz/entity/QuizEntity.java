package com.oxQuiz.entity;

import com.oxQuiz.config.QuizInitialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizEntity implements QuizInitialize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(length = QUESTION_SIZE, nullable = false)
    private String question;

    @NotNull
    @Column(nullable = false)
    private Boolean answer;

}
