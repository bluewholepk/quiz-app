// QuizQuestionService.java
package com.quizapp.QuizApp.service;

import com.quizapp.QuizApp.dao.QuizQuestionRepository;
import com.quizapp.QuizApp.entity.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizQuestionService {

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public List<QuizQuestion> getAllQuizQuestions() {
        return quizQuestionRepository.findAll();
    }

    public Optional<QuizQuestion> getQuizQuestionById(Long questionId) {
        return quizQuestionRepository.findById(questionId);
    }

    public void addQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionRepository.save(quizQuestion);
    }

    public void addQuizQuestions(List<QuizQuestion> quizQuestions) {
        quizQuestionRepository.saveAll(quizQuestions);
    }

    public void updateQuizQuestion(Long questionId, QuizQuestion updatedQuestion) {
        Optional<QuizQuestion> existingQuestion = quizQuestionRepository.findById(questionId);
        if (existingQuestion.isPresent()) {
            QuizQuestion questionToUpdate = existingQuestion.get();
            questionToUpdate.setQuestion(updatedQuestion.getQuestion());
            questionToUpdate.setOptions(updatedQuestion.getOptions());
            questionToUpdate.setCorrectOptionIndex(updatedQuestion.getCorrectOptionIndex());
            quizQuestionRepository.save(questionToUpdate);
        }
    }

    public void deleteQuizQuestion(Long questionId) {
        quizQuestionRepository.deleteById(questionId);
    }
}
