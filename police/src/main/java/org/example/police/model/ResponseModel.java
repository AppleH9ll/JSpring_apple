package org.example.police.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rpm_java_responses")
public class ResponseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_response")
    private Long idResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id", nullable = false)
    private AppealModel appeal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id", nullable = false)
    private UserModel officer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public ResponseModel() {}

    public Long getIdResponse() { return idResponse; }
    public void setIdResponse(Long idResponse) { this.idResponse = idResponse; }
    public AppealModel getAppeal() { return appeal; }
    public void setAppeal(AppealModel appeal) { this.appeal = appeal; }
    public UserModel getOfficer() { return officer; }
    public void setOfficer(UserModel officer) { this.officer = officer; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}