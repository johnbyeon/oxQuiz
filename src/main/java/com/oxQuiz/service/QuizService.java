package com.oxQuiz.service;

import com.oxQuiz.dto.QuizDto;
import com.oxQuiz.entity.QuizEntity;
import com.oxQuiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public List<QuizDto> findAllQuiz(){
        return quizRepository.findAllOrderByID()
                .stream()
                .map(QuizDto::fromEntity)
                .toList();
    }
    public void insertQuiz(QuizDto quizDto) {
        QuizEntity entity = QuizDto.toDto(quizDto);
        quizRepository.save(entity);
    }

    public void updateQuiz(QuizDto quizDto) {
        QuizEntity entity = QuizDto.toDto(quizDto);
            quizRepository.save(entity);
    }

    public QuizDto findQuiz(Long id) {
        QuizEntity quizEntity = quizRepository.findById(id).orElse(null);
        if(ObjectUtils.isEmpty(quizEntity)){
            return null;
        }
        return QuizDto.fromEntity(quizEntity);
    }

    public void deleteQuiz(Long id) {
        QuizEntity quizEntity = quizRepository.findById(id).orElse(null);
        if(!ObjectUtils.isEmpty(quizEntity)){
            quizRepository.delete(quizEntity);
        }
    }
}
