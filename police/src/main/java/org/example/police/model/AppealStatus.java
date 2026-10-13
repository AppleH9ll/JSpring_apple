package org.example.police.model;

public enum AppealStatus {
    NEW("Новое"),
    IN_PROGRESS("В работе"),
    REVIEWED("Рассмотрено"),
    REJECTED("Отклонено"),
    CLOSED("Закрыто");

    private final String displayName;
    AppealStatus(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}