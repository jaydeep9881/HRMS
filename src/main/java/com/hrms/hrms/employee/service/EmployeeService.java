package com.hrms.hrms.employee.service;

import com.hrms.hrms.department.model.Department;
import com.hrms.hrms.department.repositary.DepartmentRepo;
import com.hrms.hrms.employee.dto.EmployeeRequest;
import com.hrms.hrms.employee.dto.EmployeeResponse;
import com.hrms.hrms.employee.model.Employee;
import com.hrms.hrms.employee.repository.EmployeeRepo;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    // Convert Employee model to EmployeeResponse DTO
    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .departmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                .department(employee.getDepartment() != null ? employee.getDepartment().getName() : null)
                .role(employee.getRole())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .isActive(employee.getIsActive())
                .build();
    }

    // Convert EmployeeRequest DTO to Employee model
    private Employee mapToEmployee(EmployeeRequest request) {
        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
        }

        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(department)
                .role(request.getRole())
                .salary(request.getSalary())
                .joiningDate(request.getJoiningDate())
                .isActive(request.getIsActive())
                .build();
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public EmployeeResponse addEmployee(EmployeeRequest request) {
        Employee employee = mapToEmployee(request);
        Employee savedEmployee = employeeRepo.save(employee);
        return mapToResponse(savedEmployee);
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return mapToResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Apply updates from request
        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setRole(request.getRole());
        existingEmployee.setSalary(request.getSalary());
        existingEmployee.setJoiningDate(request.getJoiningDate());
        existingEmployee.setIsActive(request.getIsActive());

        // Update department
        if (request.getDepartmentId() != null) {
            Department department = departmentRepo.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existingEmployee.setDepartment(department);
        }

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
