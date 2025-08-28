package com.oxQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class QuizHistoryOutput {
    //퀴즈번호
    private Long id;
    //퀴즈 질문
    private String question;
    //퀴즈 정답
    private String answer;
    //퀴즈 작성자
    private String writer;
    //정답여부
    private String isCorrect;
    //퀴즈푼시간
    private Timestamp quizTime;

    public static QuizHistoryOutput makeOutput(QuizHistoryDto historyDto, QuizDto quizDto){
        QuizHistoryOutput quizHistoryOutput = QuizHistoryOutput.builder()
                                            .id(historyDto.getId())
                                            .writer(quizDto.getWriter())
                                            .question(quizDto.getQuestion())
                                            .quizTime(historyDto.getQuizTime())
                                            .build();
        if(historyDto.getIsCorrect())
            quizHistoryOutput.setIsCorrect("O");
        else
            quizHistoryOutput.setIsCorrect("X");

        if(quizDto.getAnswer())
            quizHistoryOutput.setAnswer("O");
        else
            quizHistoryOutput.setAnswer("X");

        return quizHistoryOutput;

    }
}
