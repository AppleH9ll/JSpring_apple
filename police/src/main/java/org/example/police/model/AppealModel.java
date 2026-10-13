package org.example.police.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rpm_java_appeals")
public class AppealModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appeal")
    private Long idAppeal;

    @Column(name = "registration_number", nullable = false, unique = true, length = 30)
    private String registrationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private UserModel citizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id")
    private UserModel officer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentModel department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AppealTypeModel type;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppealStatus status = AppealStatus.NEW;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean anonymous = false;

    @OneToMany(mappedBy = "appeal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachmentModel> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "appeal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponseModel> responses = new ArrayList<>();

    public AppealModel() {}

    public Long getIdAppeal() { return idAppeal; }
    public void setIdAppeal(Long idAppeal) { this.idAppeal = idAppeal; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public UserModel getCitizen() { return citizen; }
    public void setCitizen(UserModel citizen) { this.citizen = citizen; }
    public UserModel getOfficer() { return officer; }
    public void setOfficer(UserModel officer) { this.officer = officer; }
    public DepartmentModel getDepartment() { return department; }
    public void setDepartment(DepartmentModel department) { this.department = department; }
    public AppealTypeModel getType() { return type; }
    public void setType(AppealTypeModel type) { this.type = type; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public AppealStatus getStatus() { return status; }
    public void setStatus(AppealStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public boolean isAnonymous() { return anonymous; }
    public void setAnonymous(boolean anonymous) { this.anonymous = anonymous; }
    public List<AttachmentModel> getAttachments() { return attachments; }
    public void setAttachments(List<AttachmentModel> attachments) { this.attachments = attachments; }
    public List<ResponseModel> getResponses() { return responses; }
    public void setResponses(List<ResponseModel> responses) { this.responses = responses; }
}