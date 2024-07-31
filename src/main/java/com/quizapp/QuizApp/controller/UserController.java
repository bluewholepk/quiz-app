package com.quizapp.QuizApp.controller;

import com.quizapp.QuizApp.entity.User;
import com.quizapp.QuizApp.entity.QuizQuestion; // Import QuizQuestion
import com.quizapp.QuizApp.service.UserService;
import com.quizapp.QuizApp.service.QuizQuestionService; // Import QuizQuestionService

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Map;



@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private QuizQuestionService quizQuestionService;
    

    @GetMapping("/registeruser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register.html";
    }

    @PostMapping("/registeruser")
    public String processRegistration(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user);

        // Add success message and username to the model
        model.addAttribute("successMessage", "User registered successfully!");
        model.addAttribute("registeredUsername", user.getUsername());

        // Redirect to a registration success page
        return "redirect:/allusers";
    }


    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login.html";
    }

   
    @PostMapping("/")
    public String processLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User authenticatedUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            // Store user information in the session
            session.setAttribute("user", authenticatedUser);
            return "redirect:/quiz";
        } else {
            model.addAttribute("error", "User not found or incorrect password. Please register or try again.");
            return "login.html";
        }
    }



    @GetMapping("/dashboarduser")
    public String showDashboard(Model model, HttpSession session) {
        // Retrieve user information from the session
        User user = (User) session.getAttribute("user");
        
        // Check if the user is logged in (optional)
        if (user != null) {
            // You can add any dashboard-related logic here
            model.addAttribute("user", user);
            return "dashboard.html";
        } else {
            // Redirect to login if the user is not authenticated
            return "redirect:/";
        }
    }
    
    @GetMapping("/allusers")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "all-users.html";
    }
    
    
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/allusers";
    }
    
    @GetMapping("/admin")
    public String showAdminPanel(Model model) {
        return "admin.html";
    }
    
    @GetMapping("/allquestions")
    public String showAllQuestions(Model model) {
        List<QuizQuestion> questions = quizQuestionService.getAllQuizQuestions();
        model.addAttribute("questions", questions);
        return "all-questions.html";
    }

    @GetMapping("/admin/addQuestionForm")
    public String showAddQuestionForm(Model model) {
        model.addAttribute("newQuestion", new QuizQuestion());
        return "add-question-form.html";
    }

    @PostMapping("/admin/addQuestion")
    public String addQuestion(@ModelAttribute("newQuestion") QuizQuestion newQuestion) {
        quizQuestionService.addQuizQuestion(newQuestion);
        return "redirect:/allquestions";
    }

    @GetMapping("/admin/updateQuestionForm")
    public String showUpdateQuestionForm(@RequestParam("id") Long questionId, Model model) {
        Optional<QuizQuestion> existingQuestion = quizQuestionService.getQuizQuestionById(questionId);
        existingQuestion.ifPresent(question -> model.addAttribute("existingQuestion", question));
        return "update-question-form.html";
    }


    @PostMapping("/admin/updateQuestion")
    public String updateQuestion(@RequestParam("id") Long existingQuestionId, @ModelAttribute("existingQuestion") QuizQuestion updatedQuestion) {
        // Update the question in the database
        quizQuestionService.updateQuizQuestion(existingQuestionId, updatedQuestion);

        // Redirect back to the quiz page
        return "redirect:/allquestions";
    }


    @PostMapping("/admin/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") Long questionId) {
        quizQuestionService.deleteQuizQuestion(questionId);
        return "redirect:/allquestions";
    }
    
    @GetMapping("/quiz")
    public String showQuiz(Model model, HttpSession session) {
        // Retrieve user information from the session
        User user = (User) session.getAttribute("user");

        // Check if the user is logged in
        if (user != null) {
            // You can add quiz-related logic here
            // For example, provide a list of questions to the model
            List<QuizQuestion> questions = quizQuestionService.getAllQuizQuestions();
            model.addAttribute("questions", questions);
            return "quiz-page.html";
        } else {
            // Redirect to login if the user is not authenticated
            return "redirect:/";
        }
    }
    
    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Map<String, String> allParams, Model model) {
        // Retrieve the list of questions from the database
        List<QuizQuestion> questions = quizQuestionService.getAllQuizQuestions();

        // Process quiz answers and calculate the score
        int score = calculateScore(questions, allParams);

        // Add the score to the model
        model.addAttribute("score", score);

        // Redirect to the result page
        return "result.html";
    }

    private int calculateScore(List<QuizQuestion> questions, Map<String, String> userAnswers) {
        int score = 0;

        for (QuizQuestion question : questions) {
            // Get the correct answer for the current question
            String correctAnswer = getCorrectAnswer(question);

            // Get the user's answer for the current question
            String userAnswer = userAnswers.get("answers[" + question.getId() + "]");

            // Compare the user's answer with the correct answer
            if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                score++;
            }
        }

        return score;
    }

    private String getCorrectAnswer(QuizQuestion question) {
        // Assuming options is a List<String> in your QuizQuestion entity
        List<String> options = question.getOptions();

        // Assuming correctOptionIndex is an index pointing to the correct answer
        int correctAnswerIndex = question.getCorrectOptionIndex();

        // Retrieve the correct answer from the options list
        if (correctAnswerIndex >= 0 && correctAnswerIndex < options.size()) {
            return options.get(correctAnswerIndex);
        } else {
            // Handle the case where the correctOptionIndex is out of bounds
            return "";
        }
    }






}
