// QuizQuestionRepository.java
package com.quizapp.QuizApp.dao;

import com.quizapp.QuizApp.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
}
