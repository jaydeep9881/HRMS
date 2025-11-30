package com.hrms.hrms.employee.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeeRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    private Long departmentId;


    @NotBlank(message = "Role is required")
    private String role;

    @Positive(message = "Salary must be greater than 0")
    private Double salary;

    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;

    private Boolean isActive;



}
