package org.example.police.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Ответ с ошибкой")
public class ErrorResponse {

    @Schema(description = "Временная метка")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP статус")
    private int status;

    @Schema(description = "Сообщение об ошибке")
    private String message;

    @Schema(description = "Путь запроса")
    private String path;

    public ErrorResponse(int status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}