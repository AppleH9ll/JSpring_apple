package org.example.police.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.example.police.dto.AuthRequest;
import org.example.police.dto.AuthResponse;
import org.example.police.dto.ErrorResponse;
import org.example.police.model.UserModel;
import org.example.police.security.JwtService;
import org.example.police.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthRestController(AuthenticationManager authenticationManager,
                              JwtService jwtService,
                              UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Вход и получение JWT")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(), req.getPassword()));
            UserDetails details = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(details);
            UserModel user = userService.findByUsername(req.getUsername()).orElseThrow();
            return ResponseEntity.ok(new AuthResponse(
                    token, user.getUsername(), user.getRoleNames()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(401, "Неверный логин или пароль", "/api/auth/login"));
        }
    }
}