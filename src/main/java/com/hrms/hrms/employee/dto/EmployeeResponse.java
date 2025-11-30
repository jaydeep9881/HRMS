package com.hrms.hrms.employee.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;

    private String role;
    private Double salary;
    private LocalDate joiningDate;
    private Boolean isActive;
}
