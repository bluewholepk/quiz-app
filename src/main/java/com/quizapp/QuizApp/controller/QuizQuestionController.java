// QuizQuestionController.java
package com.quizapp.QuizApp.controller;

import com.quizapp.QuizApp.entity.QuizQuestion;
import com.quizapp.QuizApp.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @GetMapping
    public List<QuizQuestion> getAllQuizQuestions() {
        return quizQuestionService.getAllQuizQuestions();
    }

    @GetMapping("/{id}")
    public Optional<QuizQuestion> getQuizQuestionById(@PathVariable Long id) {
        return quizQuestionService.getQuizQuestionById(id);
    }

    @PostMapping
    public void addQuizQuestion(@RequestBody QuizQuestion quizQuestion) {
        quizQuestionService.addQuizQuestion(quizQuestion);
    }

    @PutMapping("/{id}")
    public void updateQuizQuestion(@PathVariable Long id, @RequestBody QuizQuestion updatedQuestion) {
        quizQuestionService.updateQuizQuestion(id, updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public void deleteQuizQuestion(@PathVariable Long id) {
        quizQuestionService.deleteQuizQuestion(id);
    }
}
