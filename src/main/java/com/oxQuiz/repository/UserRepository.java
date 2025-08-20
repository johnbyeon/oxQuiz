package com.oxQuiz.repository;

import com.oxQuiz.entity.UserEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntitiy,String> {
    @Query(value = "SELECT * FROM user_entity ORDER BY user_id", nativeQuery = true)
    List<UserEntitiy> findAllOrderByID();

}
