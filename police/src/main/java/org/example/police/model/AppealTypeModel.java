package org.example.police.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rpm_java_appeal_types")
public class AppealTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private Long idType;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    public AppealTypeModel() {}
    public AppealTypeModel(Long idType, String name) {
        this.idType = idType;
        this.name = name;
    }

    public Long getIdType() { return idType; }
    public void setIdType(Long idType) { this.idType = idType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}