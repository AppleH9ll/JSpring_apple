package org.example.police.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rpm_java_departments")
public class DepartmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department")
    private Long idDepartment;

    @Column(nullable = false, unique = true, length = 200)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    public DepartmentModel() {}

    public Long getIdDepartment() { return idDepartment; }
    public void setIdDepartment(Long idDepartment) { this.idDepartment = idDepartment; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}