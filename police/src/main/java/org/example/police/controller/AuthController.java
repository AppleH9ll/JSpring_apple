package org.example.police.controller;

import org.example.police.model.RoleEnum;
import org.example.police.model.UserModel;
import org.example.police.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginView() { return "login"; }

    @GetMapping("/registration")
    public String registrationView() { return "register"; }

    @PostMapping("/registration")
    public String register(UserModel user, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "register";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(RoleEnum.CITIZEN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}