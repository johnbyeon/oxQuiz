package com.oxQuiz.repository;

import com.oxQuiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity,Long> {
    @Query(value = "SELECT * FROM quiz_entity ORDER BY id", nativeQuery = true)
    List<QuizEntity> findAllOrderByID();

}
