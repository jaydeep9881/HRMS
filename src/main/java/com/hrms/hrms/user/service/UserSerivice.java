package com.hrms.hrms.user.service;

import com.hrms.hrms.employee.model.Employee;
import com.hrms.hrms.employee.repository.EmployeeRepo;
import com.hrms.hrms.user.DTO.UserRequest;
import com.hrms.hrms.user.DTO.UserResponse;
import com.hrms.hrms.user.controller.Users;
import com.hrms.hrms.user.repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSerivice {
    @Autowired
    private UserRepo repo;
    @Autowired
    private EmployeeRepo empRepo;
    public static Users toEntity(UserRequest request, Employee employee) {
        return Users.builder()

                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .employee(employee)
                .build();
    }
    // Convert Entity → Response DTO
    public static UserResponse toResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .employeeId(user.getEmployee().getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    private final BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);
    public UserResponse addUser(UserRequest user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Employee emp= empRepo.findById(user.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        Users usr= toEntity(user,emp);
        Users savedUser = repo.save(usr);
        return  toResponse(savedUser);

    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> users= repo.findAll().stream()
                .map(UserSerivice::toResponse)
                .toList();
        return users;
    }

    public UserDetails loadUserByUsername(String username) {
        Users user = repo.findByUsername(username);
        if (user != null) {
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }
        throw new RuntimeException("User not found with username: " + username);
    }
}
