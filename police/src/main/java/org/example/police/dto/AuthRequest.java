package org.example.police.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на аутентификацию")
public class AuthRequest {

    @Schema(description = "Логин", example = "citizen")
    @NotBlank(message = "Логин обязателен")
    private String username;

    @Schema(description = "Пароль", example = "citizen123")
    @NotBlank(message = "Пароль обязателен")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}