package com.globetrotter.service;

import com.globetrotter.model.User;
import com.globetrotter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String username) {
        Optional<User> existing = userRepository.findByUsername(username);
        if(existing.isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(username);
        user.setCorrectAnswers(0);
        user.setIncorrectAnswers(0);
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public void updateScore(User user, boolean correct) {
        if(correct) {
            user.setCorrectAnswers(user.getCorrectAnswers() + 1);
        } else {
            user.setIncorrectAnswers(user.getIncorrectAnswers() + 1);
        }
        userRepository.save(user);
    }
}
