package com.hrms.hrms.user.controller;

import com.hrms.hrms.employee.model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String username;

    private String password;
    private String role;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private  LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt =LocalDateTime.now();
    }
}
