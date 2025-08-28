package com.oxQuiz.service;

import com.oxQuiz.dto.QuizDto;
import com.oxQuiz.dto.QuizHistoryDto;
import com.oxQuiz.entity.QuizEntity;
import com.oxQuiz.entity.QuizHistoryEntity;
import com.oxQuiz.repository.QuizHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
@RequiredArgsConstructor
@Service
public class QuizHistoryService {
    private final QuizHistoryRepository quizHistoryRepository;

    public List<QuizHistoryDto> findAllUserId(String userId) {
      return quizHistoryRepository.searchUser(userId)
              .stream()
              .map(QuizHistoryDto::fromEntity)
              .sorted(Comparator.comparing(QuizHistoryDto::getId))
              .toList();

      }

    public void insertHistory(QuizHistoryDto dto) {
        QuizHistoryEntity entity = QuizHistoryDto.toDto(dto);
        quizHistoryRepository.save(entity);
    }

}
