package com.hrms.hrms.employee.service;

import com.hrms.hrms.employee.dto.EmployeeRequest;
import com.hrms.hrms.employee.dto.EmployeeResponse;
import com.hrms.hrms.employee.model.Employee;
import com.hrms.hrms.employee.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

//    Converted Employee model into  EmployeeResponse dto
    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .role(employee.getRole())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .isActive(employee.getIsActive())
                .build();
    }

//    Converted EmployeeRequest dto into Employee model
    private Employee mapToEmployee(EmployeeRequest employee) {
        return Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .role(employee.getRole())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .isActive(employee.getIsActive())
                .build();
    }


    public List<EmployeeResponse> getAllEmployees() {
    List<Employee> employees = employeeRepo.findAll();
    return  employees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee =  mapToEmployee(employeeRequest);
        Employee savedEmployee = employeeRepo.save(employee);

        return mapToResponse(savedEmployee);

    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return  mapToResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        Employee updatedEmployee = employeeRepo.save(existingEmployee);

        return mapToResponse(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepo.deleteById(id);
    }
}

