package org.example.police.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ аутентификации с JWT токеном")
public class AuthResponse {

    @Schema(description = "JWT токен")
    private String token;

    @Schema(description = "Тип токена", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "Логин пользователя")
    private String login;

    @Schema(description = "Роли пользователя")
    private String roles;

    public AuthResponse(String token, String login, String roles) {
        this.token = token;
        this.login = login;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
}