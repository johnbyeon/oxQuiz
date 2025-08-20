package com.oxQuiz.repository;

import com.oxQuiz.entity.QuizHistoryEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizHistoryRepository extends JpaRepository<QuizHistoryEntity,Long> {
    @Query(value = "SELECT * FROM quiz_history_entity WHERE user_id LIKE CONCAT('%', :keyword, '%') ORDER BY id", nativeQuery = true)
    List<QuizHistoryEntity> searchUser(@Param("keyword")String keyword);
}
