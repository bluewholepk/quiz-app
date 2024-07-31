package com.quizapp.QuizApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("1234")
public class QuestionController {
	@GetMapping("1234")
	public String getAllQuestions() {
		return "HIIIIIIIIII";
	}
}
 