package com.quizapp.QuizApp.dao;


import com.quizapp.QuizApp.entity.User;
import java.util.List;

public interface UserDao {
    void save(User user);
    User findByUsername(String username);
    List<User> findAll();
    void deleteById(Long id);
}
