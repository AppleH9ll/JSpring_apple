package org.example.police.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AppealRequest {

    @NotBlank(message = "Тема обращения обязательна")
    private String subject;

    @NotBlank(message = "Текст обращения обязателен")
    private String text;

    @NotNull(message = "Тип обращения обязателен")
    private Long typeId;

    private Long departmentId;
    private boolean anonymous;

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Long getTypeId() { return typeId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public boolean isAnonymous() { return anonymous; }
    public void setAnonymous(boolean anonymous) { this.anonymous = anonymous; }
}