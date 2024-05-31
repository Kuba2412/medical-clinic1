package com.Kuba2412.medicalclinic.controller;

import com.Kuba2412.medicalclinic.model.User;
import com.Kuba2412.medicalclinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user.getUsername(), user.getPassword());
    }

    @GetMapping("/{id}")
    public User getUserId(@PathVariable Long id) {
        return userService.getUserId(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{username}/password")
    public User updatePassword(@PathVariable String username, @RequestBody String newPassword) {
        return userService.updatePassword(username, newPassword);
    }
}
