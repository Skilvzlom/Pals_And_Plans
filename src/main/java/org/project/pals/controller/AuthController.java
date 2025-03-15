package org.project.pals.controller;

import org.project.pals.model.User;
import org.project.pals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> regNewUser(@RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
