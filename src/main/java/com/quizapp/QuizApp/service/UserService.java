package com.quizapp.QuizApp.service;


import com.quizapp.QuizApp.dao.UserDao;
import com.quizapp.QuizApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void registerUser(User user) {
        userDao.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    @Transactional
    public void deleteUserById(Long userId) {
        userDao.deleteById(userId);
    }

    
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}

