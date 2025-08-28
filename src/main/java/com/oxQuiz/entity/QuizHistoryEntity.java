package com.oxQuiz.entity;

import com.oxQuiz.config.UserInitialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizHistoryEntity implements UserInitialize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
