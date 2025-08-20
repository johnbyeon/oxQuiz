package com.oxQuiz.service;

import com.oxQuiz.dto.QuizDto;
import com.oxQuiz.dto.QuizHistoryDto;
import com.oxQuiz.entity.QuizEntity;
import com.oxQuiz.entity.QuizHistoryEntity;
import com.oxQuiz.repository.QuizHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class QuizHistoryService {
    private final QuizHistoryRepository quizHistoryRepository;

    public List<QuizHistoryDto> findAllUserId(String userId) {
      return quizHistoryRepository.searchUser(userId)
              .stream()
              .map(QuizHistoryDto::fromEntity)
              .toList();
      }

}
