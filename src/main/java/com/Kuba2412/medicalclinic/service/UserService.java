package com.Kuba2412.medicalclinic.service;


import com.Kuba2412.medicalclinic.model.User;
import com.Kuba2412.medicalclinic.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public User addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }


    public User getUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
